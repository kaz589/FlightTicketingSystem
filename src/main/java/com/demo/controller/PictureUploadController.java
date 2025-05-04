package com.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class PictureUploadController {

    // 儲存圖片的資料夾
    private static final String UPLOAD_DIR = "uploads/images";

    // 上傳圖片接口
    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("檔案未上傳");
        }

        try {
            // 取得原始檔案名稱
            String fileName = file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + "/" + fileName); //拼接路徑
            Files.createDirectories(path.getParent());

            // 儲存檔案
            file.transferTo(path);

            // 假設圖片儲存完畢後，返回圖片的 URL
            String imageUrl = "/uploads/images/" + fileName; // URL 是相對於網頁根目錄的
            return ResponseEntity.ok(Collections.singletonMap("imageUrl", imageUrl));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("圖片上傳失敗");
        }
    }
}
