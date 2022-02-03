package com.demows.lbvie.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demows.lbvie.dtos.BookDTO;
import com.demows.lbvie.exceptions.NoDataFoundException;
import com.demows.lbvie.service.BookService;

@RestController
@RequestMapping("/api")
public class BookController  {
	
	@Autowired
	private BookService service;
	
	@GetMapping("/books")
	public List<BookDTO> list() throws NoDataFoundException
	{
		return service.findAll();
	}
	
	@PostMapping("book/add")
	public ResponseEntity<BookDTO> add(@Valid @RequestBody BookDTO dto)
	{
	
		service.create(dto);
		
		return new ResponseEntity<>(dto,HttpStatus.CREATED);
	
		
	}
	
	@GetMapping("/book/{id}")
	private ResponseEntity<BookDTO> find(@PathVariable String id) throws NoDataFoundException
	{
		BookDTO dto = service.find(id);
		
		return new ResponseEntity<>(dto,HttpStatus.FOUND);
	}
	
	
	@PutMapping("/book/update/{id}")
	private ResponseEntity<BookDTO> update(@PathVariable String id,@Valid @RequestBody BookDTO dto) throws NoDataFoundException
	{
		service.update(dto, id);
		
		return new ResponseEntity<>(dto,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/book/delete/{id}")
	private ResponseEntity<BookDTO> delete(@PathVariable String id) throws NoDataFoundException
	{
		service.find(id);
		
		service.remove(id);
		
		return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
	}
	
	

}
