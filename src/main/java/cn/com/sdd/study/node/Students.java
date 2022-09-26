package cn.com.sdd.study.node;

import lombok.Data;

/**
 * @author suidd
 * @name Students
 * @description TODO
 * @date 2020/5/8 10:38
 * Version 1.0
 **/
@Data
public class Students {
    private String name;
    private int credit;
    private int age;

    public Students(String name, int credit, int age) {
        this.name = name;
        this.credit = credit;
        this.age = age;
    }

    public void show() {
        System.out.println("show...name=" + this.name + " credit=" + this.credit + " age=" + this.age);
    }
}