package com.example.xunibibackend.service.impl;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.example.xunibibackend.constants.HttpStatus;
import com.example.xunibibackend.controller.UserController;
import com.example.xunibibackend.entity.User;
import com.example.xunibibackend.mapper.TeamMapper;
import com.example.xunibibackend.mapper.UserMapper;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.xunibibackend.util.ClassExamine;

import java.time.LocalDate;

@Slf4j
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    TeamMapper teamMapper;
    @Override
    public MyResult register(User user) {
        User getUser=userMapper.getByUsername(user.getUsername());
        if(getUser != null){
            return MyResult.error("用户已经存在");
        }
        user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        user.setDate(LocalDate.now());
        userMapper.addUser(user);
        return MyResult.success("注册用户成功！",user);
    }

    @Override
    public MyResult login(User user) {
        User getUser=userMapper.getByUsername(user.getUsername());
        if(getUser==null){
            return MyResult.error(HttpStatus.BAD_REQUEST,"用户名不存在！");
        }
        if(!getUser.getPassword().equals(DigestUtil.md5Hex(user.getPassword()))){
            return MyResult.error(HttpStatus.WRONG_PARAM,"用户名密码错误！");
        }
        return MyResult.success("登录成功！",getUser);
    }

    @Override
    public MyResult update(User user) throws Exception {
        User getUser=userMapper.getByUsername(user.getUsername());
        if(getUser==null){
            return MyResult.error("用户名不存在！");
        }
        // 检测传来的对象里面字段值是否为空，若是就用数据库里面的对象相应字段值补上
        if (!StrUtil.isEmpty(user.getPassword())) {
            // 加密储存
            user.setPassword(DigestUtil.md5Hex(user.getPassword()));
        }
        // 对象互补
        ClassExamine.objectOverlap(user, getUser);

        userMapper.update(user);
        return MyResult.success("修改成功！",user);
    }

    @Override
    public MyResult isLogin(HttpSession session) {
        // 从session中取出用户信息
        User sessionUser=(User) session.getAttribute(UserController.SESSION_NAME);
        //如果session中没有用户信息说明用户未登录
        if(sessionUser==null){
            return MyResult.error("用户未登录！");
        }
        // 登录了则去数据库取出信息进行比对
        User getUser = userMapper.getByUserId(sessionUser.getUserId());
        // 如果session用户找不到对应的数据库中的用户或者找出的用户密码和session中用户不一致则说明session中用户信息无效
        if (getUser == null || !getUser.getPassword().equals(sessionUser.getPassword())) {
            return MyResult.error("用户信息无效！");
        }
        return MyResult.success("用户已登录！",getUser);
    }
}
