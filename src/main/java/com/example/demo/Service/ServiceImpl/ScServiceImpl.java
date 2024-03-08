package com.example.demo.Service.ServiceImpl;

import com.example.demo.Default.DefaultConst;
import com.example.demo.Service.ScService;
import com.example.demo.entity.Sc;
import com.example.demo.mapper.ScMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScServiceImpl implements ScService {
    @Autowired
    private ScMapper scMapper;
    @Override
    public List<Sc> getScByStudentId(String studentId) {
        List<Sc> studentInfo = scMapper.getScByStudentId(studentId);
        return studentInfo;
    }

    @Override
    public int updateCourseStateByStudentIdAndexaId(int s, String studentId , int exaId) {
        int i = scMapper.updateCourseStateByStudentIdAndexaId(4, studentId, exaId);
        return i;
    }


}
