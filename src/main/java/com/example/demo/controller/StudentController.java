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

    @Autowired
    private TeacherService teacherService;



    @GetMapping("/test")
    public void test(){
        System.out.println("访问成功");
    }

    //    查看考试安排
    @GetMapping("/exam/{sid}")
    public Rest exam(@PathVariable("sid") String id){
        System.out.println("被访问l");
        try {

            System.out.println("进入try了");
            List<Sc> scByStudentId = scService.getScByStudentId(id);

            System.out.println(scByStudentId);
            if(scByStudentId.isEmpty()){
                return Rest.failure("未知错误");
            }
            List<Map<String,Object>> exaList = new ArrayList<>();
            for (Sc sc : scByStudentId) {
                int courseState = sc.getCourseState();
                if (courseState != 0 && courseState != 1 && courseState != 3){
                    int exaId = sc.getExaId();
                    System.out.println(exaId);
                    Exa exaById = exaService.getExaById(exaId);
                    Map<String,Object> exaMap = Map.of("id",exaById.getId(),"courseName",exaById.getCourseName(),"exaTime",exaById.getExaTime(),"classroomNumber",exaById.getClassroomNumber(),"teacherName",teacherService.getTeacherByTeacherId(exaById.getTeacherId()).getName(),"exaState",exaById.getExaState());
                    System.out.println(exaById);
                    exaList.add(exaMap);
                }
            }
            return Rest.success(exaList);
        }catch (Exception e){
            e.printStackTrace();
            return Rest.failure(CodeEnum.ERROR);
        }
    }


    //申请补考
    @GetMapping("/apply")
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
    @GetMapping("/attend")
    public Rest attend(@RequestParam("sid") String sid,@RequestParam("exaId") int exaId){
        try {
            Exa exaById = exaService.getExaById(exaId);
            if (exaById ==null){
                return Rest.failure("没有这堂考试");
            }
            int exaState = exaById.getExaState();
            String courseName = exaById.getCourseName();
            int courseIdByName = courseService.getCourseIdByName(courseName);
            if (exaState ==2||exaState==1){
                int i = scService.updateCourseStateByStudentIdAndexaId(6, sid, exaId);
                List<question_db> questionData = new ArrayList<>();
                if (i == 1){
                    List<question_db> allQuestion = questionDbService.getAllQuestionBycourseId(courseIdByName);
//                    判断有没有该种类型的题
                    if(allQuestion.isEmpty()){
                        return Rest.failure("没有这种题目");
                    }
//                    抽五十道题，不够就直接给前端
                    for (int j = 0; j < 50; j++) {
                        if(allQuestion.isEmpty()){
                            break;
//                            return Rest.failure("题目不够");
                        }
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
    @GetMapping("/grade")//
    public Rest grade(@RequestParam("sid") String sid){
        System.out.println(sid);
        try {
            List<Sc> scByStudentId = scService.getScByStudentId(sid);
            List<Map<String , String>> mapList = new ArrayList<>();
            for (Sc sc : scByStudentId) {
                String courseName = courseService.getCourseNameById(sc.getCourseId());
                int courseState = sc.getCourseState();
                if (courseState == 3) {
                    Map<String, String> mapItem = Map.of("courseName", courseName, "grade", String.valueOf(sc.getGrade()));
                    mapList.add(mapItem);
                }
                else{
                    return Rest.success("当前不能查询");
                }
            }
            return Rest.success(mapList);
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }
//考试结束
    @GetMapping("/finish")//
    public Rest FinishExam(@RequestParam("sid") String sid,@RequestParam("exaId") int exaId,@RequestParam("grade")int grade){
        try {
            Sc scByStudentIdAndExaId = scService.getScByStudentIdAndExaId(sid, exaId);
            if (scByStudentIdAndExaId !=null){
                scByStudentIdAndExaId.setGrade(grade);
                scService.updateCourseStateByStudentIdAndexaId(3,sid,exaId);
                scService.updateGradeByStudentId(grade,scByStudentIdAndExaId.getStudentId(),scByStudentIdAndExaId.getCourseId());
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
