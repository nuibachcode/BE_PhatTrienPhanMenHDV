package com.smilecare.booking_service.dto;

import lombok.Data;

@Data
public class ServiceDTO {
    private Integer id;

    // Phải đặt đúng tên này thì mới có hàm getNameService()
    private String nameService;

    // Phải đặt đúng tên này thì mới có hàm getPrice()
    private Long price;

    private String description;
    private Integer duration;
}