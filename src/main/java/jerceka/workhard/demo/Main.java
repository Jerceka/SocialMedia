package jerceka.workhard.demo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import jerceka.workhard.demo.database.Account;
import jerceka.workhard.demo.database.Friends;
import jerceka.workhard.demo.database.Likes;
import jerceka.workhard.demo.database.Performance;
import jerceka.workhard.demo.database.Posts;

@RestController
public class Main {
	@Autowired
	private Ser service;
	private String owner;
	@RequestMapping("/")
	public ModelAndView Home(Model model) {
		long start = System.currentTimeMillis();
		List<Posts> po = service.allPosts();
		model.addAttribute("po", po);
		ModelAndView mv = new ModelAndView("index");
		long end = System.currentTimeMillis();
		service.savcePerformance(start, end, "HomePage");
		return mv;
	}
	@RequestMapping("/login")
	public ModelAndView openPersonlPage(Model model,@RequestParam String name,
			@RequestParam String password) {
		if(service.checkLogin(name, password)) {
			long start = System.currentTimeMillis();
			owner = name;
			service.loadPersonalPage(model, name);
			ModelAndView mv = new ModelAndView("Personal");
			long end = System.currentTimeMillis();
			service.savcePerformance(start, end, "OpenPersonalPage");
			return mv;
			
		}else {
			ModelAndView mv = new ModelAndView("index");
			return mv;
		}
	}
	@RequestMapping("/sendToCreatePage")
	public ModelAndView sendToCreatePage() {
		long start = System.currentTimeMillis();
		ModelAndView mv = new ModelAndView("create");
		long end = System.currentTimeMillis();
		service.savcePerformance(start, end, "SendToCreatePage");
		return mv;
	}	
	@RequestMapping("/createAccount")
	public ModelAndView MakeAccount(Model model,Account a){
			if(!service.checkName(a.getName())) {
				long start = System.currentTimeMillis();
				service.makeAccount(a);
				owner = a.getName();
				service.loadPersonalPage(model, a.getName());
				ModelAndView mv = new ModelAndView("Personal");
				long end = System.currentTimeMillis();
				service.savcePerformance(start, end, "MakeAccount");
				return mv;
			}else {
				ModelAndView mv = new ModelAndView("create");
				return mv;
			}
	}
	@RequestMapping("/sendToPostPage")
	public ModelAndView goToPostPage(){
		long start = System.currentTimeMillis();
		ModelAndView mv = new ModelAndView("post");
		long end = System.currentTimeMillis();
		service.savcePerformance(start, end, "SendToPostPage");
		return mv;
	}
	@RequestMapping("/createPost")
	public ModelAndView makePost(Model model,Account a,Posts s){
		long start = System.currentTimeMillis();
		List<Account> account = service.getOneAccount(owner);
		LocalDateTime myDateObj = LocalDateTime.now();
	    DateTimeFormatter date = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");
		s.setDay(myDateObj.format(date));
		s.setTime(myDateObj.format(time));
		s.setName(owner);
		int postOwner = account.get(0).getPersonId();
		s.setOwner(postOwner);
		service.createPost(s);
		service.loadPersonalPage(model, owner);
		ModelAndView mv = new ModelAndView("Personal");
		long end = System.currentTimeMillis();
		service.savcePerformance(start, end, "MakePost");
		return mv;
	}
	@RequestMapping("/goToHomePage")
	public ModelAndView HomePage(Model model) {
		long start = System.currentTimeMillis();
		List<Account> account = service.allAccount();
		model.addAttribute("account", account);
		List<Account> ownerAccount = service.getOneAccount(owner);
		int ownerId = ownerAccount.get(0).getPersonId();
		List<Posts> po = service.FriendsPosts(ownerId);
		model.addAttribute("po", po);
		ModelAndView mv = new ModelAndView("home");
		long end = System.currentTimeMillis();
		service.savcePerformance(start, end, "goToHomePage");
		return mv;
	}
	@RequestMapping("/addFriend")
	public ModelAndView addFriend(@RequestParam String name,Model model,Friends f) {
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
		service.savcePerformance(start, end, "AddFriend");
		return mv;
	}
	@RequestMapping("/goToAcceptFriendshipPage")
	public ModelAndView acceptFriend(Model model) {
		long start = System.currentTimeMillis();
		ModelAndView mv = new ModelAndView("acceptFriendship");
		List<Account> accounts = service.accpetList(owner);
		model.addAttribute("waitingList", accounts);
		service.accpetList(owner);
		long end = System.currentTimeMillis();
		service.savcePerformance(start, end, "acceptFriend");
		return mv;
	}
	@RequestMapping("/acceptFriend")
	public ModelAndView Accepted(Model model,@RequestParam String name) {
		long start = System.currentTimeMillis();
		List<Account> Owner = service.getOneAccount(owner);
		List<Account> Friend = service.getOneAccount(name);
		int ownerID = Owner.get(0).getPersonId();
		int FriendID = Friend.get(0).getPersonId();
		service.Accepted(FriendID, ownerID);
		service.loadPersonalPage(model, owner);
		ModelAndView mv = new ModelAndView("Personal");
		long end = System.currentTimeMillis();
		service.savcePerformance(start, end, "Accepted");
		return mv;
	}
	@RequestMapping("/MyPage")
	public ModelAndView GoToPersonalPage(Model model){
		long start = System.currentTimeMillis();
		service.loadPersonalPage(model, owner);
		ModelAndView mv = new ModelAndView("Personal");
		long end = System.currentTimeMillis();
		service.savcePerformance(start, end, "GoToPersonalPage");
		return mv;
	}
	@RequestMapping("/likePost")
	public ModelAndView likePost(@RequestParam int postid,Likes l,
			Model model) {
		List<Account> ownerid = service.getOneAccount(owner);
		service.likePost(postid,ownerid.get(0).getPersonId(),l);
		return HomePage(model);
	}
}
