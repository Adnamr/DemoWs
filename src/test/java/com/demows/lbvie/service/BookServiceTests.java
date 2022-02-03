package com.demows.lbvie.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demows.lbvie.dao.BookRepo;
import com.demows.lbvie.dtos.BookDTO;
import com.demows.lbvie.entities.Book;
import com.demows.lbvie.exceptions.NoDataFoundException;
import com.demows.lbvie.mapper.LbvieMapperImpl;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
	
    private static final Logger log = LoggerFactory.getLogger(BookServiceTests.class);

	
	@Mock
	BookRepo repository;

	@Spy
	LbvieMapperImpl mapper;
	
	@InjectMocks
	BookService service;

	List<Book> list;
	
	@BeforeEach
	void init()
	{
		list = Arrays.asList(new Book(1L,"Java","Adnane",new BigDecimal(100),"US JAVA LB"));
	}


	@Test
    @DisplayName("Test findAll Books")
	public void findAll() throws NoDataFoundException
	{
		Mockito.lenient().doReturn(list).when(repository).findAll();
		
		List<BookDTO> books = service.findAll();
		
		assertThat(books).isNotEmpty();
		
		Assertions.assertEquals(books.get(0).getBook_title(), list.get(0).getBook_title());
		
		verify(repository, times(1)).findAll();
	}
	
	
	@Test
    @DisplayName("Test find Book")
	public void findId() throws NoDataFoundException
	{
		long id = 1L;
		
		Book entity = list.get(0);
		
		Assertions.assertNotNull(entity);
		
		doReturn(Optional.of(entity)).when(repository).findById(entity.getId());
				
		BookDTO dto = service.find(String.valueOf(id));
		
		Assertions.assertEquals(dto.getBook_title(), entity.getBook_title());
		
	}
	
	@Test
    @DisplayName("Test save Book")
	public void save()
	{
		Book book = list.get(0);

		BookDTO booktmp = mapper.BookToDto(book);
		
		doReturn(book).when(repository).save(ArgumentMatchers.any());
		
		BookDTO dto = service.create(booktmp);
		
		Assertions.assertNotNull(dto);
		
		Assertions.assertEquals(book.getBook_autor(), book.getBook_autor());
		
	}
	
	
	@Test
    @DisplayName("Test update Book")
	public void update() throws NumberFormatException, NoDataFoundException
	{
		Book book = list.get(0);
		
		BookDTO booktmp = mapper.BookToDto(book);
		
		doReturn(Optional.of(book)).when(repository).findById(book.getId());
		
		BookDTO dto = service.update(booktmp,booktmp.getId());
		
		Assertions.assertNotNull(dto);
		
		Assertions.assertEquals(book.getBook_autor(), book.getBook_autor());
		
	}
	
	
	
	

}
