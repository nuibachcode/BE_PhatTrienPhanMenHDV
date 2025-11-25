package com.smilecare.booking_service.entity;

import jakarta.persistence.*;
import java.util.HashSet; // Import
import java.util.Set; // Import

@Entity
@Table(name = "service")
public class Service {
    @Id
    private Integer id;

    // Thêm cột Price (Giả sử tên cột là 'Price')
    @Column(name = "Price")
    private Long price;

    // Thêm liên kết ngược
    @OneToMany(mappedBy = "service")
    private Set<BookingServiceAssociation> bookings = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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