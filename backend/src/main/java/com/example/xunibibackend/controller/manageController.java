package com.example.xunibibackend.controller;

import com.example.xunibibackend.entity.VirtualCoinTransaction;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.ManageService;
import com.example.xunibibackend.service.TeamsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/manage")
@Slf4j
public class manageController {
    @Autowired
    ManageService manageService;
    @Autowired
    TeamsService teamsService;
    /*
        获取团队虚拟币交易记录
     */
    @GetMapping("/getRecord")
    public MyResult getRecord(HttpServletRequest request){
        log.info("-------虚拟币");
        //根据登录情况前端传来的cookie获取用户
        HttpSession session= request.getSession();
        List<VirtualCoinTransaction> records;
        records = manageService.getRecords(request.getSession());
        return MyResult.success(records);
    }
    /*
        微信小程序获取通知公告
     */
    @GetMapping("/notice")
    public MyResult getNotice() {
        String notice = manageService.getNotice();

        if (notice != null && !notice.isEmpty()) {
            log.info("通知公告：{}",notice);
            return MyResult.success(notice);
        } else {
            log.info("通知公告：欢迎使用虚拟币交易系统");
            return MyResult.success("欢迎使用虚拟币交易系统！");
        }
    }
    /*
        上传通知
        @Param content notice内容
     */

    @PostMapping("/notice")
    public MyResult insertNotice(@RequestBody Map<String, String> request) {
        // 从请求体中获取通知内容
        String content = request.get("content");

        // 检查通知内容是否为空
        if (content == null || content.trim().isEmpty()) {
            return MyResult.error("通知内容不能为空");
        }

        // 调用服务层方法插入通知内容
        Integer success = manageService.insertNotice(content);

        // 根据插入操作结果返回响应
        if (success == 1) {
            return MyResult.success("通知发布成功");
        } else {
            return MyResult.error("通知发布失败，请重试");
        }
    }
    /*
        获取团队虚拟币数量
     */
    @GetMapping("/getCoin")
    public MyResult getCoin(HttpServletRequest request){
        //根据登录情况前端传来的cookie获取用户
        HttpSession session = request.getSession();
        Double coin= teamsService.getCoin(request.getSession());
//        log.info("虚拟币数量：{}",coin);
        return MyResult.success(coin);
    }
}
