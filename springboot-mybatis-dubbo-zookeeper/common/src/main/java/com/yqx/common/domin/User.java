package com.yqx.common.domin;

import javax.persistence.Id;
import java.io.Serializable;

// 在Common工程中创建User实体类对象 并实现Serializable接口
// 表明可以让该类进行序列化 无具体意义.
public class User implements Serializable {

    @Id
    private Integer id;     // 主键ID
    private String name;    // 用户姓名
    private Integer age;    // 用户年龄
    private String sex;     // 用户性别

    public User(Integer id, String name, Integer age, String sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
