package com.example.movieweb.service.impl;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.movieweb.entity.Movie;
import com.example.movieweb.entity.Review;
import com.example.movieweb.repository.ReviewRepository;
import com.example.movieweb.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Review createReview(String reviewBody, String imdbId) {
		Review review = reviewRepository.insert(new Review(reviewBody, LocalDateTime.now(), LocalDateTime.now()));

		mongoTemplate.update(Movie.class).matching(Criteria.where("imdbId").is(imdbId))
				.apply(new Update().push("reviewIds").value(review.getId())).first();
		return review;
	}

	@Override
	public Review updateReview(String id, String newReviewBody) {
		// Chuyển đổi id từ String sang ObjectId
		ObjectId objectId = new ObjectId(id);
		// Tạo một đối tượng Update để cập nhật nội dung review và thời gian cập nhật
		Update update = new Update().set("reviewBody", newReviewBody).set("updatedAt", LocalDateTime.now());
		// Sử dụng mongoTemplate để cập nhật review
		mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(objectId)), // Tìm review theo ObjectId
				update, Review.class // Đối tượng cần cập nhật
		);

		// Lấy lại review đã được cập nhật để trả về
		return mongoTemplate.findById(objectId, Review.class);
	}

	@Override
	public void deleteReview(String id) {
		// Chuyển đổi id từ String sang ObjectId
		ObjectId objectId = new ObjectId(id);
		// Tìm review bằng mongoTemplate và xóa
		Review reviewToDelete = mongoTemplate.findAndRemove(Query.query(Criteria.where("_id").is(objectId)),
				Review.class);
		// Nếu review tồn tại, cập nhật Movie để loại bỏ review khỏi danh sách reviewIds
		if (reviewToDelete != null) {
			mongoTemplate.update(Movie.class).matching(Criteria.where("reviewIds").is(objectId))
					.apply(new Update().pull("reviewIds", objectId)) // Loại bỏ reviewId khỏi danh sách
					.first();
		} else {
			throw new RuntimeException("Review not found");
		}
	}

}
