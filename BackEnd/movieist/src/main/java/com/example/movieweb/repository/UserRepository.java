package com.example.movieweb.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.movieweb.entity.Role;
import com.example.movieweb.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	User findByRole(Role role);

}
