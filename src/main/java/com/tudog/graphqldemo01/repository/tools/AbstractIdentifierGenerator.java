package com.tudog.graphqldemo01.repository.tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.function.Consumer;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.hibernate.tuple.ValueGenerator;

import lombok.extern.slf4j.Slf4j;

/**
 * 抽象Hibernate标识列实现类，可通过实现该类来自定义标识列生成策略 标识的格式为：prefix + number + suffix, prefix
 * 和 suffix 可以为空 该类的实现依赖于数据列在表中的自然顺序，使用 (select max(${fieldName} from
 * ${tableName})) 来判断表中最大值
 */
@Slf4j
public abstract class AbstractIdentifierGenerator implements ValueGenerator<String> {

    /**
     * 默认域名称
     */
    public static final String DEFAULT_FIELD_NAME = "id";

    private String prefix = "";
    private String suffix = "";
    private final String tableName;
    private String fieldName = DEFAULT_FIELD_NAME;

    public AbstractIdentifierGenerator(final String tableName, final String fieldName) {
        this.tableName = tableName;
        this.fieldName = fieldName;
    }

    public AbstractIdentifierGenerator(final String tableName, final String fieldName, final String prefix) {
        this.tableName = tableName;
        this.fieldName = fieldName;
    }

    public AbstractIdentifierGenerator(final String tableName, final String fieldName, final String prefix,
            final String suffix) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    private class SqlWork implements Work {
        private String result;
        private Consumer<String> workFinished;
        private FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>(){
            @Override
            public String call() throws Exception {
                
                return null;
            }
        });

        private SqlWork(Consumer<String> workFinished) {
            this.workFinished = workFinished;
        }

        public Future<String> getFuture(){
            return futureTask;
        }

        @Override
        public void execute(Connection connection) throws SQLException {
            try {
                final Statement statement = connection.createStatement();
                final ResultSet rs = statement.executeQuery("select max(" + fieldName + ") max_sign from " + tableName);
                String id = null;
                if (rs.next()) {
                    id = rs.getString(1);
                }
                id = nextNumber(id);
                result = prefix + id + suffix;
                log.info("Generated Id: " + result);
                workFinished.accept(result);
            } catch (final Exception e) {
                log.error("Generating Id error: " + e.getMessage());
                e.printStackTrace();
            }
        }

    }

    @Override
    public String generateValue(final Session session, final Object owner) {
        String generatedId = "1232";

        session.doWork(new SqlWork(s -> {
            System.out.println("work " + s);
        }));

        // FutureTask<String> futureTask = new FutureTask<String>(null);
        // try {
        //     futureTask.get();
        // } catch (InterruptedException | ExecutionException e) {
        //     e.printStackTrace();
        // }

        return generatedId;
        // Query<User> query = session.createQuery("select u from User u",User.class);
        // try {
        //     List<User> list = query.getResultList();
        //     //User idRaw = query.getSingleResult();

        //    // ResultSet rs=statement.executeQuery("select max(" + fieldName + ") max_sign from " + tableName);
        //     // String id;
        //     // if(idRaw == null){
        //     //     id = nextNumber(null);
        //     // }else{
        //     //     String idStr = extractIdNumber(idRaw);
        //     //     id = nextNumber(idStr);
        //     // }
        //     // String generatedId = prefix + id + suffix;
        //     // log.info("Generated Id: " + generatedId);
        //     // return generatedId;        
        //     return "fd";
        // } catch (Exception e) {
        //     log.error("Generating Id error: " + e.getMessage());
        //     e.printStackTrace();
        // }
       // return null;
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
    private String extractIdNumber(final String idRaw){
        return idRaw.substring(idRaw.indexOf(prefix) + prefix.length(),idRaw.indexOf(suffix));
    }

}