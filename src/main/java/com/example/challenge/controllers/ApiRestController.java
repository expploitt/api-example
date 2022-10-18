package com.example.challenge.controllers;

import com.example.challenge.models.Activity;
import com.example.challenge.models.Booking;
import com.example.challenge.models.ClassActivity;
import com.example.challenge.requests.BookReq;
import com.example.challenge.requests.ClassActivityReq;
import com.example.challenge.respositories.ActivityRepository;
import com.example.challenge.respositories.BookingRepository;
import com.example.challenge.respositories.ClassActivityRepository;


import com.example.challenge.services.ClassActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.springframework.http.HttpStatus.*;

@RestController
public class ApiRestController {

    private final ClassActivityRepository classActivityRepository;

    private final ActivityRepository activityRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    private ClassActivityService service;

    Logger logger = LoggerFactory.getLogger(ApiRestController.class);


    public ApiRestController(ClassActivityRepository classActivity, ActivityRepository activity, BookingRepository bookingRepository) {
        this.classActivityRepository = classActivity;
        this.activityRepository = activity;
        this.bookingRepository = bookingRepository;
    }

    @PostMapping(value = "/classes")
    public ResponseEntity<ClassActivity> createClass(@RequestBody ClassActivityReq classActivityReq) {

        if (classActivityReq != null) {
            ClassActivity classActivity = new ClassActivity(
                    classActivityReq.getName(),
                    classActivityReq.getStart_date(),
                    classActivityReq.getEnd_date(),
                    classActivityReq.getCapacity());
            if (this.service.createActivityClass(classActivity))
                return new ResponseEntity<>(classActivity, CREATED);
        }

        return new ResponseEntity<>(null, BAD_REQUEST);
    }

    @PostMapping(value = "/bookings")
    public ResponseEntity<String> bookings(@RequestBody BookReq bookReq) {

        if (bookReq != null) {
            if (bookReq.getName() != null && bookReq.getDate() != null) {
                Booking book = new Booking(bookReq.getName(), bookReq.getDate());
                if (this.service.bookActivity(book)) {
                    return new ResponseEntity<>("Booking created successfully", CREATED);
                } else {
                    return new ResponseEntity<>("Activity not booked, there isn't activity in that date", BAD_REQUEST);
                }
            }
        }
        return new ResponseEntity<>("Booking request not valid", BAD_REQUEST);
    }

    @GetMapping(value = "classActivity")
    public ResponseEntity<List<ClassActivity>> getClassActivityByName(@RequestParam String name) {

        List<ClassActivity> activities = this.classActivityRepository.findAll();
        return new ResponseEntity<>(activities, OK);
    }
}
