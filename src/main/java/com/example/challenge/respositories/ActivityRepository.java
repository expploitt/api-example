package com.example.challenge.respositories;

import com.example.challenge.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query("select u from Activity u where u.activityDay = :#{#date}")
    Activity findActivityByDate(@Param("date") LocalDate date);
}