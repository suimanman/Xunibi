package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.User;
import com.example.xunibibackend.response.MyResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    MyResult register(User user);

    MyResult login(User user);

    MyResult update(User user ,String teamName) throws Exception;

    MyResult isLogin(HttpSession session);

    MyResult editPassword(User sessionUser, String oldPassword, String newPassword);

    // 增删改查
    Integer createUser(User user);

    List<User> getAllUser();

    User getUserByName(String name);

    Integer updateUser(Integer Id, User user);

    void deleteUserById(Integer userId);


}
