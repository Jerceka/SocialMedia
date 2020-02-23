package jerceka.workhard.demo;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

@Service
public class Ser {
	Date date = new Date();
	@Autowired
	private PostsRepo post;
	@Autowired
	private AccountRepo account;
	@Autowired
	private PerformanceRepo performance;
	@Autowired
	private FriendsRepo friend;
	@Autowired
	private LikesRepo like;
	public List<Posts> allPosts(){
		return post.findAll();
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
			return "enjoy your night " + name;
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
	@Transactional
	public List<Posts> personalPosts(int ownerId){
		return post.postsOfOwner(ownerId);
	}
	@Transactional
	public void loadPersonalPage(Model m,String name) {
		List<Account> Account = account.findByName(name);
		List<Posts> posts = post.postsOfOwner(Account.get(0).getPersonId());
		m.addAttribute("personalAccount", Account);
		m.addAttribute("greeting", greeting(name));
		m.addAttribute("personalPosts", posts);
		m.addAttribute("owner", name);
		m.addAttribute("friendsNumber", friend.numberOfFriends(Account.get(0).getPersonId()));
	}
	public List<Account> allAccount(){
		return account.findAll();
	}
	@Transactional
	public Boolean checkFriendship(int idofFriendsOffer,int idOfAcceptFriendOffer) {
		try {
			friend.rowWithTwoId(idofFriendsOffer, idOfAcceptFriendOffer).get(0).getFriendsid();
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	public void SaveFriendship(Friends f){
		friend.save(f);
	}
	@Transactional
	public List<Account> accpetList(String owner) {
		List<Account> ownerAccount = account.findByName(owner);
		int ownerId = ownerAccount.get(0).getPersonId();
		List<Friends> waitingFriendship = friend.acceptList(ownerId);
		int[] idOFOfferFriendShip = new int[waitingFriendship.size()];
		for(int i=0;i<waitingFriendship.size();i++) {
			idOFOfferFriendShip[i] = waitingFriendship.get(i).getFirst();
		}
		List<Account> x= null;
		for(int i=0;i<waitingFriendship.size();i++) {
			if(i==0) {
				x = account.findByPersonid(idOFOfferFriendShip[i]);
			}else
			if(i>0) {
				x.addAll(account.findByPersonid(idOFOfferFriendShip[i]));
			}
		}
		return x;
	}
	@Transactional
	public void Accepted(int ownerFriend,int owner) {
		friend.Accepted(ownerFriend, owner);
	}
	@Transactional
	public List<Posts> FriendsPosts(int owner) {
		int y = friend.numberOfFriends(owner);
		int[] x = new int[y];
		for(int i=0;i<y;i++) {
			int e = friend.FrinedList(owner).get(i).getFirst();
			int w = friend.FrinedList(owner).get(i).getSecond();
			if(e==owner) {
				x[i] = w;
			}else
			if(w==owner) {
				x[i] = e;
			}
		}
		List<Posts> friendPosts = null;
		for(int i=0;i<y;i++) {
			if(i==0) {
				friendPosts = post.postsOfOwner(x[i]);
			}else
			if(i>0) {
				friendPosts.addAll(post.postsOfOwner(x[i]));
			}
		}
		return friendPosts;
	}
	@Transactional
	public void likePost(int postID,int whoLikeThePost,Likes l) {
		try {
			if(like.existsBypostid(postID)) {
				List<Likes> likes = like.findByPostid(postID);
				for(int i=0;i<likes.size();i++) {
					if(whoLikeThePost==likes.get(i).getLikeownerid()) {
					}else {
						l.setPostid(postID);
						l.setLikeownerid(whoLikeThePost);
						like.save(l);
						int likeNumber = like.likeNumbers(postID);
						post.updateLikes(likeNumber, postID);
					}
				}
			}else {
				l.setPostid(postID);
				l.setLikeownerid(whoLikeThePost);
				like.save(l);
				int likeNumber = like.likeNumbers(postID);
				post.updateLikes(likeNumber, postID);
			}
		}catch(Exception e) {
			System.err.println("likePost Have problem: " + e.getMessage());
		}
	}
}
