package com.springnews.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springnews.entity.Votes;

@Repository
public interface VotesRepository extends JpaRepository<Votes, Integer>{
	
	Votes findByNewsIdAndUser(int newsId, String user);
	
	List<Votes> findByNewsId(int newsId);
	
}
