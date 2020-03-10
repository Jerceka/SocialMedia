package jerceka.workhard.demo.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "post" , path = "post")
public interface PostsRepo extends JpaRepository<Posts, Integer> {
	@Query(
			  value = "SELECT * FROM Posts WHERE owner = ?1", 
			  nativeQuery = true)
	List<Posts> postsOfOwner(int ownerId);
	@Modifying
	@Query(
			value = "update posts set likes=?1 where postid=?2", 
			nativeQuery = true)
	void updateLikes(int likesnumber,int postid);
}
