package com.example.travel.controller;

import com.example.travel.model.Booking;
import com.example.travel.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Home Page
    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute("bookings", bookingService.getAllBookings());

        return "index";
    }

    // Display All Bookings
    @GetMapping("/bookings")
    public String getAllBookings(Model model) {

        model.addAttribute("bookings", bookingService.getAllBookings());

        return "index";
    }

    // Show Add Booking Form
    @GetMapping("/add")
    public String addBookingForm(Model model) {

        model.addAttribute("booking", new Booking());

        return "addBooking";
    }

    // Save Booking
    @PostMapping("/save")
    public String saveBooking(@ModelAttribute Booking booking) {

        bookingService.saveBooking(booking);

        return "redirect:/bookings";
    }

    // Show Edit Form
    @GetMapping("/edit/{id}")
    public String editBooking(@PathVariable Integer id, Model model) {

        Booking booking = bookingService.getBookingById(id);

        model.addAttribute("booking", booking);

        return "updateBooking";
    }

    // Update Booking
    @PostMapping("/update")
    public String updateBooking(@ModelAttribute Booking booking) {

        bookingService.updateBooking(booking);

        return "redirect:/bookings";
    }

    // Delete Booking
    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Integer id) {

        bookingService.deleteBooking(id);

        return "redirect:/bookings";
    }

}
