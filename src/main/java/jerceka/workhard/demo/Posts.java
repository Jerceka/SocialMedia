package jerceka.workhard.demo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Posts {
	@Id
	private int postid;
	private int likes;
	private int owner;
	private String post;
	public int getPostId() {
		return postid;
	}
	public void setPostId(int postId) {
		this.postid = postId;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public int getOwner() {
		return owner;
	}
	public void setOwner(int owner) {
		this.owner = owner;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
}
