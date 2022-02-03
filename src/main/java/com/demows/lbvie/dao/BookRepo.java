package com.demows.lbvie.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demows.lbvie.entities.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {

}
