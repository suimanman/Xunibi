package com.example.xunibibackend.controller;

import com.example.xunibibackend.response.MyResult;
import com.example.xunibibackend.constants.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB

    // 单文件上传接口
    @PostMapping("/upload")
    public MyResult uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();

        // 检查文件大小
        if (file.getSize() > MAX_FILE_SIZE) {
            return MyResult.error("文件大小超过最大限制： 5 MB");
        }

        try {
            // 设置文件存储路径
            Path path = Paths.get(uploadDir + File.separator + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // 生成文件下载链接
            String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/files/download/")
                    .path(fileName)
                    .toUriString();

            return MyResult.success("文件上传成功！", downloadUri);
        } catch (IOException e) {
            return MyResult.error("文件上传失败： " + e.getMessage());
        }
    }

    // 多文件上传接口
    @PostMapping("/uploadMultiple")
    public MyResult uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        List<String> downloadLinks = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.getSize() > MAX_FILE_SIZE) {
                return MyResult.error("其中一个或多个文件超过最大限制： 5 MB");
            }

            String fileName = file.getOriginalFilename();
            try {
                // 设置文件存储路径
                Path path = Paths.get(uploadDir + File.separator + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                // 生成文件下载链接
                String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/files/download/")
                        .path(fileName)
                        .toUriString();

                downloadLinks.add(downloadUri);
            } catch (IOException e) {
                return MyResult.error("文件上传失败： " + fileName + ": " + e.getMessage());
            }
        }

        return MyResult.success("上传成功！", downloadLinks);
    }

    // 文件下载接口
    @GetMapping("/download/{fileName:.+}")
    public MyResult downloadFile(@PathVariable String fileName) {
        try {
            // 从存储路径加载文件
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            byte[] fileBytes = Files.readAllBytes(filePath);

            // 将文件字节数组包装在 MyResult 中
            return MyResult.success("文件下载成功！", fileBytes);
        } catch (IOException e) {
            return MyResult.error(HttpStatus.NOT_FOUND, "文件未找到: " + fileName);
        }
    }
}