package com.example.demo.Service;

import com.example.demo.entity.question_db;

import java.util.List;

public interface Question_dbService {
    List<question_db> getAllQuestionByid(int courseId);

    List<question_db> getAllQuestionByid();

    boolean deleteQuestion(int id);
}
