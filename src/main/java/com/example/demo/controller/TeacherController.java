package com.example.demo.controller;

import com.example.demo.Service.*;
import com.example.demo.entity.*;
import com.example.demo.enums.CodeEnum;
import com.example.demo.utils.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ExaService exaService;

    @Autowired
    private TsService tsService;

    @Autowired
    private ScService scService;

    @Autowired
    private Question_dbService questionDbService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/test")
    public void test(){
        System.out.println("访问成功");
    }

//    查看监考的考试
    @PostMapping("/find/exam")
    public Rest showExa(@RequestParam("tid") String tid){
        try {
            Teacher teacherByTeacherId = teacherService.getTeacherByTeacherId(tid);
            if (teacherByTeacherId != null){
                String teacherId = teacherByTeacherId.getTeacherId();
                List<Exa> exaByTeacherId = exaService.getExaByTeacherId(teacherId);
                return Rest.success(exaByTeacherId);
            }
            else {
                return Rest.failure(CodeEnum.USER_INFO_ERROR);
            }
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }


//   查看成绩
    @PostMapping("/find/grade")
    public Rest ShowGrade(@RequestParam("tid") String tid){
        try {
            Teacher teacherByTeacherId = teacherService.getTeacherByTeacherId(tid);
            if (teacherByTeacherId != null){
                int classIdByTeacherId = tsService.getClassIdByTeacherId(tid);
                List<Sc> grade = new ArrayList<>();
                List<Students> studentsByClassId = studentService.getStudentsByClassId(classIdByTeacherId);
                int courseId = teacherByTeacherId.getCourseId();
                for (int i = 0; i < studentsByClassId.size(); i++) {
                    Students students = studentsByClassId.get(i);
                    String studentId = students.getStudentId();
                    Sc scByStudentIdAndCourseId = scService.getSCByStudentIdAndCourseId(studentId, courseId);
                    grade.add(scByStudentIdAndCourseId);
                }
                return Rest.success(grade);
            }else {
                return Rest.failure(CodeEnum.FAIL_APPLY);
            }
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }

//    上成绩
    @PostMapping("/updateGrade")
    public Rest UpdateGrade(@RequestParam("grade") int grade,@RequestParam("sid") String studentId,@RequestParam("courseId") int courseId){
        try{
            int i = scService.updateGradeByStudentId(grade, studentId, courseId);
            if (i == 1) {
                return Rest.success(CodeEnum.SUCCESS);
            }else {
                return Rest.failure(CodeEnum.FAIL_APPLY);
            }

        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }

//查看题库
@PostMapping("/find/question")
    public Rest findQuestion_db(@RequestParam("tid") int tid){
        try {
            Teacher teacherByTeacherId = teacherService.getTeacherByTeacherId(String.valueOf(tid));
            if (teacherByTeacherId != null){
                List<question_db> allQuestionByid = questionDbService.getAllQuestionByid();
                return Rest.success(allQuestionByid);
            }
            else {
                return Rest.failure(CodeEnum.USER_INFO_ERROR);
            }
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }

}


//删题
@PostMapping("/delet")
    public Rest deletQuestion(@RequestParam("id") int id){
        try {
            boolean b = questionDbService.deleteQuestion(id);
            if (b){
                return Rest.success(CodeEnum.SUCCESS);
            }else {
                return Rest.failure(CodeEnum.FAIL_APPLY);
            }

        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
}


//结课
@PostMapping("/end")
    public Rest endCourse(@RequestParam("tid") int tid){
        try {
            Teacher teacherByTeacherId = teacherService.getTeacherByTeacherId(String.valueOf(tid));
            int courseId = teacherByTeacherId.getCourseId();
            int i = scService.updateCourseStateByCourseId(1, courseId);
            if (i == 1){
                return Rest.success(CodeEnum.SUCCESS);
            }else {
                return Rest.failure(CodeEnum.FAIL_APPLY);
            }
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
}
}
