package com.demows.lbvie.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demows.lbvie.dao.BookRepo;
import com.demows.lbvie.dtos.BookDTO;
import com.demows.lbvie.exceptions.NoDataFoundException;
import com.demows.lbvie.service.BookService;
import com.demows.lbvie.utils.JsonUtil;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(BookController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookControllerTests {

    private static final Logger log = LoggerFactory.getLogger(BookControllerTests.class);

	private final static String URI = "/api";
	
	@MockBean
	BookRepo repository;

	@MockBean
	BookService service;

	@Autowired
	MockMvc mockMvc;
	
	List<BookDTO> dtos;
	
	@BeforeAll
	public void init() {
		log.info(" ---- BeforeEach BookControllerTests ---- ");

		dtos = Arrays.asList(new BookDTO("1", "Php", "Sun", new BigDecimal(100), "Casa"));
	}
	
	
	@AfterAll
	public void afterEach() {
		log.info(" ---- AfterEach BookControllerTests ---- ");
	}
	

	@Test
	@Order(1)
	public void testfindAll() throws Exception {

		Mockito.when(service.findAll()).thenReturn(dtos);

		mockMvc.perform(get(URI + "/books"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", Matchers.greaterThan(0)))
				.andExpect(jsonPath("$[0].book_title", Matchers.is("Php")));

		Assertions.assertThat(service.findAll()).isNotEmpty();

//	     this.mockMvc
//			.perform(MockMvcRequestBuilders.get("/{id}", 1)
//					.accept(MediaType.APPLICATION_JSON))
//			.andDo(print()).andExpect(status().isOk())
//			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));

	}

	@Test
	@Order(2)
	public void testCreate() throws Exception {

		BookDTO dto = new BookDTO();
		dto.setId("10");
		dto.setBook_autor("Adnane");
		dto.setBook_title("JAVA");
		dto.setBook_amount(new BigDecimal(1000));
		dto.setLibrary_name("RABAT JAVA");

		//when(service.create(ArgumentMatchers.any(BookDTO.class))).thenReturn(dto);
		
        //doReturn(dto).when(service).create(dto);
        doReturn(dto).when(service).create(any(BookDTO.class));

		mockMvc.perform(post(URI + "/book/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJsonByte(dto)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.book_title", is(dto.getBook_title())));
	}

	/*
	 * Find Test
	 */

	@Test
	@Order(3)
	public void testFindWhenisExist() throws Exception {
		BookDTO dto = dtos.get(0);
		
        doReturn(dto).when(service).find(dto.getId());


		mockMvc.perform(get(URI + "/book/" + dto.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound())
				.andExpect(jsonPath("id", is(dto.getId())));

	}
	
	@Test
	@Order(4)
	public void testFindWhenNotExist() throws Exception {
		BookDTO dto = dtos.get(0);

		Mockito.doThrow(new NoDataFoundException(dto.getId())).when(service).find(dto.getId());

		mockMvc.perform(get(URI + "/book/" + dto.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

	}
	
	/*
	 * Update Test
	 */


	@Test
	@Order(5)
	public void testUpdateWhenisExist() throws Exception {

		BookDTO dto = new BookDTO();
		dto.setId("10");
		dto.setBook_autor("Adnane");
		dto.setBook_title("JAVA");
		dto.setBook_amount(new BigDecimal(1000));
		dto.setLibrary_name("RABAT JAVA");
		
		when(service.update(dto, dto.getId())).thenReturn(dto);

		mockMvc.perform(put(URI+ "/book/update/" + dto.getId())
				.content(JsonUtil.toJsonByte(dto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id", is(dto.getId())));
		}

	@Test
	@Order(6)
	public void testUpdateWhenNotExist() throws Exception {
		BookDTO dto = dtos.get(0);

		Mockito.doThrow(new NoDataFoundException(dto.getId())).when(service).update(dto, dto.getId());
		
		mockMvc.perform(put("/book/update/" + dto.getId())
				.content(JsonUtil.toJsonByte(dto))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	/*
	 * Delete Test
	 */
	@Test
	@Order(7)
	public void testDeleteWhenIsExist() throws Exception {
		BookDTO dto = dtos.get(0);

        when(service.find(dto.getId())).thenReturn(dto);

		mockMvc.perform(delete(URI + "/book/delete/" + dto.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

	}

	@Test
	@Order(8)
	public void testDeleteWhenNotExist() throws Exception {
		BookDTO dto = dtos.get(0);

		Mockito.doThrow(new NoDataFoundException(dto.getId())).when(service).remove(dto.getId());

		mockMvc.perform(delete(URI + "/book/delete/" + dto.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());


	}

}
