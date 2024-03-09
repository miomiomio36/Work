package com.example.demo.controller;

import com.example.demo.Service.*;
import com.example.demo.entity.Exa;
import com.example.demo.entity.Sc;
import com.example.demo.entity.Students;
import com.example.demo.entity.Teacher;
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
    private StudentService studentService;

    @GetMapping("/test")
    public void test(){
        System.out.println("访问成功");
    }

//    查看监考的考试
    @PostMapping("/showExa")
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
    @PostMapping("showGrade")
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

}
