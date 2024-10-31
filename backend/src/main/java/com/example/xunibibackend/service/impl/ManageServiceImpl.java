package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.entity.Notice;
import com.example.xunibibackend.mapper.NoticeMapper;
import com.example.xunibibackend.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ManageServiceImpl implements ManageService {
    @Autowired
    NoticeMapper noticeMapper;
    @Override
    public String getNotice() {
        return noticeMapper.getNotice();
    }

    @Override
    public Integer insertNotice(String content) {
        Notice notice=new Notice();
        notice.setContent(content);
        notice.setDisplay(false);
        return noticeMapper.insert(notice);
    }
}
