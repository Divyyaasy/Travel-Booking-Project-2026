package com.example.travel.service;

import com.example.travel.model.Booking;
import com.example.travel.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    // Save Booking
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    // Get All Bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Get Booking By ID
    public Booking getBookingById(Integer id) {
        return bookingRepository.findById(id).orElse(null);
    }

    // Update Booking
    public Booking updateBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    // Delete Booking
    public void deleteBooking(Integer id) {
        bookingRepository.deleteById(id);
    }

}
