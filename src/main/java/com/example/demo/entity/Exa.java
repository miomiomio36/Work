package com.example.demo.entity;

import lombok.Data;

@Data
public class Exa {
    private int id;//考试id
    private String courseName;//课程名
    private String exaTime;//考试时间
    private String classroomNumber;//教室编号
    private String teacherId;//监考老师工号
    private int exaState;//考试状态
}
