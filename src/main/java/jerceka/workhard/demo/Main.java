package jerceka.workhard.demo;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Main {
	@Autowired
	private Ser service;
	private String owner;
	Date date = new Date();
	@RequestMapping("/")
	public ModelAndView Home(Model m,Performance p) {
		long start = System.currentTimeMillis();
		List<Posts> po = service.allPosts();
		m.addAttribute("po", po);
		ModelAndView mv = new ModelAndView("index");
		long end = System.currentTimeMillis();
		long d = end - start;
		p.setDate(date.toString());
		p.setMethod("HomePage");
		p.setTime(d);
		service.savePerformance(p);
		return mv;
	}
	@RequestMapping("/login")
	public ModelAndView openPersonlPage(Model m,@RequestParam String name,
			@RequestParam String password,Performance p) {
		if(service.checkLogin(name, password)) {
			long start = System.currentTimeMillis();
			this.owner = name;
			List<Account> Acc = service.getOneAccount(name);
			m.addAttribute("personalAccount", Acc);
			m.addAttribute("greeting", service.greeting(name));
			ModelAndView mv = new ModelAndView("Personal");
			long end = System.currentTimeMillis();
			long d = end - start;
			p.setDate(date.toString());
			p.setMethod("OpenPeronslPage");
			p.setTime(d);
			service.savePerformance(p);
			return mv;
		}else {
			ModelAndView mv = new ModelAndView("index");
			return mv;
		}
	}
	@RequestMapping("/sendToCreatePage")
	public ModelAndView sendToCreatePage(Performance p) {
		long start = System.currentTimeMillis();
		ModelAndView mv = new ModelAndView("create");
		long end = System.currentTimeMillis();
		long d = end - start;
		p.setDate(date.toString());
		p.setMethod("SendToCreatePage");
		p.setTime(d);
		service.savePerformance(p);
		return mv;
	}	
	@RequestMapping("/createAccount")
	public void MakeAccount(Account a,Performance p){
			long start = System.currentTimeMillis();
			service.makeAccount(a);
			long end = System.currentTimeMillis();
			long d = end - start;
			p.setDate(date.toString());
			p.setMethod("MakeAccount");
			p.setTime(d);
			service.savePerformance(p);
	}
}
