package jerceka.workhard.demo.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Likes {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
