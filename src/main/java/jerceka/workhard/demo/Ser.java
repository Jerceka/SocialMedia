package jerceka.workhard.demo;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import jerceka.workhard.demo.database.*;

@Service
public class Ser {
	Date date = new Date();
	@Autowired
	private PostsRepo postRepo;
	@Autowired
	private AccountRepo accountRepo;
	@Autowired
	private PerformanceRepo performanceRepo;
	@Autowired
	private FriendsRepo friendRepo;
	@Autowired
	private LikesRepo like;
	@Autowired
	private Performance p;
	public List<Posts> allPosts(){
		return postRepo.findAll();
	}
	@Transactional
	public boolean checkLogin(String name,String password) {
		if(accountRepo.existsByName(name) && accountRepo.existsByPassword(password)) {
			String truePassword = accountRepo.findByName(name).get(0).getPassword();
			if(truePassword.equals(password)) {
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
		return accountRepo.existsByName(name);
	}
	@SuppressWarnings("deprecation")
	public String greeting(String name) {
		if(date.getHours()>=4&&date.getHours()<17) {
			return "enjoy your day " + name;
		}else{
			return "enjoy your night " + name;
		}
	}
	@Transactional
	public List<Account> getOneAccount(String name){
		return accountRepo.findByName(name);
	}
	public void makeAccount(Account a) {
		accountRepo.save(a);
	}
	public void createPost(Posts s) {
		postRepo.save(s);
	}
	public void savcePerformance(long start,long end,String methodName) {
			long d = end - start;
			p.setDate(date.toString());
			p.setMethod(methodName);
			p.setTime(d);
			performanceRepo.save(p);
	}
	@Transactional
	public List<Posts> personalPosts(int ownerId){
		return postRepo.postsOfOwner(ownerId);
	}
	@Transactional
	public void loadPersonalPage(Model m,String name) {
		List<Account> Account = accountRepo.findByName(name);
		List<Posts> posts = postRepo.postsOfOwner(Account.get(0).getPersonId());
		m.addAttribute("personalAccount", Account);
		m.addAttribute("greeting", greeting(name));
		m.addAttribute("personalPosts", posts);
		m.addAttribute("owner", name);
		m.addAttribute("friendsNumber", friendRepo.numberOfFriends(Account.get(0).getPersonId()));
	}
	public List<Account> allAccount(){
		return accountRepo.findAll();
	}
	@Transactional
	public Boolean checkFriendship(int idofFriendsOffer,int idOfAcceptFriendOffer) {
		if(friendRepo.existsByFirst(idOfAcceptFriendOffer)) {
			if(friendRepo.existsBySecond(idOfAcceptFriendOffer)) {
				List<Friends> firstList =friendRepo.findByFirst(idOfAcceptFriendOffer);
				List<Friends> secondList =friendRepo.findBySecond(idOfAcceptFriendOffer);
				for(Friends i : firstList) {
					if(i.getSecond()==idofFriendsOffer) {
						return true;
					}
				}
				for(Friends i : secondList) {
					if(i.getFirst()==idofFriendsOffer) {
						return true;
					}
				}
			};
		};
		return false;
	}
	public void SaveFriendship(Friends f){
		friendRepo.save(f);
	}
	@Transactional
	public List<Account> accpetList(String owner) {
		List<Account> ownerAccount = accountRepo.findByName(owner);
		int ownerId = ownerAccount.get(0).getPersonId();
		List<Friends> waitingFriendship = friendRepo.acceptList(ownerId);
		int[] idOFOfferFriendShip = new int[waitingFriendship.size()];
		for(int i=0;i<waitingFriendship.size();i++) {
			idOFOfferFriendShip[i] = waitingFriendship.get(i).getFirst();
		}
		List<Account> x= null;
		for(int i=0;i<waitingFriendship.size();i++) {
			if(i==0) {
				x = accountRepo.findByPersonid(idOFOfferFriendShip[i]);
			}else
			if(i>0) {
				x.addAll(accountRepo.findByPersonid(idOFOfferFriendShip[i]));
			}
		}
		return x;
	}
	@Transactional
	public void Accepted(int ownerFriend,int owner) {
		friendRepo.Accepted(ownerFriend, owner);
	}
	@Transactional
	public List<Posts> FriendsPosts(int owner) {
		int NumberOfFriends = friendRepo.numberOfFriends(owner);
		int[] friendsIdList = new int[NumberOfFriends];
		for(int i=0;i<NumberOfFriends;i++) {
			int firstIDGroup = friendRepo.FrinedList(owner).get(i).getFirst();
			int secondIDGroup = friendRepo.FrinedList(owner).get(i).getSecond();
			if(firstIDGroup==owner) {
				friendsIdList[i] = secondIDGroup;
			}else
			if(secondIDGroup==owner) {
				friendsIdList[i] = firstIDGroup;
			}
		}
		List<Posts> friendPosts = null;
		for(int i=0;i<NumberOfFriends;i++) {
			if(i==0) {
				friendPosts = postRepo.postsOfOwner(friendsIdList[i]);
			}else
			if(i>0) {
				friendPosts.addAll(postRepo.postsOfOwner(friendsIdList[i]));
			}
		}
		return friendPosts;
	}
	@Transactional
	public void likePost(int postID,int whoLikeThePost,Likes l) {
		try {
			if(like.existsBypostid(postID)) {
				List<Likes> likes = like.findByPostid(postID);
				int exists = 0;
				for(Likes i : likes) {
					if(whoLikeThePost==(i.getLikeownerid())) {
						exists = 1;
					}
				}
					if(exists==0) {
						l.setPostid(postID);
						l.setLikeownerid(whoLikeThePost);
						like.save(l);
						int likeNumber = like.likeNumbers(postID);
						postRepo.updateLikes(likeNumber, postID);
					}
			}else {
				l.setPostid(postID);
				l.setLikeownerid(whoLikeThePost);
				like.save(l);
				int likeNumber = like.likeNumbers(postID);
				postRepo.updateLikes(likeNumber, postID);
			}
		}catch(Exception e) {
			System.err.println("likePost Have problem: " + e.getMessage());
		}
	}
}
