package com.smilecare.booking_service.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "booking_service")
public class BookingServiceAssociation {

    @EmbeddedId // 1. Dùng Khóa gộp (file bạn vừa tạo ở trên)
    private BookingServiceKey id = new BookingServiceKey();

    // --- Định nghĩa liên kết ---
    @ManyToOne
    @MapsId("bookingId") // 2. Ánh xạ 'bookingId' từ Key
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne
    @MapsId("serviceId") // 3. Ánh xạ 'serviceId' từ Key
    @JoinColumn(name = "service_id")
    private Service service;

    // --- Cột dữ liệu thêm (Lý do chúng ta phải làm cách này) ---
    @Column(name = "PriceAtBooking") // 4. Khớp với tên cột trong DB
    private Integer priceAtBooking;

    public BookingServiceKey getId() {
        return id;
    }

    public void setId(BookingServiceKey id) {
        this.id = id;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Integer getPriceAtBooking() {
        return priceAtBooking;
    }

    public void setPriceAtBooking(Integer priceAtBooking) {
        this.priceAtBooking = priceAtBooking;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}