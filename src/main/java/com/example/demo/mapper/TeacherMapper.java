package com.example.demo.mapper;

import com.example.demo.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

//用于操作教师表的Mapper
@Mapper
public interface TeacherMapper {
    @Select("select * from test_db.teacher where teacherId=#{teacherId}")
    Teacher getTeacherByTeacherId(String teacherId);
}
