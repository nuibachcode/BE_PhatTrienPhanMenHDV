package com.smilecare.booking_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable // Báo cho JPA biết đây là một lớp nhúng
public class BookingServiceKey implements Serializable {

    @Column(name = "booking_id")
    private Integer bookingId;

    @Column(name = "service_id")
    private Integer serviceId;

    // Constructor rỗng
    public BookingServiceKey() {}

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
}