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
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

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
    @PostMapping("/add/exam")
    public Rest addExa(@RequestParam ("courseName") int courseId,@RequestParam("exaTime") String exaTime){
        int days = getExamDay(exaTime);
        if(days == -1){
            return Rest.failure("不支持不同月份安排考试");
        }
        else {
            List<Course> courseList = courseService.getAll();
            System.out.println(courseList);
            //计算时间段内的最大考试场数
            int exaMax = courseList.size()*3*days;
            System.out.println(exaMax);
            List<String> stuIdList = scService.getStudentIdByCourseId(courseId);
            System.out.println(stuIdList.size());
            //设置考场人数为2人
            int examCount = stuIdList.size()/2 +1;
            if(examCount>exaMax){
                return Rest.failure("时间段内无法分配足够考试");
            }
            else {

            }
        }
        return Rest.success();

    }

//审核缓考
    @PostMapping("/auth/exam")
    public Rest authExam(@RequestParam("sid") String sid,@RequestParam("exaId") int exaId,@RequestParam("pass") int pass){
        try {
            Sc scByStudentIdAndExaId = scService.getScByStudentIdAndExaId(sid, exaId);
            if (pass == 1){
//                scByStudentIdAndExaId.setCourseState(5);
                scService.updateCourseStateByStudentIdAndexaId(5,sid,exaId);
                return Rest.success();
            } else if (pass == 0) {
//                scByStudentIdAndExaId.setCourseState(2);
                scService.updateCourseStateByStudentIdAndexaId(2,sid,exaId);
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
            Map<String,Integer> map = new HashMap<>();


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
                map.put(name,count);
            }

            return Rest.success(map);
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }

    private int getExamDay(String exaTime){
        String[] exaTimeArr = exaTime.split("-");
        Integer month1 = Integer.valueOf(exaTimeArr[0].substring(5,7));
        Integer day1 = Integer.valueOf(exaTimeArr[0].substring(8,10));
        Integer month2 = Integer.valueOf(exaTimeArr[1].substring(5,7));
        Integer day2 = Integer.valueOf( exaTimeArr[1].substring(8,10));
        //先用月份2减去月份1，如果为0就说明考试日期在同一月，则直接用day2减去day1再加1就行
        //若不为0说明不再同一个月，那么
        int i = month2-month1;
        if(i!=0){
            System.out.println("不支持不同月份排考试");
            return -1;
        }
        else{
            int days = day2-day1;
            System.out.println(days);
            return days+1;
        }
    }
}
