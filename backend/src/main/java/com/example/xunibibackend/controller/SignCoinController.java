package com.example.xunibibackend.controller;

import com.example.xunibibackend.entity.dto.SignCoin;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.SignCoinService;
import com.example.xunibibackend.service.SignInCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sign-coin")
@Slf4j
@CrossOrigin(
        origins = "http://localhost:8081", // 允许的前端地址
        allowCredentials = "true" // 允许携带凭证
)
public class SignCoinController {
    @Autowired
    private SignCoinService signCoinService;

    @GetMapping
    public MyResult getSignCoin() {
        return signCoinService.getSignCoin();
    }

    /**
     * 更新签到虚拟币数量
     *
     * @param coin 新的虚拟币数量
     */

    @PutMapping("/update")
    public MyResult updateSignCoin(@RequestBody double coin) {
        if (coin < 0) {
            return MyResult.error("虚拟币数量必须为非负数！");
        }

        try {
            signCoinService.updateSignCoin(coin);
            return MyResult.success("修改成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
