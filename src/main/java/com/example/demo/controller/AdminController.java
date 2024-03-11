package com.example.demo.controller;

import com.example.demo.Service.ExaService;
import com.example.demo.Service.ScService;
import com.example.demo.entity.Exa;
import com.example.demo.entity.Sc;
import com.example.demo.enums.CodeEnum;
import com.example.demo.utils.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ExaService exaService;

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
    public Rest authExam(@RequestParam("sid") String sid,@RequestParam("exaId") int exaId){
        try {
            Sc scByStudentIdAndExaId = scService.getScByStudentIdAndExaId(sid, exaId);

//            scByStudentIdAndExaId.setCourseState();

            return Rest.success();
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }
}
