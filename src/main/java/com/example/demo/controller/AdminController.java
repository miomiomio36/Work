package com.example.demo.controller;

import com.example.demo.Service.ExaService;
import com.example.demo.entity.Exa;
import com.example.demo.enums.CodeEnum;
import com.example.demo.utils.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ExaService exaService;
    @GetMapping("/test")
    public void test(){
        System.out.println("访问成功");
    }


//上传考试
    @PostMapping("/add")
    public Rest addExa(@RequestBody Exa exa){
        try {
            int i = exaService.addExa(exa);
            if (i==1){
                return Rest.success(CodeEnum.SUCCESS);
            }
            else {
                return Rest.failure(CodeEnum.FAIL_APPLY);
            }
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }
}
