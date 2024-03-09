package com.example.demo.Service.ServiceImpl;

import com.example.demo.Service.TsService;
import com.example.demo.mapper.TsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TsServiceImpl implements TsService {

    @Autowired
    private TsMapper tsMapper;

    @Override
    public int getClassIdByTeacherId(String teacherId) {
        int classIdByTeacherId = tsMapper.getClassIdByTeacherId(teacherId);
        return classIdByTeacherId;
    }
}
