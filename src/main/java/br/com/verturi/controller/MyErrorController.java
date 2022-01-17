package br.com.verturi.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

public class MyErrorController implements ErrorController {
	
	HttpHeaders headers;

	{
		headers = new HttpHeaders();
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
		headers.add("Access-Control-Allow-Headers", "Origin, Content-Type, X-Auth-Token");
	}
	
	@RequestMapping("/error")
	public ResponseEntity<String> handleError() {
		// do something like logging
		return new ResponseEntity<>("Erro!!!", headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
