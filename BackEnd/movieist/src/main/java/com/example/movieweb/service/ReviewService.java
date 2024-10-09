package com.example.movieweb.service;

import com.example.movieweb.entity.Review;

public interface ReviewService {
	Review createReview(String reviewBody, String imdbId);
	Review updateReview(String id, String newReviewBody);
	void deleteReview(String id);
}
