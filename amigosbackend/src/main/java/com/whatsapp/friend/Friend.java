package com.whatsapp.friend;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.whatsapp.user.User;

@Entity 
public class Friend {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@OneToOne
	@JoinColumn(name="userId")
	private User userId;
	@ManyToOne
	@JoinColumn(name="friendId")
	private User friendId;
	private String status;
	private boolean isOnline;
	
	private boolean isMe;
	
	public boolean isMe() {
		return isMe;
	}
	public void setMe(boolean isMe) {
		this.isMe = isMe;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public User getUserId() {
		return userId;
	}
	public void setUserId(User userId) {
		this.userId = userId;
	}
	public User getFriendId() {
		return friendId;
	}
	public void setFriendId(User friendId) {
		this.friendId = friendId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	
	
}