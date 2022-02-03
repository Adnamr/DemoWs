package com.demows.lbvie.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.demows.lbvie.dtos.BookDTO;
import com.demows.lbvie.entities.Book;

@Mapper(
	componentModel = "spring"
)
public interface LbvieMapper {
	
	BookDTO BookToDto(Book book);
	
	List<BookDTO> ListBookToDTO(List<Book> books);
	
	Book DtoToBook(BookDTO dto);
	
}
