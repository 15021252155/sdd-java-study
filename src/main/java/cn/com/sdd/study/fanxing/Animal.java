package cn.com.sdd.study.fanxing;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author suidd
 * @name Animal
 * @description 动物类
 * @date 2020/6/3 10:48
 * Version 1.0
 **/
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Animal {
    private String name;
    private int legs;
}