package com.example.xunibibackend.service;

import com.example.xunibibackend.entity.Notice;
import com.example.xunibibackend.response.MyResult;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface NoticeService {


    MyResult getAllNotices();

    MyResult getNoticeById(int id);

    MyResult createNotice(Notice notice);

    void updateNotice(Notice notice);

    void deleteNotice(int id);
}
