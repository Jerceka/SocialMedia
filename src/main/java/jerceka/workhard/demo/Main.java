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
			service.loadPersonalPage(m, name);
			ModelAndView mv = new ModelAndView("Personal");
			long end = System.currentTimeMillis();
			service.savcePerformance(p,start, end, "OpenPersonalPage");
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
				owner = a.getName();
				service.loadPersonalPage(m, a.getName());
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
		s.setName(owner);
		int postOwner = account.get(0).getPersonId();
		s.setOwner(postOwner);
		service.createPost(s);
		service.loadPersonalPage(m, owner);
		ModelAndView mv = new ModelAndView("Personal");
		long end = System.currentTimeMillis();
		service.savcePerformance(p,start, end, "MakePost");
		return mv;
	}
	@RequestMapping("/goToHomePage")
	public ModelAndView HomePage(Model model,Performance p) {
		long start = System.currentTimeMillis();
		List<Account> account = service.allAccount();
		model.addAttribute("account", account);
		List<Posts> po = service.allPosts();
		model.addAttribute("po", po);
		ModelAndView mv = new ModelAndView("home");
		long end = System.currentTimeMillis();
		service.savcePerformance(p, start, end, "goToHomePage");
		return mv;
	}
	@RequestMapping("/addFriend")
	public ModelAndView addFriend(@RequestParam String name,Model model,Friends f
						,Performance p) {
		long start = System.currentTimeMillis();
		List<Account> whoSendFriendRequest = service.getOneAccount(owner);
		List<Account> whoReceiveFriendRequest = service.getOneAccount(name);
		int sender = whoSendFriendRequest.get(0).getPersonId();
		int approval = whoReceiveFriendRequest.get(0).getPersonId();
		if(!service.checkFriendship(sender, approval)&&sender!=approval) {
			f.setFirst(sender);
			f.setSecond(approval);
			f.setAccept(0);
			service.SaveFriendship(f);
		}
		ModelAndView mv = new ModelAndView("Personal");
		service.loadPersonalPage(model, owner);
		Long end = System.currentTimeMillis();
		service.savcePerformance(p, start, end, "AddFriend");
		return mv;
	}
	@RequestMapping("/goToAcceptFriendshipPage")
	public ModelAndView acceptFriend(Performance p,Model model) {
		long start = System.currentTimeMillis();
		ModelAndView mv = new ModelAndView("acceptFriendship");
		List<Account> accounts = service.accpetList(owner);
		model.addAttribute("waitingList", accounts);
		service.accpetList(owner);
		long end = System.currentTimeMillis();
		service.savcePerformance(p, start, end, "acceptFriend");
		return mv;
	}
	@RequestMapping("/acceptFriend")
	public ModelAndView Accepted(Performance p,Model model
			,@RequestParam String name) {
		long start = System.currentTimeMillis();
		List<Account> Owner = service.getOneAccount(owner);
		List<Account> Friend = service.getOneAccount(name);
		int ownerID = Owner.get(0).getPersonId();
		int FriendID = Friend.get(0).getPersonId();
		service.Accepted(FriendID, ownerID);
		service.loadPersonalPage(model, owner);
		ModelAndView mv = new ModelAndView("Personal");
		long end = System.currentTimeMillis();
		service.savcePerformance(p, start, end, "Accepted");
		return mv;
	}
	@RequestMapping("/MyPage")
	public ModelAndView GoToPersonalPage(Performance p,Model model){
		long start = System.currentTimeMillis();
		service.loadPersonalPage(model, owner);
		ModelAndView mv = new ModelAndView("Personal");
		long end = System.currentTimeMillis();
		service.savcePerformance(p, start, end, "GoToPersonalPage");
		return mv;
	}
}
