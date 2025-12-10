package com.smilecare.payment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStatsDTO {
    private Long revenueToday;   // Doanh thu hôm nay
    private Long revenueMonth;   // Doanh thu tháng này
    private Long totalOrders;    // Tổng đơn tháng này
    private Long totalDoctors;   // Tổng số bác sĩ
}