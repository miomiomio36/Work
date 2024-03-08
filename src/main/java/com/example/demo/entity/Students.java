package com.example.demo.entity;

import lombok.Data;

@Data
public class Students {
    private int id;//id
    private String studentId;//学生学号
    private String name;//学生姓名
    private int age;//年龄
    private String gender;//性别
    private String identityId;//身份证号
    private int classId;//班级
    private String role;//角色
}
