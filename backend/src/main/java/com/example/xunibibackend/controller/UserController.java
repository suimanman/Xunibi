package com.example.xunibibackend.controller;

import com.example.xunibibackend.entity.User;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    /**
     * session的字段名
     */
    public static final String SESSION_NAME = "userInfo";

    @Autowired
    UserService userService;
    /*
     * 用户注册
     * @param user    传入登录用户信息
     * @param teamName 用户所属团队
     */
    @PostMapping("/register")
    public MyResult register(@RequestBody User user){
        return userService.register(user);
    }
    /*
     * 用户登录
     * @param user    传入登录用户信息
     * @param request 请求对象，用于操作session
     */
    @PostMapping("/login")
    public MyResult login(@RequestBody User user, HttpServletRequest request){
        log.info("-------{}",user);
        MyResult result;
        result=userService.login(user);
        // 如果登录成功，则设定session
        if (result.isSuccess()) {
            request.getSession().setAttribute(SESSION_NAME, result.DATA_TAG);
        }
//        result.put(MyResult.CODE_TAG,200);
        log.info("-----{}",request.getSession().getId());
        log.info("{}",result);
        return result;
    }
    /**
     * 判断用户是否登录
     *
     * @param request 请求对象，从中获取session里面的用户信息以判断用户是否登录
     * @return 结果对象，已经登录则结果为成功，且数据体为用户信息；否则结果为失败，数据体为空
     */
    @GetMapping("/isLogin")
    public MyResult isLogin(HttpServletRequest request) {
        // 传入session到用户服务层
        return userService.isLogin(request.getSession());
    }
    @PutMapping("/update")
    public MyResult update(@RequestBody User user, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        // 检查session中的用户（即当前登录用户）是否和当前被修改用户一致
        User sessionUser = (User) session.getAttribute(SESSION_NAME);
        if (sessionUser.getUserId() != user.getUserId().intValue()) {
            return MyResult.error("当前登录用户和被修改用户不一致，终止！");
        }
        MyResult result = userService.update(user);
        // 修改成功则刷新session信息
        if (result.isSuccess()) {
            session.setAttribute(SESSION_NAME, result.DATA_TAG);
        }
        return result;
    }

    /**
     * 用户登出
     *
     * @param request 请求，用于操作session
     * @return 结果对象
     */
    @GetMapping("/logout")
    public MyResult logout(HttpServletRequest request) {
        MyResult result = new MyResult();
        // 用户登出很简单，就是把session里面的用户信息设为null即可
        request.getSession().setAttribute(SESSION_NAME, null);

        return MyResult.success("用户退出登录成功！");
    }

}
