package com.example.demo.Service.ServiceImpl;

import com.example.demo.Service.ExaService;
import com.example.demo.entity.Exa;
import com.example.demo.mapper.ExaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExaServiceImpl implements ExaService {

    @Autowired
    private ExaService exaService;
    @Override
    public Exa getExaById(int id) {
        Exa exaById = exaService.getExaById(id);
        return exaById;
    }
}
