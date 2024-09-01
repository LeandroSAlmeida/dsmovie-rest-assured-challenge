package com.devsuperior.dsmovie.controllers;

import com.devsuperior.dsmovie.tests.TokenUtil;
import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class ScoreControllerRA {

	private String adminUsername, adminPassword;
	private String adminToken;
	private Map<String, Object> postScoreInstance;

	@BeforeEach
	void setUp() throws Exception{
		baseURI = "http://localhost:8080";
		adminUsername = "maria@gmail.com";
		adminPassword = "123456";
		adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);

		postScoreInstance = new HashMap<>();
		postScoreInstance.put("movieId", 1L);
		postScoreInstance.put("score", 4);
	}
	
	@Test
	public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() throws Exception {
		postScoreInstance.put("movieId", 100L);
		JSONObject newScore = new JSONObject(postScoreInstance);

		given()
				.header("Content-type", "application/json")
				.header("Authorization", "Bearer " + adminToken)
				.body(newScore)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.put("/scores")
				.then()
				.statusCode(404);
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId() throws Exception {
		postScoreInstance.put("movieId", null);
		JSONObject newScore = new JSONObject(postScoreInstance);

		given()
				.header("Content-type", "application/json")
				.header("Authorization", "Bearer " + adminToken)
				.body(newScore)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.put("/scores")
				.then()
				.statusCode(422);
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() throws Exception {
		postScoreInstance.put("score", -1);
		JSONObject newScore = new JSONObject(postScoreInstance);

		given()
				.header("Content-type", "application/json")
				.header("Authorization", "Bearer " + adminToken)
				.body(newScore)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.put("/scores")
				.then()
				.statusCode(422);
	}
}
