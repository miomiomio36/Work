package com.example.demo.mapper;

import com.example.demo.entity.Admin;
import org.apache.ibatis.annotations.Select;

public interface AdminMapper {
    @Select("select * from test_db.admin where username=#{username}")
    Admin getAdminByUsername(String username);
}
