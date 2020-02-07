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
	Date date = new Date();
	@Autowired
	private Ser service;
	private String owner;
	@RequestMapping("/")
	public ModelAndView Home(Model m,Performance p) {
		long start = System.currentTimeMillis();
		List<Posts> po = service.allPosts();
		m.addAttribute("po", po);
		ModelAndView mv = new ModelAndView("index");
		long end = System.currentTimeMillis();
		service.savcePerformance(p,start, end, "HomePage");
		return mv;
	}
	@RequestMapping("/login")
	public ModelAndView openPersonlPage(Model m,@RequestParam String name,
			@RequestParam String password,Performance p) {
		if(service.checkLogin(name, password)) {
			long start = System.currentTimeMillis();
			owner = name;
			List<Account> Acc = service.getOneAccount(name);
			m.addAttribute("personalAccount", Acc);
			m.addAttribute("greeting", service.greeting(name));
			ModelAndView mv = new ModelAndView("Personal");
			long end = System.currentTimeMillis();
			service.savcePerformance(p,start, end, "OpenPeronslPage");
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
		service.savcePerformance(p,start, end, "SendToCreatePage");
		return mv;
	}	
	@RequestMapping("/createAccount")
	public ModelAndView MakeAccount(Model m,Account a,Performance p){
			if(!service.checkName(a.getName())) {
				long start = System.currentTimeMillis();
				service.makeAccount(a);
				List<Account> Acc = service.getOneAccount(a.getName());
				owner = a.getName();
				m.addAttribute("personalAccount", Acc);
				m.addAttribute("greeting", service.greeting(owner));
				System.out.println(Acc.get(0).getPersonId());
				ModelAndView mv = new ModelAndView("Personal");
				long end = System.currentTimeMillis();
				service.savcePerformance(p,start, end, "MakeAccount");
				return mv;
			}else {
				ModelAndView mv = new ModelAndView("create");
				return mv;
			}
	}
	@RequestMapping("/sendToPostPage")
	public ModelAndView goToPostPage(Performance p){
		long start = System.currentTimeMillis();
		ModelAndView mv = new ModelAndView("post");
		long end = System.currentTimeMillis();
		service.savcePerformance(p,start, end, "SendToPostPage");
		return mv;
	}
	@RequestMapping("/createPost")
	public ModelAndView makePost(Model m,Account a,Posts s,Performance p){
		long start = System.currentTimeMillis();
		List<Account> account = service.getOneAccount(owner);
		s.setDate(date.toString());
		int postOwner = account.get(0).getPersonId();
		s.setOwner(postOwner);
		service.createPost(s);
		m.addAttribute("personalAccount", account);
		ModelAndView mv = new ModelAndView("Personal");
		long end = System.currentTimeMillis();
		service.savcePerformance(p,start, end, "MakePost");
		return mv;
	}
}
