package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//用于操作课程表的Mapper
@Mapper
public interface Course {
    @Select("select * from test_db.course")
    List<Course> getAll();
    @Select("select name from test_db.course where id=#{id}")
    String getCourseNameById(int id);
}
