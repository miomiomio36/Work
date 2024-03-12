package com.example.demo.Service;

import com.example.demo.entity.Teacher;

import java.util.List;

public interface TeacherService {
    Teacher getTeacherByTeacherId(String teacherId);
    List<Teacher> getAll();
}
