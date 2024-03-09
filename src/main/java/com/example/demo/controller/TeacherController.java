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

    @PostMapping("/showExa")
    public Rest showExa(@RequestParam("tid") String tid){
        try {
            Teacher teacherByTeacherId = teacherService.getTeacherByTeacherId(tid);
            if (teacherByTeacherId != null){
                String teacherId = teacherByTeacherId.getTeacherId();
                Exa exaById = exaService.getExaById(Integer.parseInt(teacherId));
                return Rest.success(exaById);
            }
            else {
                return Rest.failure(CodeEnum.USER_INFO_ERROR);
            }
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }

    @PostMapping("showGrade")
    public Rest ShowGrade(@RequestParam("tid") String tid){
        try {
            Teacher teacherByTeacherId = teacherService.getTeacherByTeacherId(tid);
            if (teacherByTeacherId != null){
                int courseId = teacherByTeacherId.getCourseId();
                List<String> studentIdByCourseId = scService.getStudentIdByCourseId(courseId);
                for (int i = 0; i < studentIdByCourseId.size(); i++) {
                    String studentid = studentIdByCourseId.get(i);
                    Students student = studentService.getStudentByStudentId(studentid);
                    int classId = student.getClassId();
                    int classIdByTeacherId = tsService.getClassIdByTeacherId(tid);
                    if (classIdByTeacherId == classId){
                        int grade = scService.getGradeByStudentIdAndCourseId(studentid, courseId);
                        Map<String,Object> map = Map.of("sid",studentid,"courseId",courseId,"grade",grade);
                        return Rest.success(map);
                    }
                }

            }else {
                return Rest.failure(CodeEnum.USER_INFO_ERROR);
            }
            return Rest.success();
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }

}
