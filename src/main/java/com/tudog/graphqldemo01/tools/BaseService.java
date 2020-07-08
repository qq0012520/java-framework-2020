package com.tudog.graphqldemo01.tools;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.OneToMany;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphs;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;

/**
 * 基本业务类，提供基本的CRUD方法和分页方法
 * 对于GraphQL的支持：查询方法通常需要传入 graphql.schema.DataFetchingEnvironment 对象
 * 该对象用于提取实体的某些（懒加载）属性，实体中有些属性会根据实际情况提取，比如注解为@OneToMany的属性
 * 
 * @param <T> 实体对象类型
 * @param <ID> 实体的ID类型
 */
public abstract class BaseService<T, ID extends Serializable> {
    protected EntityGraphJpaRepository<T, ID> baseRepository;

    //保存实体类的用于查询提取的属性名称
    private List<String> graphAttributeNames = new ArrayList<>();

    @Autowired
    protected void setBaseRepository(EntityGraphJpaRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @SuppressWarnings("unchecked")
    public BaseService(){
        ParameterizedType parameterizedType =  (ParameterizedType) this.getClass().getGenericSuperclass();
        Type entityType = parameterizedType.getActualTypeArguments()[0];
        Class<T> clazz = (Class<T>) entityType;
        System.out.println(clazz);
        Field[] fields = clazz.getDeclaredFields();
        if(fields.length > 0){
            for (Field field : fields) {
                if(field.isAnnotationPresent(OneToMany.class)){
                    graphAttributeNames.add(field.getName());
                }
            }
        }
    }

    protected EntityGraph extractedGraphAttributes(DataFetchingEnvironment env) {
        DataFetchingFieldSelectionSet selectionSet = env.getSelectionSet();

        List<String> fetchGraphsNames = graphAttributeNames.stream()
        .filter(name -> selectionSet.contains(name) || selectionSet.contains("content/" + name))
        .collect(Collectors.toList());

        EntityGraph entityGraph = null;
        if(fetchGraphsNames.size() > 0){
            entityGraph = EntityGraphUtils.fromAttributePaths(fetchGraphsNames.toArray(new String[0]));
        }else{
            entityGraph = EntityGraphs.empty();
        }
        return entityGraph;
    }

    public T findById(ID id,DataFetchingEnvironment env){
        EntityGraph graphEntity = extractedGraphAttributes(env);
        return baseRepository.findById(id,graphEntity).orElse(null);
    }

    public Page<T> findAll(int page,int size,DataFetchingEnvironment env){
        EntityGraph graphEntity = extractedGraphAttributes(env);
        return baseRepository.findAll(PageRequest.of(page, size), graphEntity);
    }

    public Page<T> findAll(Pageable pageable,DataFetchingEnvironment env){
        EntityGraph graphEntity = extractedGraphAttributes(env);
        return baseRepository.findAll(pageable, graphEntity);
    }

    public List<T> findAll(DataFetchingEnvironment env){
        EntityGraph graphEntity = extractedGraphAttributes(env);
        return (List<T>)baseRepository.findAll(graphEntity);
    }

   
}