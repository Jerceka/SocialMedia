package jerceka.workhard.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Ser {
	@Autowired
	private PostsRepo post;
	@Autowired
	private AccountRepo accountt;
	public List<Account> allAccount() {
		return accountt.findAll();
	}
	public List<Posts> allPosts(){
		return post.findAll();
	}
}
