package com.example.demo.mapper;

import com.example.demo.entity.question_db;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface Question_dbMapper {
    @Select("select * from test_db.question_db where courseId = #{courseId}")
    List<question_db> getAllQuestion(int courseId);

    @Select("select * from test_db.question_db")
    List<question_db> getAllQuestion();

    @Delete("delete from test_db.question_db where id =#{id} ")
    boolean deleteQuestion(int id);
}
