package com.springnews.controller;


import java.util.concurrent.Callable;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.springnews.entity.News;
import com.springnews.service.NewsService;
import com.springnews.service.VotingService;

@Controller
public class NewsController {
	
	@Autowired
	private NewsService newsService;		
	
	@Autowired
	private VotingService voting;
	
	private boolean initDone = false;
	
	@ModelAttribute("name")
	public String name(Authentication auth){
		if(auth != null)
			return auth.getName();
		return null;
	}
	
	@RequestMapping("/")
	public String index(Model model, Authentication auth){
		if(auth != null){
			voting.setColors(auth.getName());
		}
		if(!initDone){
			newsService.init();
			initDone = true;
		}			
		model.addAttribute("news", newsService.getAllNews());
		return "index";
	}
	
	@GetMapping("/upvote/{id}")
	@ResponseBody
	public String upvote(@PathVariable int id, Authentication auth){
		return voting.upvote(id, auth.getName());
	}
	
	@GetMapping("/downvote/{id}")
	@ResponseBody
	public String downvote(@PathVariable int id, Authentication auth){
		return voting.downvote(id, auth.getName());
	}	
	
	@GetMapping("/remove/{id}")
	@ResponseBody
	public void removeNewsById(@PathVariable int id, Model model, Authentication auth){
		if(newsService.getNewsById(id).getAuthor().equals(auth.getName()) || auth.getName().equals("admin"))
			newsService.removeNewsById(id);
		voting.setColors(auth.getName());
	}
	
	@GetMapping("/login")
	public String login(){
		return "login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session){
		if(session != null)
			session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/create")
	public String create(Model model){
		model.addAttribute("news", new News());
		return "create";
	}
	
	@PostMapping("/create")
	public String addNews(@Valid @ModelAttribute News news, BindingResult br, Authentication auth){
		if(br.hasErrors())
			return "create";
		newsService.addNews(news, auth.getName());
		return "redirect:/";
	}
	
	@GetMapping("/pollnews")
	@ResponseBody
	public Callable<Object> poll(Model model){
		return new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				int init = newsService.getAllNews().size();
				int count = 0;
				while(count < 151){
					
					if(newsService.getNewsCount() > init){
						model.addAttribute("news", newsService.getAllNews().get(0));
						return new ModelAndView("partial");
					}
					else if(init > newsService.getNewsCount()){
						return newsService.getDeletedId();
					}
					else{
						count++;
						Thread.sleep(200);
					}
				}
				return null;
			}
		};
	}
}