package com.springnews.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springnews.entity.News;
import com.springnews.entity.Votes;
import com.springnews.repository.VotesRepository;

@Service
public class VotingService {
	
	@Autowired
	NewsService newsService;
	
	@Autowired
	VotesRepository votesRepository;
	
	public String upvote(int id, String user){
		Votes vote = votesRepository.findByNewsIdAndUser(id, user);
		if(vote == null){
			votesRepository.save(new Votes(id, user, 1));
			newsService.upvote(id);
		}
		else{
			switch(vote.getF()){
			case 1:
				newsService.downvote(id);
				vote.setF(0);
				break;
			case 0:
				newsService.upvote(id);
				vote.setF(1);
				break;
			case -1:
				newsService.upvote(id);
				newsService.upvote(id);
				vote.setF(1);
				break;
			}
			votesRepository.save(vote);
		}
		return newsService.getVotes(id) + "," + (vote != null ? vote.getF() : 1);
	}
	
	public String downvote(int id, String user){
		Votes vote = votesRepository.findByNewsIdAndUser(id, user);
		if(vote == null){
			votesRepository.save(new Votes(id, user, -1));
			newsService.downvote(id);
		}
		else{
			switch(vote.getF()){
			case 1:
				newsService.downvote(id);
				newsService.downvote(id);
				vote.setF(-1);
				break;
			case 0:
				newsService.downvote(id);
				vote.setF(-1);
				break;
			case -1:
				newsService.upvote(id);
				vote.setF(0);
				break;
			}
			votesRepository.save(vote);
		}
		return newsService.getVotes(id) + "," + (vote != null ? vote.getF() : -1);
	}	
	
	public List<Votes> getAllVotes(){
		return votesRepository.findAll();
	}
	
	public void setColors(String user){
		List<Votes> votes = votesRepository.findAll().stream().filter((u) -> u.getUser().equals(user)).collect(Collectors.toList());   
		for(News n : newsService.getAllNews()){
			n.setColor(0);
			for(Votes v : votes)
				if(n.getId() == v.getNewsId())
					n.setColor(v.getF());

		}
	}

}
