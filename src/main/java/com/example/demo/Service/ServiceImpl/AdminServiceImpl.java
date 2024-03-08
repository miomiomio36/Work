package com.example.demo.Service.ServiceImpl;

import com.example.demo.Service.AdminService;
import com.example.demo.entity.Admin;
import com.example.demo.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Override
    public Admin getAdminByUsername(String username) {
        Admin adminByUsername = adminMapper.getAdminByUsername(username);
        return adminByUsername;
    }
}
