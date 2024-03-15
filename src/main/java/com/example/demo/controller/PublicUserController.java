package com.example.demo.controller;

import com.example.demo.Service.*;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Course;
import com.example.demo.entity.Students;
import com.example.demo.entity.Teacher;
import com.example.demo.enums.CodeEnum;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/public")
public class PublicUserController {
    @Autowired
    private StudentService service;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ScService scService;

    @Autowired
    private ExaService exaService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/test")
    public void test(){
        System.out.println("访问成功");
    }

    @GetMapping("/loginS/{sid}")
    public Rest StudentLogin(@PathVariable("sid") String id){
        try {
            System.out.println("1");
            Students student = service.getStudentByStudentId(id);
            String studentId = student.getStudentId();
            String name = student.getName();
//            Map<String,String> sMap;
//            sMap.put(studentId,name);

            String jwt = jwtUtils.createJwt(studentId, student.getRole());


            Map<String,String> map = Map.of("sid",studentId,"name",name,"jwt",jwt);

            return Rest.success(map);
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }

    @GetMapping("/loginT/{tid}")
    public Rest TeacherLogin(@PathVariable("tid") String id){
        try {

            Teacher teacher = teacherService.getTeacherByTeacherId(id);
            String teacherId = teacher.getTeacherId();
            String name = teacher.getName();

            String jwt = jwtUtils.createJwt(teacherId, teacher.getRole());

            int courseId = teacher.getCourseId();
            String courseName =  courseService.getCourseNameById(courseId);


            name = name + "("+courseName+")";
            Map<String,String> map = Map.of("tid",teacherId,"name",name,"jwt",jwt,"courseId", String.valueOf(courseId));

            return Rest.success(map);
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }

    @PostMapping("/loginA")
    public Rest ManagerLogin(@RequestBody Admin admin){
        try {
            System.out.println(admin);
            Admin adminByUsername = adminService.getAdminByUsername(admin.getUsername());
            if (adminByUsername == null) {
                return Rest.failure(CodeEnum.USER_INFO_ERROR);
            }
            String jwt = null;
            if (Objects.equals(adminByUsername.getPassword(), admin.getPassword())) {
                jwt = jwtUtils.createJwt(adminByUsername.getUsername(), adminByUsername.getRole());
            }
            Map<String, Object> map = Map.of("jwt",jwt,"admin",adminByUsername);

            return Rest.success(map);
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }
}
