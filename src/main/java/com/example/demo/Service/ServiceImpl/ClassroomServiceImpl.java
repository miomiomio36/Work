package com.example.demo.Service.ServiceImpl;

import com.example.demo.Service.ClassroomService;
import com.example.demo.entity.Classroom;
import com.example.demo.mapper.ClassroomMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomServiceImpl implements ClassroomService {
    @Autowired
    private ClassroomMapper classroomMapper;

    @Override
    public List<Classroom> getAll() {
        return classroomMapper.getAll();
    }

    @Override
    public String getNumberById(int id) {
        return classroomMapper.getNumberById(id);
    }
}
