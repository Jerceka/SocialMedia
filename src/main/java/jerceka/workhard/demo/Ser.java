package jerceka.workhard.demo;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Ser {
	@Autowired
	private PostsRepo post;
	@Autowired
	private AccountRepo account;
	@Autowired
	private PerformanceRepo performance;
	public List<Posts> allPosts(){
		return post.findAll();
	}
	public void setPerformane() {
		performance.all();
	}
	@Transactional
	public boolean checkLogin(String name,String password) {
		if(account.existsByName(name) && account.existsByPassword(password)) {
			String truePass = account.findByName(name).get(0).getPassword();
			if(truePass.equals(password)) {
				return true;
			}
			else {
				return false;
			}
		}else {
			return false;
		}
	}
	@SuppressWarnings("deprecation")
	public String greeting(String name) {
		Date time = new Date();
		if(time.getHours()>=4&&time.getHours()<17) {
			return "enjoy your day " + name;
		}else{
			return "Good your night " + name;
		}
	}
	@Transactional
	public List<Account> getOneAccount(String name){
		return account.findByName(name);
	}
	public void makeAccount(Account a) {
		account.save(a);
	}
}
