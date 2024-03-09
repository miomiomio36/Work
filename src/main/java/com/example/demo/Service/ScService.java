package com.example.demo.Service;

import com.example.demo.entity.Sc;

import java.util.List;

public interface ScService {
    List<Sc> getScByStudentId(String studentId);

    int updateCourseStateByStudentIdAndexaId(int courseState,String studentId,int exaId);

    List<String> getStudentIdByCourseId(int courseId);

    int getGradeByStudentIdAndCourseId(String studentId,int courseId);


}
