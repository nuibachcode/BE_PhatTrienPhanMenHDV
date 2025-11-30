package com.smilecare.user_service.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialtyResponseDTO {

    // Khớp kiểu dữ liệu với Entity Specialty.java (Integer)
    private Integer id;

    // Khớp với tên trường trong Entity Specialty.java
    private String nameSpecialty;

    // description (bio) cũng là một trường Frontend có thể cần
    private String description;
}