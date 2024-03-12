package com.example.demo.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

//用于操作老师班级表的Mapper
@Mapper
public interface TsMapper {
    @Select("select classId from test_db.ts where teacherId=#{teacherId}")
    List<Integer> getClassIdByTeacherId(String teacherId);
}
