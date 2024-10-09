package com.example.movieweb.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.movieweb.entity.Review;

@Repository
public interface ReviewRepository extends MongoRepository<Review, ObjectId> {
}
