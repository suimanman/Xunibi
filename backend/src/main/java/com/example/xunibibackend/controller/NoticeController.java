package com.example.xunibibackend.controller;

import com.example.xunibibackend.entity.Notice;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notices")
@CrossOrigin(
        origins = "http://localhost:8081", // 允许的前端地址
        allowCredentials = "true" // 允许携带凭证
)
public class NoticeController {
    @Autowired
    private  NoticeService noticeService;

    @GetMapping
    public MyResult getAllNotices() {
        return noticeService.getAllNotices();
    }

    @GetMapping("/{id}")
    public MyResult getNoticeById(@PathVariable int id) {
        return noticeService.getNoticeById(id);
    }

    @PostMapping
    public MyResult createNotice(@RequestBody Notice notice) {
       return noticeService.createNotice(notice);
    }

    @PutMapping("/{id}")
    public void updateNotice(@PathVariable int id, @RequestBody Notice notice) {
        notice.setId(id);
        noticeService.updateNotice(notice);
    }

    @DeleteMapping("/{id}")
    public void deleteNotice(@PathVariable int id) {
        noticeService.deleteNotice(id);
    }
}
