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
        int i = scMapper.updateCourseStateByStudentId(s, studentId, exaId);
        return i;
    }

    @Override
    public List<String> getStudentIdByCourseId(int courseId) {

        List<String> studentIdByCourseId = scMapper.getStudentIdByCourseId(courseId);
        return studentIdByCourseId;
    }

    @Override
    public int getGradeByStudentIdAndCourseId(String studentId, int courseId) {
        int gradeByStudentIdAndCourseId = scMapper.getGradeByStudentIdAndCourseId(studentId, courseId);
        return gradeByStudentIdAndCourseId;
    }

    @Override
    public Sc getSCByStudentIdAndCourseId(String studentId, int courseId) {
        Sc scByStudentIdAndCourseId = scMapper.getSCByStudentIdAndCourseId(studentId, courseId);
        return scByStudentIdAndCourseId;
    }

    @Override
    public Sc getScByCourseId(int courseId) {
        Sc scByCourseId = scMapper.getScByCourseId(courseId);
        return scByCourseId;
    }

    @Override
    public int updateGradeByStudentId(int grade, String studentId, int courseId) {
        int i = scMapper.updateGradeByStudentId(grade, studentId, courseId);
        return i;
    }

    @Override
    public Sc getScByStudentIdAndExaId(String studentId, int exaId) {
        Sc scByStudentIdAndExaId = scMapper.getScByStudentIdAndExaId(studentId, exaId);

        return scByStudentIdAndExaId;
    }

    @Override
    public int updateCourseStateByCourseId(int courseState, int courseId) {
        int i = scMapper.updateCourseStateByCourseId(courseState, courseId);
        return i;
    }

    @Override
    public List<Sc> getAllDeferred(int courseState) {
        List<Sc> allDeferred = scMapper.getAllDeferred(courseState);
        return allDeferred;
    }


}
