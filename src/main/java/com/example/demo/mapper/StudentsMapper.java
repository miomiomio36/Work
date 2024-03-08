package com.example.demo.mapper;

import com.example.demo.entity.Students;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//用于操作学生表的Mapper
@Mapper
public interface StudentsMapper {
    @Select("select * from test_db.students where studentId=#{studentId}")
    Students getStudentByStudentId(String studentId);
    @Select("select * from test_db.students where classId=#{classId}")
    List<Students> getStudentsByClassId(int classId);
    @Select("select studentId from test_db.students")
    String getStudentID();


}
