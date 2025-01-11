package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class QueryLoader {
    private static  final Properties queries = new Properties();
    static {
        try (InputStream inputStream= QueryLoader.class.getClassLoader().getResourceAsStream("sql_queries.properties")){
            if (inputStream==null) {
                throw new RuntimeException("Файл не найден");
            }
            queries.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static  String getQuery(String key){
        String query =queries.getProperty(key);
        if (query==null) {
            throw new RuntimeException("SQL запрос не найден!");
        }
        return query;
    }
}
