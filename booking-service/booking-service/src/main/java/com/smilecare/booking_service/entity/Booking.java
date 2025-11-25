package com.smilecare.booking_service.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet; // Import
import java.util.Set; // Import

@Entity
@Table(name = "booking")
public class Booking {

    // ... (id, status, description, user, schedule... giữ nguyên như cũ)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_booking") // Đã sửa ở bước trước
    private LocalDate dateBooking;

    @Column(name = "time_start") // Đã sửa ở bước trước
    private LocalTime timeStart;

    private String status;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    // --- SỬA CHỖ NÀY ---
    // Bỏ @ManyToMany đi
    // Thay bằng @OneToMany đến bảng Association
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private Set<BookingServiceAssociation> services = new HashSet<>();

    // Getters/Setters
    // ... (Hãy đảm bảo bạn đã tạo Getters/Setters cho "services")

    // --- Thêm hàm helper (tiện ích) ---
    public void addService(Service service, int price) {
        BookingServiceAssociation association = new BookingServiceAssociation();
        association.setBooking(this);
        association.setService(service);
        association.setPriceAtBooking(price);

        // Cập nhật ID cho Khóa gộp
        BookingServiceKey key = new BookingServiceKey();
        key.setBookingId(this.getId()); // Cần ID của booking
        key.setServiceId(service.getId()); // Cần ID của service
        association.setId(key);

        this.services.add(association);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(LocalDate dateBooking) {
        this.dateBooking = dateBooking;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public Set<BookingServiceAssociation> getServices() {
        return services;
    }

    public void setServices(Set<BookingServiceAssociation> services) {
        this.services = services;
    }
}