package com.example.demo.controller;

import com.example.demo.Service.*;
import com.example.demo.Service.ServiceImpl.StudentServiceImpl;
import com.example.demo.entity.Exa;
import com.example.demo.entity.Sc;
import com.example.demo.entity.Students;
import com.example.demo.entity.question_db;
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
import java.util.Random;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ScService scService;
    @Autowired
    private Question_dbService questionDbService;

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
            for (Sc sc : scByStudentId) {
                int courseState = sc.getCourseState();
                if (courseState != 0 && courseState != 1 && courseState != 3){
                    int exaId = sc.getExaId();
                    Exa exaById = exaService.getExaById(exaId);
                    Exa.add(exaById);
                }

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

            System.out.println(1);
            int i = scService.updateCourseStateByStudentIdAndexaId(4, sid, exaID);
            if (i == 1){
                return Rest.success();

            }else {
                return Rest.failure(CodeEnum.FAIL_APPLY);
            }
        }catch (Exception e){
            System.out.println(2);
            return Rest.failure(CodeEnum.ERROR);
        }
    }


//    参加考试
    @PostMapping("/attend")
    public Rest attend(@RequestParam("sid") String sid,@RequestParam("exaId") int exaId){
        try {
            Exa exaById = exaService.getExaById(exaId);
            int exaState = exaById.getExaState();
            String courseName = exaById.getCourseName();
            int courseIdByName = courseService.getCourseIdByName(courseName);

            if (exaState ==2){
                int i = scService.updateCourseStateByStudentIdAndexaId(6, sid, exaId);
                List<question_db> questionData = new ArrayList<>();
                if (i == 1){

                    List<question_db> allQuestion = questionDbService.getAllQuestionBycourseId(courseIdByName);
                    for (int j = 0; j < 50; j++) {

                        Random r = new Random();
                        int i1 = r.nextInt(allQuestion.size());

                        questionData.add(allQuestion.get(i1));
                        allQuestion.remove(i1);
                    }
                    return Rest.success(questionData);
                }else {
                    return Rest.failure(CodeEnum.USER_INFO_ERROR);
                }
            }else {
                return Rest.failure(CodeEnum.FAIL_APPLY);
            }
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }
//查看成绩
    @PostMapping("/grade")
    public Rest grade(@RequestParam("sid") String sid){
        try {
            List<Sc> scByStudentId = scService.getScByStudentId(sid);
            Map<String , Integer> map = null;
            for (int i = 0; i < scByStudentId.size(); i++) {
                Sc sc = scByStudentId.get(i);
                int courseState = sc.getCourseState();
                if (courseState == 3){
                    map = Map.of("courseId", sc.getCourseId(), "grade", sc.getGrade());
                }
            }


            return Rest.success(map);
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }
//考试结束
    @PostMapping("/finish")
    public Rest FinishExam(@RequestParam("sid") String sid,@RequestParam("exaId") int exaId,@RequestParam("grade")int grade){
        try {
            Sc scByStudentIdAndExaId = scService.getScByStudentIdAndExaId(sid, exaId);
            if (scByStudentIdAndExaId !=null){
                scByStudentIdAndExaId.setGrade(grade);
                return Rest.success(CodeEnum.SUCCESS);
            }
            else {
                return Rest.failure(CodeEnum.USER_INFO_ERROR);
            }
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }


}
