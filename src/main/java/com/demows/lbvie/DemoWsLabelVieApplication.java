package com.demows.lbvie;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.demows.lbvie.dao.BookRepo;
import com.demows.lbvie.entities.Book;

@SpringBootApplication
public class DemoWsLabelVieApplication implements CommandLineRunner {

	@Autowired
	private BookRepo repository;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoWsLabelVieApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		
		repository.save(new Book(1L,"JAva","Sun",new BigDecimal(100),"Casablanca Lb"));
	}
	
	

}
