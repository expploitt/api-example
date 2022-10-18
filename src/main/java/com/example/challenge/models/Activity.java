package com.example.challenge.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_classActivity")
    private ClassActivity classActivity;
    private String name;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate activityDay;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "activity")
    private Set<Booking> bookingSet;

    public Activity() {
    }

    public Activity(String name, LocalDate activityDay, int capacity) {
        this.name = name;
        this.activityDay = activityDay;
        this.capacity = capacity;
        this.bookingSet = new HashSet<Booking>();
    }

    private int capacity;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public LocalDate getActivityDay() {
        return activityDay;
    }

    public void setActivityDay(LocalDate activityDay) {
        this.activityDay = activityDay;
    }

    public Set<Booking> getBookingSet() {
        return bookingSet;
    }

    public void setBookingSet(Set<Booking> bookingSet) {
        this.bookingSet = bookingSet;
    }

//    public ClassActivity getClassActivity() {
//        return classActivity;
//    }
//
//    public void setClassActivity(ClassActivity classActivity) {
//        this.classActivity = classActivity;
//    }
}
