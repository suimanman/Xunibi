package com.example.xunibibackend.service.impl;
import com.example.xunibibackend.entity.Notice;
import com.example.xunibibackend.mapper.NoticeMapper;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.NoticeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;

    public NoticeServiceImpl(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    @Override
    public MyResult getAllNotices() {
        return MyResult.success( noticeMapper.findAll());
    }

    @Override
    public MyResult getNoticeById(int id) {
        return MyResult.success( noticeMapper.findById(id));
    }

    @Override
    public MyResult createNotice(Notice notice) {
       return MyResult.success(  noticeMapper.insert(notice));
    }

    @Override
    public void updateNotice(Notice notice) {
        noticeMapper.update(notice);
    }

    @Override
    public void deleteNotice(int id) {
        noticeMapper.delete(id);
    }
}
