package com.example.demo.Service.ServiceImpl;

import com.example.demo.Service.Question_dbService;
import com.example.demo.entity.question_db;
import com.example.demo.mapper.Question_dbMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Question_dbServiceImpl implements Question_dbService {

    @Autowired
    private Question_dbMapper questionDbMapper;


    @Override
    public List<question_db> getAllQuestionBycourseId(int courseId) {
        List<question_db> allQuestion = questionDbMapper.getAllQuestionBycourseId(courseId);
        return allQuestion;
    }

    @Override
    public List<question_db> getAllQuestion() {
        List<question_db> allQuestion = questionDbMapper.getAllQuestion();
        return allQuestion;
    }

    @Override
    public boolean deleteQuestion(int id) {
        boolean b = questionDbMapper.deleteQuestion(id);
        return b;
    }

    @Override
    public int addQuestion(question_db questionDb) {
        int i = questionDbMapper.addQuestion(questionDb);
        return i;
    }
}
