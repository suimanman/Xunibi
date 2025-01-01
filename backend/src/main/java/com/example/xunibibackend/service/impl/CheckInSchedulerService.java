package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.entity.Team;
import com.example.xunibibackend.entity.User;
import com.example.xunibibackend.entity.VirtualCoinTransaction;
import com.example.xunibibackend.entity.dto.SignInData;
import com.example.xunibibackend.mapper.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class CheckInSchedulerService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    TeamMapper teamMapper;
    @Autowired
    CoinTransactionMapper coinTransactionMapper;
    @Autowired
    SignCoinMapper signCoinMapper;
    @Autowired
    SignRecordMapper signRecordMapper;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String URL = "https://v2-api.delicloud.com/v2.0/cloudappapi";
    private static final String APP_KEY = "eea8179f992cab25a6286152fc4d1cbd";
    private static final String APP_SECRET = "dpu3vksypktd5r2wjcoz82fbfidx38ei"; // 替换为实际密钥
    private static final String API_MODULE = "CHECKIN";
    private static final String API_CMD = "checkin_query";

    // 已处理数据的集合
    private final Set<String> processedNames = new HashSet<>();

    private Integer nextId;
    private Integer finalId;
    /**
     * 每 3 秒请求一次签到数据
     */
    @Scheduled(fixedRate = 3000)
    public void fetchCheckInDataPeriodically() {
        try {
            // 设置请求头和请求体
            HttpHeaders headers = createHeaders("/v2.0/cloudappapi", API_MODULE, API_CMD);
            Map<String, Object> params = createRequestBody();

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params, headers);

            // 发送 POST 请求
            ResponseEntity<JsonNode> response = restTemplate.exchange(URL, HttpMethod.POST, requestEntity, JsonNode.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode next_id_node = response.getBody().path("data").path("next_id");
                nextId = next_id_node.isMissingNode() ? null : next_id_node.asInt();
                finalId=nextId;
//                log.info("nextId:{}",nextId);
                JsonNode dataNode = response.getBody().path("data").path("data");

                // 将 JsonNode 转换为 SignInData 列表
                ObjectMapper mapper = new ObjectMapper();
                List<SignInData> signInDataList = mapper.convertValue(dataNode, new TypeReference<List<SignInData>>() {});
//                log.info("获得数据：{}",signInDataList);
                // 筛选当天数据
                List<SignInData> filteredData = filterTodayData(signInDataList);
//                log.info("过滤后数据：{}",filteredData);
                // 处理获取到的签到数据
                if(!filteredData.isEmpty())
                    handleSignInData(filteredData);
//                else log.info("今天尚无人签到");
            }
        } catch (Exception e) {
            log.error("人脸识别系统接口请求失败: {}", e.getMessage());
        }
    }
    /**
     * 筛选当天数据
     */
    private List<SignInData> filterTodayData(List<SignInData> signInDataList) {
        // 获取当前年月日
        LocalDate today = LocalDate.now();

        // 筛选条件：签到数据的年月日与今天相同
        return signInDataList.stream()
                .filter(data -> {
                    LocalDate recordDate = convertTimestampToDate(data.getCheck_time());
//                    log.info("时间：{}",recordDate);
                    return recordDate.equals(today);
                })
                .toList();
    }
    /**
     * 将时间戳转换为 LocalDate
     */
    private LocalDate convertTimestampToDate(long timestamp) {
        // 时间戳单位为秒，需要转换为毫秒
        Instant instant = Instant.ofEpochSecond(timestamp);
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }
    /**
     * 每天清空处理记录
     */
    @Scheduled(cron = "0 0 0 * * ?") // 每天午夜清空
    public void clearProcessedIdsDaily() {
        processedNames.clear();
//        log.info("已处理数据集合已清空");
    }

    /**
     * 处理签到数据
     */
    private void handleSignInData(List<SignInData> signInDataList) {
        for (SignInData data : signInDataList) {
            Pattern pattern = Pattern.compile("\"member_name\":\"([^\"]+)\"");
            Matcher matcher = pattern.matcher(data.getCheck_data());
            String memberName="";
            if (matcher.find()) {
                memberName = matcher.group(1); // 提取 member_name
            }
            if (!processedNames.contains(memberName) ){
//                log.info("重复数据跳过处理: {}", data);
                // 新数据，进行处理
                processedNames.add(memberName);
                log.info("名字：{}",memberName);
                processNewData(data);
            }
        }
    }

    private void processNewData(SignInData data) {
        //增加签到人数
        LocalDate date=LocalDate.now();
//        log.info("date:{}",date);
        Integer count=signRecordMapper.getCount(date);
        if(count == null){
            signRecordMapper.insertDate(date);
            count = 1;
        }else{
            count++;
        }
        signRecordMapper.updateCount(date,count);
        // TODO: 发放虚拟币
        Double coin=signCoinMapper.findCoin();
        // 使用正则表达式提取 employee_num
        Pattern pattern = Pattern.compile("\"employee_num\":\"(\\d+)\",.*\"member_name\":\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(data.getCheck_data());

        if (matcher.find()) {
            String employeeNum = matcher.group(1); // 提取匹配的数字部分
            String memberName = matcher.group(2); // 提取 member_name
            User user=userMapper.getByUsername(employeeNum);

            Integer teamId=user.getTeamId();
            Team team =teamMapper.selectByTeamId(teamId);
            Double coinNew=team.getVirtualCoins() + coin;
            teamMapper.updateCoinById(teamId,coinNew);

            //将记录添加到虚拟币交易记录表中
            VirtualCoinTransaction coinTransaction=new VirtualCoinTransaction();
            coinTransaction.setCoinAmount(coin);
            coinTransaction.setTransactionDate(LocalDate.now());
            coinTransaction.setTransactionType("收入");
            coinTransaction.setDescription(memberName+"签到");
            coinTransaction.setTeamId(teamId);
            coinTransactionMapper.insert(coinTransaction);
        } else {
            log.info("无数据");
        }
//        log.info("处理新数据: {}", data);
    }

    /**
     * 创建请求头
     */
    private HttpHeaders createHeaders(String path, String apiModule, String apiCmd) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String signature = generateSignature(path, timestamp, APP_KEY, APP_SECRET);

        HttpHeaders headers = new HttpHeaders();
        headers.add("App-Key", APP_KEY);
        headers.add("App-Timestamp", timestamp);
        headers.add("App-Sig", signature);
        headers.add("Api-Module", apiModule);
        headers.add("Api-Cmd", apiCmd);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * 创建请求体
     */
    private Map<String, Object> createRequestBody() {
        Map<String, Object> params = new HashMap<>();
        if(nextId==null) {
            if(finalId==null) nextId=0;
            else nextId=finalId;
        }
        params.put("next_id", nextId);
        params.put("page_size", 1); // 每次只请求一条数据
        return params;
    }

    /**
     * 生成签名
     */
    private String generateSignature(String path, String timestamp, String appKey, String appSecret) {
        String rawString = path + timestamp + appKey + appSecret;
        return DigestUtils.md5DigestAsHex(rawString.getBytes(StandardCharsets.UTF_8)).toLowerCase();
    }
    /**
     * 初始化考勤数据
     */
    public void initializeCheckInData() {
        String initCmd = "checkin_query_init";

        // 设置请求头
        HttpHeaders headers = createHeaders("/v2.0/cloudappapi", API_MODULE, initCmd);

        // 初始化请求体为空
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);

        try {
            // 发起 POST 请求
            ResponseEntity<JsonNode> response = restTemplate.exchange(URL, HttpMethod.POST, requestEntity, JsonNode.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                log.info("初始化考勤数据成功，响应: {}", response.getBody());
            } else {
                log.error("初始化考勤数据失败，响应: {}", response.getBody());
            }
        } catch (Exception e) {
            log.error("初始化考勤数据请求失败: {}", e.getMessage());
        }
    }
}