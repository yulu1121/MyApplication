package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyClass {
    public static void main(String[] args) {
        Schema schema = new Schema("android", 1, "com.example.greendao");
        //创建javabean对象
        Entity entity = schema.addEntity("User");
        //添加属性
        entity.addIdProperty().primaryKey().autoincrement();
        entity.addStringProperty("user_name");
        entity.addIntProperty("user_age");
        try {
            new DaoGenerator().generateAll(schema,"../MyApplication/greendaodemo/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
