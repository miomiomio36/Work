package com.example.demo.Service;

import com.example.demo.entity.Course;
import com.example.demo.mapper.CourseMapper;

import java.util.List;

public interface CourseService {
    int getCourseIdByName(String name);

    List<Course> getAll();

    String getCourseNameById(int id);
}
