package com.example.demo.entity;

import lombok.Data;

@Data
public class Teacher {
    private int id;//id
    private String name;//名字
    private int courseId;//课程id
    private int age;//年龄
    private String gender;//性别
    private String role;//角色
    private String teacherId;//老师工号
}
