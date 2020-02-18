package jerceka.workhard.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendsRepo extends JpaRepository<Friends, Integer> {
	@Query(
			value = "select count(first and second) from friends where first=?1 and accept=1 or second=?1 and accept=1", 
			nativeQuery = true)
	int numberOfFriends(int id);
	@Query(
			value = "select * from friends where first=?1 and second=?2 or first=?2 and second=?1", 
			nativeQuery = true)
	List<Friends> rowWithTwoId(int idofFriendsOffer,int idOfAcceptFriendOffer);
	@Query(
			value = " select * from friends where second=?1 and accept=0", 
			nativeQuery = true)
	List<Friends> acceptList(int id);
	@Modifying
	@Query(
			value = "update friends set accept=1 where first=?1 and second=?2", 
			nativeQuery = true)
	void Accepted(int OwnerNewFriend,int owner);
	@Query(
			value = "select * from friends where first=?1 and accept=1 or second=?1 and accept=1", 
			nativeQuery = true)
	List<Friends> FrinedList(int owner);
	
}
