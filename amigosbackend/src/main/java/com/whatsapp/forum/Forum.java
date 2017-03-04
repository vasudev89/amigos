package com.whatsapp.forum;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.whatsapp.user.User;

@Entity
public class Forum {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long forumId;
	private String title;
	
		
	@ManyToOne
	@JoinColumn(name="userId")
	private User userId;
	
	
	private Date forumdate;
	
	private boolean posted=true;

	
	//getters and setters
	
	public long getForumId() {
		return forumId;
	}

	public void setForumId(long forumId) {
		this.forumId = forumId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Date getForumdate() {
		return forumdate;
	}

	public void setForumdate(Date forumdate) {
		this.forumdate = forumdate;
	}

	public boolean isPosted() {
		return posted;
	}

	public void setPosted(boolean posted) {
		this.posted = posted;
	}

	
	
}