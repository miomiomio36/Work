package com.example.demo.mapper;

import com.example.demo.entity.Sc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

//用于操作学生选课表的Mapper
@Mapper
public interface ScMapper {

    @Select("select * from test_db.sc where studentId=#{studentId}")
    List<Sc> getScByStudentId(String studentId);//学生通过学号获取所有的信息，获取课程状态，获取成绩等
    @Select("select studentId from test_db.sc where courseId=#{courseId}")
    List<String> getStudentIdByCourseId(int courseId);//通过课程号查询这门课的所有学生

    @Select("select * from test_db.sc where courseState=#{courseState}")
    List<Sc> getAllDeferred(int courseState);//查询所有要延期考试的学生

    @Select("select grade from test_db.sc where studentId=#{studentId} and courseId=#{coureseId}")
    int getGradeByStudentIdAndCourseId(String studentId,int courseId);//通过学生学号和课程id查询成绩

    @Select("select * from test_db.sc where courseId=#{courseId}")
    Sc getScByCourseId(int courseId);//通过课程id来查Sc

    @Select("select * from test_db.sc where studentId=#{studentId} and exaId=#{exaId}")
    Sc getScByStudentIdAndExaId(String studentId,int exaId);//通过学生学号和考试id查询成绩

    @Select("select * from test_db.sc where studentId=#{studentId} and courseId=#{courseId}")
    Sc getSCByStudentIdAndCourseId(String studentId,int courseId);//通过学生学号和课程id查询Sc内所有内容
    @Update("update test_db.sc set grade=#{grade} where studentId=#{studentId} and courseId=#{courseId}")
    int updateGradeByStudentId(int grade,String studentId,int courseId);//根据学号和科目修改学生成绩
    @Update("update test_db.sc set courseState=#{courseState} where courseId=#{courseId}")
    int updateCourseStateByCourseId(int courseState,int courseId);//根据课程号修改课程状态
    @Update("update test_db.sc set exaId=#{exaId} where courseId=#{courseId} and studentId=#{studentId}")
    int updateExaId(int courseId,String studentId);//根据课程号和学生学号添加考试id
    @Update("update test_db.sc set courseState=#{courseState} where studentId=#{studentId} and exaId=#{exaId}")
    int updateCourseStateByStudentId(int courseState,String studentId,int exaId);//通过学生学号和考试id修改课程状态

}
