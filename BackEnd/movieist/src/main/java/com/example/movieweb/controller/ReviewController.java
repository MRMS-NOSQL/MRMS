package com.example.movieweb.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.movieweb.entity.Review;
import com.example.movieweb.service.ReviewService;

@RestController
@RequestMapping("/api/v1/admin")
public class ReviewController {
    @Autowired
    private ReviewService service;

    @PostMapping()
    public ResponseEntity<Review> createReview(@RequestBody Map<String, String> payload) {

        return new ResponseEntity<Review>(service.createReview(payload.get("reviewBody"), payload.get("imdbId")), HttpStatus.OK);
    }
    
    @PutMapping("/reviews/update/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable String id, @RequestBody Map<String, String> payload) {
        return new ResponseEntity<>(service.updateReview(id, payload.get("reviewBody")), HttpStatus.OK);
    }

    @DeleteMapping("/reviews/delete/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable String id) {
        service.deleteReview(id);
        return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }

}
