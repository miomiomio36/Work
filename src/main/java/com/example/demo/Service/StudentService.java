package com.example.demo.Service;

import com.example.demo.entity.Students;

import java.util.List;

public interface StudentService {

    Students getStudentByStudentId(String id);

    String getStudentID();

    List<Students> getStudentsByClassId(int classId);
}
