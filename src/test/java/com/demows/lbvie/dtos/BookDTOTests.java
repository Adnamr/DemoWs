package com.demows.lbvie.dtos;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.demows.lbvie.entities.Book;
import com.demows.lbvie.mapper.LbvieMapperImpl;

@ExtendWith(MockitoExtension.class)
public class BookDTOTests {

//	@Mock
//    LbvieMapperImpl mapper;
	
	//private LbvieMapper mapper = Mappers.getMapper(LbvieMapper.class);
	
	
	@InjectMocks
	LbvieMapperImpl impl;
	
    
	@Test
	public void convertEntityToDTO() {
		Book entity = new Book(1L,"Java","Adnane",new BigDecimal(100),"US JAVA LB");
		
		BookDTO dto = null;
		
        dto = impl.BookToDto(entity);
		Assertions.assertEquals(entity.getBook_title(), dto.getBook_title());
		Assertions.assertEquals(entity.getBook_autor(), dto.getBook_autor());
		Assertions.assertEquals(entity.getBook_amount(), dto.getBook_amount());
		Assertions.assertEquals(entity.getLibrary_name(), dto.getLibrary_name());

	}

	@Test
	public void convertDTOToEntity() {
		BookDTO dto = new BookDTO("1","Java","Adnane",new BigDecimal(100),"US JAVA LB");
	
		Book entity = impl.DtoToBook(dto);
		Assertions.assertEquals(dto.getBook_title(), entity.getBook_title());
		Assertions.assertEquals(dto.getBook_autor(), entity.getBook_autor());
		Assertions.assertEquals(dto.getBook_amount(), entity.getBook_amount());
		Assertions.assertEquals(dto.getLibrary_name(), entity.getLibrary_name());
	}

}
