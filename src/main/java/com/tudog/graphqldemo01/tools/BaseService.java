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
import org.springframework.data.domain.Sort;

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

    //保存实体类用于查询（lazy）属性名称
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

    /**
     * Extract Entity Graph Attributes from GraphQL's DataFechingEnvironment
     * @param env
     * @return
     */
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

    /**
	 * Retrieves an entity by its id with graph attributes.
	 *
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal Optional#empty()} if none found.
	 * @throws IllegalArgumentException if {@literal id} is {@literal null}.
	 */
    public T findById(ID id,DataFetchingEnvironment env){
        EntityGraph graphEntity = extractedGraphAttributes(env);
        return baseRepository.findById(id,graphEntity).orElse(null);
    }

    /**
	 * Retrieves an entity by its id.
	 *
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal Optional#empty()} if none found.
	 * @throws IllegalArgumentException if {@literal id} is {@literal null}.
	 */
    public T findById(ID id){
        return baseRepository.findById(id).orElse(null);
    }

    /**
	 * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object
	 * with graph attributes
	 * @param pageable
	 * @return a page of entities
	 */
    public Page<T> findAll(int page,int size,DataFetchingEnvironment env){
        EntityGraph graphEntity = extractedGraphAttributes(env);
        return baseRepository.findAll(PageRequest.of(page, size), graphEntity);
    }

    /**
	 * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object
	 * with graph attributes.
	 * @param pageable
	 * @return a page of entities
	 */
    public Page<T> findAll(Pageable pageable,DataFetchingEnvironment env){
        EntityGraph graphEntity = extractedGraphAttributes(env);
        return baseRepository.findAll(pageable, graphEntity);
    }

    /**
	 * Returns all instances of the type with graph attributes.
	 *
	 * @return all entities
	 */
    public List<T> findAll(DataFetchingEnvironment env){
        EntityGraph graphEntity = extractedGraphAttributes(env);
        return (List<T>)baseRepository.findAll(graphEntity);
    }

    
    /**
	 * Returns all entities sorted by the given options with graph attributes.
	 *
	 * @param sort
	 * @return all entities sorted by the given options
	 */
    public List<T> findAll(Sort sort,DataFetchingEnvironment env){
        EntityGraph graphEntity = extractedGraphAttributes(env);
        return (List<T>) baseRepository.findAll(sort, graphEntity);
    }

    /**
	 * Returns all entities sorted by the given options.
	 *
	 * @param sort
	 * @return all entities sorted by the given options
	 */
    public List<T> findAll(Sort sort){
        return baseRepository.findAll(sort);
    }

    /**
	 * Returns all instances of the type {@code T} with the given IDs.
	 * <p>
	 * If some or all ids are not found, no entities are returned for these IDs.
	 * <p>
	 * Note that the order of elements in the result is not guaranteed.
	 *
	 * @param ids must not be {@literal null} nor contain any {@literal null} values.
	 * @return guaranteed to be not {@literal null}. The size can be equal or less than the number of given
	 *         {@literal ids}.
	 * @throws IllegalArgumentException in case the given {@link Iterable ids} or one of its items is {@literal null}.
	 */
    public List<T> findAllById(List<ID> ids){
        return baseRepository.findAllById(ids);
    }

    /**
	 * Returns all instances of the type {@code T} with the given IDs and graph attributes.
	 * <p>
	 * If some or all ids are not found, no entities are returned for these IDs.
	 * <p>
	 * Note that the order of elements in the result is not guaranteed.
	 *
	 * @param ids must not be {@literal null} nor contain any {@literal null} values.
	 * @return guaranteed to be not {@literal null}. The size can be equal or less than the number of given
	 *         {@literal ids}.
	 * @throws IllegalArgumentException in case the given {@link Iterable ids} or one of its items is {@literal null}.
	 */
    public List<T> findAllById(List<ID> ids,DataFetchingEnvironment env){
        EntityGraph graphEntity = extractedGraphAttributes(env);
        return (List<T>) baseRepository.findAllById(ids, graphEntity);
    }

    /**
	 * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
	 * entity instance completely.
	 *
	 * @param entity must not be {@literal null}.
	 * @return the saved entity; will never be {@literal null}.
	 * @throws IllegalArgumentException in case the given {@literal entity} is {@literal null}.
	 */
    public T save(T entity){
        return baseRepository.save(entity);
    }

    /**
	 * Deletes a given entity.
	 *
	 * @param entity must not be {@literal null}.
	 * @throws IllegalArgumentException in case the given entity is {@literal null}.
	 */
    public void delete(T entity){
        baseRepository.delete(entity);
    }

    /**
	 * Returns the number of entities available.
	 *
	 * @return the number of entities.
	 */
    public long count(){
        return baseRepository.count();
    }

    /**
	 * Deletes the entity with the given id.
	 *
	 * @param id must not be {@literal null}.
	 * @throws IllegalArgumentException in case the given {@literal id} is {@literal null}
	 */
    public void deleteById(ID id){
        baseRepository.deleteById(id);
    }

    /**
	 * Returns whether an entity with the given id exists.
	 *
	 * @param id must not be {@literal null}.
	 * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
	 * @throws IllegalArgumentException if {@literal id} is {@literal null}.
	 */
    public boolean existsById(ID id){
        return baseRepository.existsById(id);
    }

    /**
	 * Flushes all pending changes to the database.
	 */
    public void flush(){
        baseRepository.flush();
    }

    /**
	 * Saves an entity and flushes changes instantly.
	 *
	 * @param entity
	 * @return the saved entity
	 */
    public T saveAndFlush(T entity){
        return baseRepository.saveAndFlush(entity);
    }

    /**
	 * Saves all given entities.
	 *
	 * @param entities must not be {@literal null} nor must it contain {@literal null}.
	 * @return the saved entities; will never be {@literal null}. The returned {@literal Iterable} will have the same size
	 *         as the {@literal Iterable} passed as an argument.
	 * @throws IllegalArgumentException in case the given {@link Iterable entities} or one of its entities is
	 *           {@literal null}.
	 */
    public List<T> saveAll(Iterable<T> entities) {
        return baseRepository.saveAll(entities);
    }

    /**
	 * Deletes the given entities in a batch which means it will create a single {@link Query}. Assume that we will clear
	 * the {@link javax.persistence.EntityManager} after the call.
	 *
	 * @param entities
	 */
    public void deleteInBatch(Iterable<T> entities){
        baseRepository.deleteInBatch(entities);
    }
   
}