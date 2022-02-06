package com.demows.lbvie;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.demows.lbvie.dao.BookRepo;
import com.demows.lbvie.entities.Book;

@SpringBootApplication
public class DemoWsLabelVieApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(DemoWsLabelVieApplication.class, args);
	}

	
	@Bean
	CommandLineRunner start(BookRepo repository) 
	{
		//repository.save(new Book(1L,"JAva","Sun",new BigDecimal(100),"Casablanca Lb"));
		return args -> {
			Stream.of("JAVA","PHP","GO","JS").forEach(book_title -> 
				repository.save(new Book(Long.valueOf((int) ((Math.random()+1)*100)),book_title,"Book Autor",new BigDecimal(Math.random()*100),"Library Lang"))
			);
		};

	}
	
	

}
