package com.example.movieweb.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movieweb.entity.Movie;
import com.example.movieweb.repository.MovieRepository;
import com.example.movieweb.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {
	@Autowired
	private MovieRepository movieRepository;

	@Override
	public List<Movie> findAllMovies() {
		return movieRepository.findAll();
	}

	@Override
	public Optional<Movie> findMovieByImdbId(String imdbId) {
		return movieRepository.findMovieByImdbId(imdbId);
	}

}
