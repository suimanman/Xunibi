package com.example.xunibibackend.service.impl;

import com.example.xunibibackend.entity.dto.AchievementByUserDTO;
import com.example.xunibibackend.mapper.AchievementMapper;
import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.service.AchievementService;
import org.apache.catalina.LifecycleState;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class AchievementServiceImpl implements AchievementService {

    @Autowired
    private AchievementMapper achievementMapper;

    @Override
    public MyResult getAllAchievement() {
        return MyResult.success(achievementMapper.getAllAchievement());
    }

    @Override
    public List<AchievementByUserDTO> getAchievementByStu() {
        return achievementMapper.getAchievementByStu();
    }

    // 导出成就数据为Excel
    public byte[] exportAchievementsToExcel() {
        List<AchievementByUserDTO> achievements = getAchievementByStu();
        return generateExcel(achievements);
    }

    private byte[] generateExcel(List<AchievementByUserDTO> achievements) {
        try(ByteArrayOutputStream bos =  new ByteArrayOutputStream()){
             Workbook workbook = new XSSFWorkbook();
             Sheet sheet =  workbook.createSheet("Achievements");
             Row headerRow = sheet.createRow(0);
             String[] columns = {"Student Name", "Department", "Major", "Class", "Team Name", "Achievement Name", "Achievement Type", "Achievement Date", "Coin Awarded", "Description", "Status"};
             for (int i = 0; i < columns.length; i++) {
                headerRow.createCell(i).setCellValue(columns[i]);
            }

            int rowNum = 1;
            for (AchievementByUserDTO achievement : achievements) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(achievement.getStudentName());
                row.createCell(1).setCellValue(achievement.getDepartment());
                row.createCell(2).setCellValue(achievement.getMajor());
                row.createCell(3).setCellValue(achievement.getClazz());
                row.createCell(4).setCellValue(achievement.getTeamName());
                row.createCell(5).setCellValue(achievement.getAchievementName());
                row.createCell(6).setCellValue(achievement.getAchievementType());
                row.createCell(7).setCellValue(achievement.getAchievementDate().toString());
                row.createCell(8).setCellValue(achievement.getCoinAwarded().doubleValue());
                row.createCell(9).setCellValue(achievement.getDescription());
                row.createCell(10).setCellValue(achievement.getStatus());
            }

            // 自动调整列宽
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }


            // 写入输出流
            workbook.write(bos);
            return bos.toByteArray();


        } catch (IOException e)
        {
                e.printStackTrace();
            return new byte[0];
        }
    }





}
