package com.example.demo.Service.ServiceImpl;

import com.example.demo.Service.TsService;
import com.example.demo.mapper.TsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TsServiceImpl implements TsService {

    @Autowired
    private TsMapper tsMapper;

    @Override
    public List<Integer> getClassIdByTeacherId(String teacherId) {
        List<Integer> classIdByTeacherId = tsMapper.getClassIdByTeacherId(teacherId);
        return classIdByTeacherId;
    }
}
