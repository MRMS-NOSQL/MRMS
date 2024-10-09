package com.example.movieweb.service;

import java.util.List;
import java.util.Optional;

import com.example.movieweb.entity.Movie;

public interface MovieService {
	List<Movie> findAllMovies();
	Optional<Movie> findMovieByImdbId(String imdbId);
}
