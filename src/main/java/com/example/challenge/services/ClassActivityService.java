package com.example.challenge.services;

import com.example.challenge.models.Activity;
import com.example.challenge.models.Booking;
import com.example.challenge.models.ClassActivity;
import com.example.challenge.respositories.ActivityRepository;
import com.example.challenge.respositories.BookingRepository;
import com.example.challenge.respositories.ClassActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ClassActivityService {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private ClassActivityRepository classActivityRepository;


    public Boolean createActivityClass(ClassActivity classActivity) {
        if (classActivity.getStart_date().isBefore(classActivity.getEnd_date())) {
            long days = DAYS.between(classActivity.getStart_date(), classActivity.getEnd_date());
            LocalDate day = classActivity.getStart_date();

            for (int i = 0; i < days; i++) {
                Activity act = new Activity(classActivity.getName(), day, classActivity.getCapacity());
                day = day.plusDays(1);
                classActivity.getActivitySet().add(act);
            }
            this.classActivityRepository.save(classActivity);
        } else {
            return false;
        }
        return true;
    }

    public boolean bookActivity(Booking booking) {
        Activity activity = this.activityRepository.findActivityByDate(booking.getBookingDate());

        if (activity != null) {
            activity.getBookingSet().add(booking);
            this.activityRepository.save(activity);
            return true;
        }
        return false;
    }
}