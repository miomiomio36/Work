package com.example.demo.mapper;

import com.example.demo.entity.Exa;
import com.example.demo.entity.question_db;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface Question_dbMapper {
    @Select("select * from test_db.question_db where courseId = #{courseId}")
    List<question_db> getAllQuestionBycourseId(int courseId);

    @Select("select * from test_db.question_db")
    List<question_db> getAllQuestion();

    @Delete("delete from test_db.question_db where id =#{id} ")
    boolean deleteQuestion(int id);

    @Insert("insert into test_db.question_db(question,answer_A,answer_B,answer_C,answer_D,courseId,TrueAnswer) "+
            "values(#{question},#{answer_A},#{answer_B}，#{answer_C}，#{answer_D}，#{courseId}，#{TrueAnswer})")
    @Options(useGeneratedKeys = true)
    int addQuestion(question_db questionDb);
}
