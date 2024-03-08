package com.example.demo.Service;

import com.example.demo.entity.Students;

public interface StudentService {

    Students getStudentByStudentId(String id);

    String getStudentID();
}
