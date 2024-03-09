package com.example.demo.controller;

import com.example.demo.Service.ExaService;
import com.example.demo.Service.ScService;
import com.example.demo.Service.ServiceImpl.StudentServiceImpl;
import com.example.demo.Service.StudentService;
import com.example.demo.entity.Exa;
import com.example.demo.entity.Sc;
import com.example.demo.entity.Students;
import com.example.demo.enums.CodeEnum;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.Rest;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PostExchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService service;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ScService scService;

    @Autowired
    private ExaService exaService;



    @GetMapping("/test")
    public void test(){
        System.out.println("访问成功");
    }

    //    查看考试安排
    @GetMapping("/exam/{sid}")
    public Rest exam(@PathVariable("sid") String id){
        try {
            List<Sc> scByStudentId = scService.getScByStudentId(id);

            List<Exa> Exa = new ArrayList<>();
            for (int i = 0; i < scByStudentId.size(); i++) {
                Sc sc = scByStudentId.get(i);
                int exaId = sc.getExaId();
                Exa exaById = exaService.getExaById(exaId);
                Exa.add(exaById);
            }
            return Rest.success(Rest.success(Exa));

        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }


//申请补考
    @PostMapping("/apply")
    public Rest apply(@RequestParam("sid") String sid,@RequestParam("exaId") int exaID){
        try {
            int i = scService.updateCourseStateByStudentIdAndexaId(4, sid, exaID);
            if (i == 1){
                return Rest.success();

            }else {
                return Rest.failure(CodeEnum.FAIL_APPLY);
            }
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }

    @PostMapping("/attend")
    public Rest attend(@RequestParam("sid") String sid,@RequestParam("exaId")int exaId){
        try {
            int i = scService.updateCourseStateByStudentIdAndexaId(6, sid, exaId);
            if (i == 1){
                return Rest.success();

            }else {
                return Rest.failure(CodeEnum.FAIL_APPLY);
            }
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }
}
