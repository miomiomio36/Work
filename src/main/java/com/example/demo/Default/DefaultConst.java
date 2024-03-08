package com.example.demo.Default;

import java.util.Map;

public class DefaultConst {
    public static final Map<Integer,String> COURSE_STATE_MAP = Map.of(0,"未结课",1,"已结课",2,"未考试",3,"已考完",4,"申请补考",5,"补考");
    public static final String ROLE_STUDENT = "role_stu";
    public static final String ROLE_TEACHER = "role_teacher";
    public static final String ROLE_ADMIN = "role_admin";
    public static final Map<String,String> GENDER_MAP = Map.of("m","男","f","女");
    public static final Map<Integer,String> EXA_TIME_MAP = Map.of(1,"08:00-10:00",2,"11:00-13:00",3,"15:00-17:00");
    public static final Map<Integer,String> EXA_STATE_MAP = Map.of(1,"未开考",2,"已开考",3,"已结束");
}
