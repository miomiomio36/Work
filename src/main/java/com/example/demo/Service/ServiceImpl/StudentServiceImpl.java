package com.example.demo.Service.ServiceImpl;

import com.example.demo.Service.StudentService;
import com.example.demo.entity.Students;
import com.example.demo.mapper.StudentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
     private StudentsMapper studentsMapper;


    @Override
    public Students getStudentByStudentId(String id) {
        Students studentByStudentId = studentsMapper.getStudentByStudentId(id);

        return studentByStudentId;
    }

    @Override
    public String getStudentID() {
        String studentID = studentsMapper.getStudentID();
        return studentID;
    }
}
