package com.example.demo.Service;

import com.example.demo.entity.question_db;

import java.util.List;

public interface Question_dbService {


    List<question_db> getAllQuestionBycourseId(int courseId);

    List<question_db> getAllQuestion();

    boolean deleteQuestion(int id);

    int addQuestion(question_db questionDb);
}
