package com.example.demo.Service.ServiceImpl;

import com.example.demo.Service.ExaService;
import com.example.demo.entity.Exa;
import com.example.demo.mapper.ExaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExaServiceImpl implements ExaService {

    @Autowired
    private ExaMapper exaMapper;
    @Override
    public Exa getExaById(int id) {
        Exa exaById = exaMapper.getExaById(id);
        return exaById;
    }

    @Override
    public List<Exa> getExaByTeacherId(String teacherId) {
        List<Exa> exaByTeacherId = exaMapper.getExaByTeacherId(teacherId);
        return exaByTeacherId;
    }

    @Override
    public int addExa(Exa exa) {
        int i = exaMapper.addExa(exa);
        return i;
    }
}
