package jerceka.workhard.demo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Likes {
	@Id
	private int id;
	private int postid;
	private int likeownerid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPostid() {
		return postid;
	}
	public void setPostid(int postid) {
		this.postid = postid;
	}
	public int getLikeownerid() {
		return likeownerid;
	}
	public void setLikeownerid(int likeownerid) {
		this.likeownerid = likeownerid;
	}
}
