package com.example.demo.mapper;

import com.example.demo.entity.Exa;
import org.apache.ibatis.annotations.*;

import java.util.List;

//用于操作考试表的Mapper
@Mapper
public interface ExaMapper {

    @Select("select * from test_db.exa")
    List<Exa> getAll();
    @Select("select * from test_db.exa where id=#{id}")
    Exa getExaById(int id);
    @Select("select * from test_db.exa where teacherId=#{teacherId}")
    List<Exa> getExaByTeacherId(String teacherId);

    @Insert("insert into test_db.exa(courseName,exaTime,classRoomNumber,teacherId,exaState) " +
            "values (#{courseName},#{exaTime},#{classroomNumber},#{teacherId},0)")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int addExa(Exa exa);//返回值为sql语句影响的行数，而插入后的自增主键将被存放在传入的exa对象中，通过exa.getId()获取
    @Update("update test_db.exa set exaState=#{exaState} where id=#{id}")
    int updateExaById(int exaState,int id);//返回值为sql语句影响的行数

    @Select("select * from test_db.exa where exaTime=#{exaTime}")
    List<Exa> getExaByTime(String exa);
}
