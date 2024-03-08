package com.example.demo.Service;

import com.example.demo.entity.Sc;

import java.util.List;

public interface ScService {
    List<Sc> getScByStudentId(String studentId);
}
