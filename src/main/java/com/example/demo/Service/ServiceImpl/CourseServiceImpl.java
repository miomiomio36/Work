package com.example.demo.Service.ServiceImpl;

import com.example.demo.Service.CourseService;
import com.example.demo.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;
    @Override
    public int getCourseIdByName(String name) {
        int courseIdByName = courseMapper.getCourseIdByName(name);
        return courseIdByName;
    }
}
