package jerceka.workhard.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Main {
	@Autowired
	private Ser service;
	@RequestMapping("/")
	public ModelAndView Home(Model m) {
		List<Account> Acc = service.allAccount();
		m.addAttribute("acc", Acc);
		List<Posts> Po = service.allPosts();
		m.addAttribute("po", Po);
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
}
