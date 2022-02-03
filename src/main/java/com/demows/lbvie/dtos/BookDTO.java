package com.demows.lbvie.dtos;

import java.math.BigDecimal;

import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDTO {
	
	
	@Id
	private String id;
	
	@JsonProperty
	@NotBlank
	private String book_title;
	
	@JsonProperty
	@NotBlank
	private String book_autor;
	
	@Min(value = 100)
	@JsonProperty
	private BigDecimal book_amount;

	@JsonProperty
	private String library_name;

}
