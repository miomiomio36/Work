package com.example.demo.controller;

import com.example.demo.Service.*;
import com.example.demo.entity.*;
import com.example.demo.enums.CodeEnum;
import com.example.demo.utils.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.demo.Default.DefaultConst.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ExaService exaService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ScService scService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/test")
    public void test(){
        System.out.println("访问成功");
    }


//分配考试
    @GetMapping("/add/exam")
    public Rest addExa(@RequestParam Integer courseId,@RequestParam String exaTime,@RequestParam String type){
        int courseState = 0;
        if(type!=null){
            if(type.equals("补考")){
                courseState =5;
            }
            else if(type.equals("正常考试")){
                courseState = 1;
            }
            else{
                return Rest.failure("考试类型传递错误");
            }
        }
        else{
            return Rest.failure("考试类型未传递");
        }
        System.out.println("课程状态"+COURSE_STATE_MAP.get(courseState));
        try{
            String courseName = courseService.getCourseNameById(courseId);
            String[] exaTimeArr = exaTime.split("-");
            Integer month1 = Integer.valueOf(exaTimeArr[0].substring(5,7));
            int day1 = Integer.parseInt(exaTimeArr[0].substring(8, 10));
            Integer month2 = Integer.valueOf(exaTimeArr[1].substring(5,7));
            Integer day2 = Integer.valueOf(exaTimeArr[1].substring(8,10));
            String timeHead = exaTimeArr[0].substring(0,8);
            int days = getExamDay(month1,month2,day1,day2);
            if(days == -1){
                return Rest.failure("不支持跨月份安排考试");
            }
            else {
                List<Classroom> classroomList = classroomService.getAll();
                System.out.println(classroomList);
                //计算时间段内的最大考试场数
                int exaMax = classroomList.size()*3*days;
                System.out.println("时间段内最大考试场数："+ exaMax);
                //根据前端传递的参数来进行正常考试和补考的分配
                List<String> stuIdList = scService.getStudentIdByCourseIdAndCourseState(courseId,courseState);
                //
                System.out.println("该课程状态下应考试的人数："+ stuIdList.size());
                //设置考场人数为2人
                int examCount = stuIdList.size()/2 +1;
                if(examCount>exaMax){
                    return Rest.failure("时间段内无法分配足够考试");
                }
                else {
                    //首先查询最早的那一天有没有安排考试，根据查询结果判断是否能安排考试，或者决定考试时间循环的起点
                    String[] TimeArr = {exaTimeArr[0]+EXA_TIME_MAP.get(1),exaTimeArr[0]+EXA_TIME_MAP.get(2),exaTimeArr[0]+EXA_TIME_MAP.get(2)};
                    int timeStart = 2;//设置时间起始初始值
                    for (String ta : TimeArr){
                        timeStart++;
                        List<Exa> exaList = exaService.getExaByTime(ta);
                        if(exaList.size()!=0){
                            timeStart--;
                            break;
                        }
                    }
                    if(timeStart == 4){
                        return Rest.failure("选定的时间段中，已有安排");
                    }
                    if(timeStart == 5){
                        timeStart =1;
                    }
                    List<Exa> exaList = new ArrayList<>();
                    for (int i=0;i<days;i++){//天数循环
                        for(int j = timeStart;j<=3;j++){//时间循环
                            List<Teacher> teacherList = teacherService.getAll();
                            for(int k=0;k<classroomList.size();k++){//考场循环
                                Exa exa = new Exa();
                                exa.setCourseName(courseName);//设置考试课程名
                                exa.setClassroomNumber(classroomList.get(k).getNumber());//设置考场
                                //设置考试时间
                                String timeLast;
                                if(day1+i<10){
                                    timeLast = "0"+ (day1 + 1);
                                }
                                else {
                                    timeLast = String.valueOf(day1+i);
                                }
                                exa.setExaTime(timeHead+timeLast+EXA_TIME_MAP.get(j));
                                //设置监考老师
                                if(k>=teacherList.size()){
                                    System.out.println("老师人数不够，请后续继续添加");
                                }
                                else {
                                    exa.setTeacherId(teacherList.get(k).getTeacherId());
                                }
                                //设置考试状态
                                exa.setExaState(0);
                                System.out.println("exa: " + exa.toString());
                                exaList.add(exa);
                            }
                        }
                    }
                    //将安排好的考试id存放入sc表中
                    int k = 0;
                    for (Exa exa : exaList) {
                        //将安排好的考试存入考试表中
                        exaService.addExa(exa);
                        System.out.println("exaID: "+ exa.getId());
                        for (int j = 0; j < 2; j++) {
                            //根据学生id和课程号更新考试id
                            if (k < stuIdList.size()) {
                                scService.updateExaId(exa.getId(), courseId, stuIdList.get(k));
                                scService.updateCourseStateByStudentIdAndexaId(2,stuIdList.get(k),exa.getId());
                                k++;
                            } else {
                                break;
                            }
                        }
                        if(k>=stuIdList.size()){
                            break;
                        }
                    }
                    System.out.println("k的计数值："+k);
                    return Rest.success();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return Rest.failure("出现了一些麻烦的错误，请联系网站管理员");
        }

    }

//审核缓考
    @GetMapping("/auth/exam")
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

//查看缓考申请
    @GetMapping("/find/deferred")
    public  Rest findDeferred(){
        try {
            List<Sc> allDeferred = scService.getAllDeferred(4);
            if(allDeferred.isEmpty()){
                return Rest.success("没有缓考申请");
            }
            return Rest.success(allDeferred);
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }

//    统计未考试的人数与科目
    @GetMapping("/statistics")
    public Rest statistics(){
        try {
            List<Sc> allDeferred = scService.getAllDeferred(1);//拿到所有正常考试的人数
            List<Sc> ScListE = scService.getAllDeferred(5);//拿到所有补考的人数
            System.out.println(allDeferred);
            List<Course> all = courseService.getAll();
            System.out.println(all);
            List<Map<String,Object>> result1 = assignExam(allDeferred,all,"正常考试");
            List<Map<String,Object>> result2 = assignExam(ScListE,all,"补考");
            List<List<Map<String,Object>>> result = new ArrayList<>();
            result.add(result1);
            result.add(result2);
            return Rest.success(result);
        }catch (Exception e){
            return Rest.failure(CodeEnum.ERROR);
        }
    }

    //统计考试的方法
    private List<Map<String,Object>> assignExam(List<Sc> ScList,List<Course> courseList,String type){
        List<Map<String,Object>> result = new ArrayList<>();
        System.out.println("拿到所有参数了");
        for (Course course : courseList) {
            int id = course.getId();
            int count = 0;
            for (Sc sc : ScList) {
                int courseId = sc.getCourseId();
                if (id == courseId) {
                    count++;
                }
            }
            System.out.println("count:" + String.valueOf(count));
            Map<String,Object> map = Map.of("courseName",course.getName(),"number",count,"Type",type,"courseId",course.getId());
            result.add(map);
        }
        System.out.println(result);
        return result;
    }

    private int getExamDay(Integer month1,Integer month2,Integer day1,Integer day2){
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
