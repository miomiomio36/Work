package com.example.demo;

import com.example.demo.Service.CourseService;
import com.example.demo.mapper.ClassroomMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private CourseService courseService;

    @Autowired
    private ClassroomMapper classroomMapper;
    @Test
    void contextLoads() {
    }
    @Test
    void testMapper(){
//        List<classroom> resultList = classroomMapper.getAll();
//        System.out.println(resultList);
//        System.out.println(classroomMapper.getNumberById(1));
    }
    @Test
    void ServiceTest(){
        System.out.println(courseService.getALl());
    }
}
