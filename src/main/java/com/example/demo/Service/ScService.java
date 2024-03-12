package com.example.demo.Service;

import com.example.demo.entity.Sc;

import java.util.List;

public interface ScService {
    List<Sc> getScByStudentId(String studentId);

    int updateCourseStateByStudentIdAndexaId(int courseState,String studentId,int exaId);

    List<String> getStudentIdByCourseId(int courseId);

    int getGradeByStudentIdAndCourseId(String studentId,int courseId);

    Sc getSCByStudentIdAndCourseId(String studentId,int courseId);
    Sc getScByCourseId(int courseId);

    int updateGradeByStudentId(int grade,String studentId,int courseId);

    Sc getScByStudentIdAndExaId(String studentId,int exaId);

    int updateCourseStateByCourseId(int courseState,int courseId);

    List<Sc> getAllDeferred(int courseState);


}
