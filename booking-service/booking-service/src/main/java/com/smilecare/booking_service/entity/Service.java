package com.smilecare.booking_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore; // <--- 1. NHỚ IMPORT CÁI NÀY
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "service")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nameService")
    private String nameService;

    @Column(name = "price")
    private Long price;

    // --- SỬA QUAN TRỌNG: THÊM @JsonIgnore ---
    // Để khi lấy thông tin Dịch vụ, nó KHÔNG cố gắng load danh sách các Booking cũ nữa.
    // Giúp JSON gọn gàng và không bị lỗi đệ quy.
    @OneToMany(mappedBy = "service")
    @JsonIgnore // <--- 2. THÊM DÒNG NÀY VÀO ĐÂY
    private Set<BookingServiceAssociation> bookings = new HashSet<>();

    public Service() {
    }

    // --- Getters & Setters ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Set<BookingServiceAssociation> getBookings() {
        return bookings;
    }

    public void setBookings(Set<BookingServiceAssociation> bookings) {
        this.bookings = bookings;
    }
}