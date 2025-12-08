package com.smilecare.booking_service.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "service") // Khớp tên bảng XAMPP
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Sửa: Thêm tự tăng ID
    private Integer id;

    // Sửa: Thêm cột này vì trong ảnh XAMPP có 'nameService'
    @Column(name = "nameService")
    private String nameService;

    // Sửa: Tên cột trong XAMPP là 'price' (chữ p thường)
    @Column(name = "price")
    private Long price;

    // Các cột khác (duration, description...) nếu cần thì thêm, không thì thôi.

    // --- Liên kết ngược (Bidirectional) ---
    // Cái này OK nếu bạn muốn từ Service tìm ngược lại các lần đặt
    @OneToMany(mappedBy = "service")
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