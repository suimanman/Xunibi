package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.controller.UserController;
import com.example.xunibibackend.entity.Notice;
import com.example.xunibibackend.entity.Team;
import com.example.xunibibackend.entity.User;
import com.example.xunibibackend.entity.VirtualCoinTransaction;
import com.example.xunibibackend.mapper.NoticeMapper;
import com.example.xunibibackend.mapper.RecordMapper;
import com.example.xunibibackend.mapper.TeamMapper;
import com.example.xunibibackend.mapper.UserMapper;
import com.example.xunibibackend.service.ManageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManageServiceImpl implements ManageService {
    @Autowired
    NoticeMapper noticeMapper;
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    UserMapper userMapper;
    @Override
    public List<String> getNotice() {
        return noticeMapper.getNotice();
    }

    @Override
    public Integer insertNotice(String content) {
        Notice notice=new Notice();
        notice.setContent(content);
        notice.setDisplay(false);
        return noticeMapper.insert(notice);
    }

    @Override
    public List<VirtualCoinTransaction> getRecords(HttpSession session) {
        User sessionUser=(User) session.getAttribute(UserController.SESSION_NAME);
        Integer teamId=userMapper.getTeamIdByUsername(sessionUser.getUsername());
        return recordMapper.getRecords(teamId);
    }
}
