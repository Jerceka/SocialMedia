package jerceka.workhard.demo;

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
//	@RequestMapping("/")
	public ModelAndView Home(Model m) {
		List<Posts> po = service.allPosts();
		m.addAttribute("po", po);
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	@RequestMapping("/login")
	public ModelAndView openPersonlPage(Model m,@RequestParam String name,@RequestParam String password) {
		if(service.checkLogin(name, password)) {
			this.owner = name;
			List<Account> Acc = service.getOneAccount(name);
			m.addAttribute("personalAccount", Acc);
			m.addAttribute("greeting", service.greeting(name));
			ModelAndView mv = new ModelAndView("Personal");
			return mv;
		}else {
			ModelAndView mv = new ModelAndView("index");
			return mv;
		}
	}
	@RequestMapping("/sendToCreatePage")
	public ModelAndView sendToCreatePage() {
		ModelAndView mv = new ModelAndView("create");
		return mv;
	}	
	@RequestMapping("/")
	public void MakeAccount(Account a){
		long sum = 0;
		long[] time = new long[100];
		for(long i : time) {
			long start = System.currentTimeMillis();
			a.setName("name");
			service.makeAccount(a);
			long end = System.currentTimeMillis();
			long d = end - start;
			sum += d;
		}  
		System.out.println(sum/1e+3);
	}
}
