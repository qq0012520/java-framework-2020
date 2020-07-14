package com.tudog.graphqldemo01.repository.tools;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.tuple.ValueGenerator;

import lombok.extern.slf4j.Slf4j;

/**
 * 抽象Hibernate标识列实现类，可通过实现该类来自定义标识列生成策略
 * 标识的格式为：prefix + number + suffix, prefix 和 suffix 可以为空
 * 该类的实现依赖于数据列在表中的自然顺序，使用 (select max(${fieldName} from ${tableName})) 来判断表中最大值
 */
@Slf4j
public abstract class AbstractIdentifierGenerator implements ValueGenerator<String> {

    /**
     * 默认域名称
     */
    public static final String DEFAULT_FIELD_NAME = "id";

    private String prefix = "";
    private String suffix = "";
    private String tableName;
    private String fieldName = DEFAULT_FIELD_NAME;

    public AbstractIdentifierGenerator(String tableName,String fieldName){
        this.tableName = tableName;
        this.fieldName = fieldName;
    }

    public AbstractIdentifierGenerator(String tableName,String fieldName,String prefix){
        this.tableName = tableName;
        this.fieldName = fieldName;
    }

    public AbstractIdentifierGenerator(String tableName,String fieldName,String prefix,String suffix){
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public String generateValue(Session session, Object owner) {
        NativeQuery<String> query = session.createNativeQuery("select max(user) max_sign from " + tableName, String.class);

        try {
            String idRaw = query.getSingleResult();

           // ResultSet rs=statement.executeQuery("select max(" + fieldName + ") max_sign from " + tableName);
            String id;
            if(idRaw == null){
                id = nextNumber(null);
            }else{
                String idStr = extractIdNumber(idRaw);
                id = nextNumber(idStr);
            }
            String generatedId = prefix + id + suffix;
            log.info("Generated Id: " + generatedId);
            return generatedId;        
            
        } catch (Exception e) {
            log.error("Generating Id error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过最大编号生成下一个编号,当前编号为空的情况下必须提供一个初始编号
     * 
     * @param currentMaxNumber 当前表中最大的编号（除去前缀和后缀）
     * @return
     */
    public abstract String nextNumber(String currentMaxNumber);

    /**
     * 从ID中去除前缀和后缀
     */
    private String extractIdNumber(String idRaw){
        return idRaw.substring(idRaw.indexOf(prefix) + prefix.length(),idRaw.indexOf(suffix));
    }

}