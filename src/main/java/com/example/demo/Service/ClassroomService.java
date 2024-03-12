package com.example.demo.Service;

import com.example.demo.entity.Classroom;

import java.util.List;

public interface ClassroomService {
    List<Classroom> getAll();
    String getNumberById(int id);
}
