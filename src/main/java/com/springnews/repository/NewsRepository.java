package com.springnews.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springnews.entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer>{

	List<News> findAllByOrderByIdDesc();
	
}
