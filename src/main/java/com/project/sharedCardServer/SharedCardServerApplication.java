package com.project.sharedCardServer;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedInputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
public class SharedCardServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(SharedCardServerApplication.class, args);
	}
}
