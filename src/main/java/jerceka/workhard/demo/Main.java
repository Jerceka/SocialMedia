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
		long t = 0;
		long[] d = new long[100000];
		for(int i=0;i<d.length;i++) {
			long start = System.nanoTime();
			here(m);
			long end = System.nanoTime();
			t = (end - start) / 1000000;
			long r = t++;
			d[i] = r;
		}
		long sum = 0;
		for(int i=0;i<d.length;i++) {
			sum = sum + d[i];
		}
		System.out.println("Total time with Second : "+sum/1000);
		System.out.println("Total Average time with Millisecond : "+sum/100000);
		return here(m);
	}
	public ModelAndView here(Model m) {
		List<Account> Acc = service.allAccount();
		m.addAttribute("acc", Acc);
		List<Posts> Po = service.allPosts();
		m.addAttribute("po", Po);
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	
}
