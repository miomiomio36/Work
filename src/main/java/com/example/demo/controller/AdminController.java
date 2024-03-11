package com.example.demo.controller;

import com.example.demo.Service.CourseService;
import com.example.demo.Service.ExaService;
import com.example.demo.Service.ScService;
import com.example.demo.entity.Course;
import com.example.demo.entity.Exa;
import com.example.demo.entity.Sc;
import com.example.demo.enums.CodeEnum;
import com.example.demo.mapper.CourseMapper;
import com.example.demo.utils.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ExaService exaService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ScService scService;
    @GetMapping("/test")
    public void test(){
        System.out.println("访问成功");
    }


//分配考试
    @PostMapping("/add")
    public Rest addExa(@RequestParam ("courseName") String courseId,@RequestParam("exaTime") String exaTime){
        try {
//            int i = exaService.addExa(exa);
//            if (i==1){
//                return Rest.success(CodeEnum.SUCCESS);
//            }
//            else {
//                return Rest.failure(CodeEnum.FAIL_APPLY);
//            }
            return Rest.success();
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }

//审核缓考
    @PostMapping("/auth/exam")
    public Rest authExam(@RequestParam("sid") String sid,@RequestParam("exaId") int exaId,int pass){
        try {
            Sc scByStudentIdAndExaId = scService.getScByStudentIdAndExaId(sid, exaId);
            if (pass == 1){
                scByStudentIdAndExaId.setCourseState(5);
                return Rest.success();
            } else if (pass == 0) {
                scByStudentIdAndExaId.setCourseState(2);
                return Rest.success();
            }else {
                return Rest.failure(CodeEnum.FAIL_APPLY);
            }



        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }

//    查看缓考申请
    @GetMapping("/find/deferred")
    public  Rest findDeferred(){
        try {
            List<Sc> allDeferred = scService.getAllDeferred(4);
            return Rest.success(allDeferred);
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }

//    统计未考试的人数与科目
    @GetMapping("/statistics")
    public Rest statistics(){
        try {
            List<Sc> allDeferred = scService.getAllDeferred(1);
            List<Course> all = courseService.getAll();
            Map<String,Object> map = null;


            for (int i = 0; i < all.size(); i++) {
                int id = all.get(i).getId();
                int count =0;
                for (int p = 0; p < allDeferred.size(); p++) {
                    Sc sc = allDeferred.get(p);
                    int courseId = sc.getCourseId();
                    if (id == courseId){
                        count++;
                    }
                }
                String name = courseService.getCourseNameById(id);
                map = Map.of("name", name, "count", count);
            }

            return Rest.success(map);
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }
}
