package com.example.demo.mapper;

import com.example.demo.entity.Classroom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//用于操作教室表的Mapper
@Mapper
public interface ClassroomMapper {

    @Select("select * from test_db.classroom")
    List<Classroom> getAll();
    @Select("select number from test_db.classroom where id=#{id}")
    String getNumberById(int id);
}
