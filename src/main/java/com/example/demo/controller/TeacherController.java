package com.example.demo.controller;

import ch.qos.logback.core.joran.spi.ElementPath;
import com.example.demo.Service.*;
import com.example.demo.entity.*;
import com.example.demo.enums.CodeEnum;
import com.example.demo.utils.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

                List<Integer> classIdByTeacherId = tsService.getClassIdByTeacherId(tid);

                List<String> str = new ArrayList<>();

                List<Students> studentsByClassId = new ArrayList<>();
                for (int i = 0; i < classIdByTeacherId.size(); i++) {
                    Integer classId = classIdByTeacherId.get(i);
                    List<Students> students = studentService.getStudentsByClassId(classId);
                    if (students != null && !students.isEmpty()) { // 检查是否为空
                        studentsByClassId.addAll(students);
                    }
                }
                int courseId = teacherByTeacherId.getCourseId();
                for (Students students : studentsByClassId) {
                    String studentId = students.getStudentId();
                    int classId = students.getClassId();
                    String name = students.getName();
                    Sc scByStudentIdAndCourseId = scService.getSCByStudentIdAndCourseId(studentId, courseId);
                    int grade = scByStudentIdAndCourseId.getGrade();
                    String s = classId+"_"+name+"_"+studentId+"_"+grade;
                    str.add(s);
                }
                return Rest.success(str);
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
                List<question_db> allQuestionById = questionDbService.getAllQuestionBycourseId(teacherByTeacherId.getCourseId());
                return Rest.success(allQuestionById);
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
    public Rest endCourse(@RequestParam("tid") String tid){
        try {
            Teacher teacherByTeacherId = teacherService.getTeacherByTeacherId(tid);
            int courseId = teacherByTeacherId.getCourseId();
            System.out.println(teacherByTeacherId);
            List<Sc> scList= scService.getScByCourseId(courseId);
            int courseState = 0;
            for(Sc sc: scList){
                courseState+=sc.getCourseState();
            }
            if (courseState ==0){
                int i = scService.updateCourseStateByCourseId(1, courseId);
                if (i == 0){
                    return Rest.failure(CodeEnum.FAIL_APPLY);

                }else {
                    return Rest.success(CodeEnum.SUCCESS);
                }
            }else {
                return Rest.failure("不需要结课");
            }
        }catch (Exception e){
            e.printStackTrace();
            return Rest.failure(CodeEnum.ERROR);
        }
    }

//    导入题库,未测试
    @PostMapping("/import/question")
    public Rest ImportQuestion(@RequestBody question_db question){
        System.out.println(question.toString());
        try {
            int i = questionDbService.addQuestion(question);
            if (i==1){
                return Rest.success(CodeEnum.SUCCESS);
            }else {
                return Rest.failure(CodeEnum.FAIL_APPLY);
            }
        }catch (Exception e){
            e.printStackTrace();
            return Rest.failure(CodeEnum.ERROR);
        }
    }

//    下载对应类型题库
    @PostMapping("/download")
    public Rest DownloadQuestion(@RequestParam("courseId")int courseId){
        try {
            List<question_db> allQuestionByid = questionDbService.getAllQuestionBycourseId(courseId);

            return Rest.success(allQuestionByid);
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }
}




