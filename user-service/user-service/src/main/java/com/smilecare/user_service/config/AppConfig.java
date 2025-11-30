package com.smilecare.user_service.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    /**
     * Khai báo ModelMapper là một Bean để Spring quản lý
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Cấu hình ModelMapper tùy chọn (nếu cần)
        // Ví dụ: Thiết lập cách xử lý các trường chưa được ánh xạ
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        return modelMapper;
    }
}