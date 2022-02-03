package com.demows.lbvie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demows.lbvie.dao.BookRepo;
import com.demows.lbvie.dtos.BookDTO;
import com.demows.lbvie.entities.Book;
import com.demows.lbvie.exceptions.NoDataFoundException;
import com.demows.lbvie.mapper.LbvieMapper;


@Service
public class BookService {
	
	@Autowired
	private BookRepo repository;
	
	@Autowired
	private LbvieMapper mapper;

	
	public BookService(BookRepo repository, LbvieMapper mapper) {
		super();
		this.repository = repository;
		this.mapper = mapper;
	}

	public List<BookDTO> findAll() throws NoDataFoundException
	{
		List<BookDTO> dtos = mapper.ListBookToDTO(repository.findAll());
		if (dtos.isEmpty()) 
		{
			throw new NoDataFoundException("Books list not found");
		}
		
		return dtos;
	}
	
	public BookDTO create(BookDTO dto)
	{
		return mapper.BookToDto(repository.save(mapper.DtoToBook(dto)));
	}
	
	public BookDTO update(BookDTO dto,String id) throws NumberFormatException, NoDataFoundException
	{
		Book book = repository.findById(Long.valueOf(id)).orElseThrow(() ->  new NoDataFoundException(id + " Not found"));
		
		book = mapper.DtoToBook(dto);
		book.setId(Long.valueOf(id));
		
		repository.save(book);
		
		return mapper.BookToDto(book);
	}
	
	public BookDTO find(String id) throws NoDataFoundException
	{
		Book book;
		
		book = repository.findById(Long.valueOf(id)).orElseThrow(() ->  new NoDataFoundException(id + " Not found"));
		BookDTO dto = mapper.BookToDto(book);
		return dto;
	
		
	}
	
	
	public void remove(String id) throws NumberFormatException, NoDataFoundException
	{
		Book book = repository.findById(Long.valueOf(id)).orElseThrow(() ->  new NoDataFoundException(id + " Not found"));
		repository.deleteById(book.getId());

	}
	
	
	public void remove(BookDTO dto) throws NumberFormatException, NoDataFoundException
	{
		repository.delete(mapper.DtoToBook(dto));
	}

}
