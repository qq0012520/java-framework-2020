package com.tudog.graphqldemo01.repository.tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.hibernate.tuple.ValueGenerator;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * 抽象Hibernate标识列实现类，可通过实现该类来自定义标识列生成策略 标识的格式为：prefix + number + suffix, prefix
 * 和 suffix 可以为空 该类的实现依赖于数据列在表中的自然顺序，使用 (select max(${fieldName} from
 * ${tableName})) 来判断表中最大值
 */
public abstract class  AbstractIdentifierGenerator implements ValueGenerator<String> {

    private final org.slf4j.Logger log;

    /**
     * 默认域名称
     */
    public static final String DEFAULT_FIELD_NAME = "id";

    private String prefix = "";
    private String suffix = "";
    private String tableName;
    private String fieldName = DEFAULT_FIELD_NAME;
    private String result;

    private AbstractIdentifierGenerator() {
        log = LoggerFactory.getLogger(this.getClass());
    }

    public AbstractIdentifierGenerator(String tableName,String fieldName) {
        this();
        checkConstructorParams(tableName,fieldName);
        this.tableName = tableName;
        this.fieldName = fieldName;
    }

    public AbstractIdentifierGenerator(String tableName,String fieldName,String prefix) {
        this();
        checkConstructorParams(tableName,fieldName,prefix);
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.prefix = prefix;
    }

    public AbstractIdentifierGenerator(String tableName,String fieldName,String prefix,String suffix) {
        this();
        checkConstructorParams(tableName,fieldName,prefix,suffix);
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    private void checkConstructorParams(String... strings){
        if(!checkStringsNotEmpty(strings)){
            log.error("Constructor parameters couldn't be empty!");
            throw new IllegalArgumentException(this.getClass().getName() + ": Constructor parameters couldn't be empty!");
        }
    }

    private boolean checkStringsNotEmpty(String... strings){
        boolean isEmpty = false;
        for (int i = 0; i < strings.length; i++) {
            if(StringUtils.isEmpty(strings[i])){
                isEmpty = true;
                break;
            }
        }
        return !isEmpty;
    } 

    private class SqlWork implements Work {
        private CountDownLatch workFinishedLatch;
        private SqlWork(CountDownLatch workFinishedLatch) {
            this.workFinishedLatch = workFinishedLatch;
        }

        @Override
        public void execute(Connection connection) throws SQLException {
            try {
                final Statement statement = connection.createStatement();
                final ResultSet rs = statement.executeQuery("select max(" + fieldName + ") max_sign from " + tableName);
                String id = null;
                if (rs.next()) {
                    id = rs.getString(1);
                    if(!StringUtils.isEmpty(id)){
                        id = extractIdNumber(id);
                    }
                }
                id = nextNumber(id);
                result = prefix + id + suffix;
                log.info("Generated Value: " + result);
                workFinishedLatch.countDown();
            } catch (Exception e) {
                result = null;
                workFinishedLatch.countDown();
                log.error("Generating Value error: " + e.getMessage());
            }
        }
    }
    

    @Override
    public String generateValue(final Session session, final Object owner) {
        CountDownLatch latch = new CountDownLatch(1);
        session.doWork(new SqlWork(latch));
        try {
            latch.await();
            return result;
        } catch (InterruptedException e) {
            log.error("Generating Value error with interruption: " + e.getMessage());
            return "";
        }
    }

    /**
     * 通过最大编号生成下一个编号,当前编号为空的情况下必须提供一个初始编号
     * 
     * @param currentMaxNumber 当前表中最大的编号（除去前缀和后缀）
     * @return
     */
    protected abstract String nextNumber(String currentMaxNumber);

    /**
     * 从ID中去除前缀和后缀
     */
    private String extractIdNumber(String idRaw){
        if(StringUtils.isEmpty(prefix) && StringUtils.isEmpty(suffix)){
            return idRaw;
        }
        if(StringUtils.isEmpty(prefix)){
            return idRaw.substring(0,idRaw.indexOf(suffix));
        }else if(StringUtils.isEmpty(suffix)){
            return idRaw.substring(idRaw.indexOf(prefix) + prefix.length());
        }else{
            return idRaw.substring(idRaw.indexOf(prefix) + prefix.length(),idRaw.indexOf(suffix));
        }
    }

}