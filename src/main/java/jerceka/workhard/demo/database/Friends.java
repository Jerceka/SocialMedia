package jerceka.workhard.demo.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Friends {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int friendsid;
	private int first;
	private int second;
	private int accept;
	public int getFriendsid() {
		return friendsid;
	}
	public void setFriendsid(int friendsid) {
		this.friendsid = friendsid;
	}
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}
	public int getAccept() {
		return accept;
	}
	public void setAccept(int accept) {
		this.accept = accept;
	}
}
