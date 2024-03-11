package com.example.demo.Service;

import com.example.demo.entity.Exa;

import java.util.List;

public interface ExaService {
    Exa getExaById(int id);

    List<Exa> getExaByTeacherId(String teacherId);


    int addExa(Exa exa);
}
