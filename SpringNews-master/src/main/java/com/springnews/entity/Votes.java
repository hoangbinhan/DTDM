package com.springnews.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Votes {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int newsId;
	private String user;
	private int f;
	
	public Votes(){}
	
	public Votes(int newsId, String user, int f) {
		this.newsId = newsId;
		this.user = user;
		this.f = f;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getF() {
		return f;
	}
	public void setF(int f) {
		this.f = f;
	}
	public int getNewsId() {
		return newsId;
	}
	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}
}
