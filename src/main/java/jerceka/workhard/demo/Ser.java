package jerceka.workhard.demo;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Ser {
	Date date = new Date();
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
	@Transactional
	public boolean checkName(String name) {
		return account.existsByName(name);
	}
	@SuppressWarnings("deprecation")
	public String greeting(String name) {
		if(date.getHours()>=4&&date.getHours()<17) {
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
	public void createPost(Posts s) {
		post.save(s);
	}
	public void savcePerformance(Performance p,long start,long end
			,String methodName) {
			long d = end - start;
			p.setDate(date.toString());
			p.setMethod(methodName);
			p.setTime(d);
			performance.save(p);
	}
}
