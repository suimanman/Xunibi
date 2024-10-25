package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.User;
import com.example.xunibibackend.response.MyResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    MyResult register(User user);

    MyResult login(User user);

    MyResult update(User user) throws Exception;

    MyResult isLogin(HttpSession session);
}
