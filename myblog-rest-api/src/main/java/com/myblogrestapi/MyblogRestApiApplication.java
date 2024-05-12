package com.myblogrestapi;

import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.crypto.SecretKey;

@SpringBootApplication
public class MyblogRestApiApplication {
	public static void main(String[] args)
	{
		SpringApplication.run(MyblogRestApiApplication.class, args);
	}


}
