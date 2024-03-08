package com.example.demo.entity;

import lombok.Data;

@Data
public class Sc {
    private int id;//id
    private String studentId;//学生学号
    private int courseId;//课程id
    private int grade;//成绩
    private int exaId;//考试id
    private int courseState;//当前状态
}
