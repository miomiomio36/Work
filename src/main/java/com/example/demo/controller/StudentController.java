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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {
    @Autowired
    private StudentService service;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ScService scService;

    @Autowired
    private ExaService exaService;

    @GetMapping("/login/{tid}")
    public Rest login(@PathVariable("tid") String id){
        try {
            System.out.println(id);
            Students student = service.getStudentByStudentId(id);
            String studentId = student.getStudentId();
            String name = student.getName();
//            Map<String,String> sMap;
//            sMap.put(studentId,name);

            String jwt = jwtUtils.createJwt(studentId, student.getRole());


            Map<String,String> map = Map.of("tid",studentId,"name",name,"jwt",jwt);

            return Rest.success(map);
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }

    @GetMapping("/exam/{tid}")
    public Rest exam(@PathVariable("tid") String id){
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

//    @PostMapping("/apply")

}
