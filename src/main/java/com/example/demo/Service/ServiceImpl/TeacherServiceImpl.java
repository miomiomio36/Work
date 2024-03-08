package com.example.demo.Service.ServiceImpl;

import com.example.demo.Service.TeacherService;
import com.example.demo.entity.Teacher;
import com.example.demo.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    @Override
    public Teacher getTeacherByTeacherId(String teacherId) {

        Teacher teacherByTeacherId = teacherMapper.getTeacherByTeacherId(teacherId);
        return teacherByTeacherId;
    }
}
