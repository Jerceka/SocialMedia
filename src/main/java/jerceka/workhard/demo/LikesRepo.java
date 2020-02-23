package jerceka.workhard.demo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LikesRepo extends CrudRepository<Likes, Integer>{
	boolean existsBypostid(int postID);
	boolean existsBylikeownerid(int likeownerid);
	List<Likes> findByPostid(int postID);
	@Query(
			value = "select count(postid) from likes where postid=?1", 
			nativeQuery = true)
	int likeNumbers(int postid);
}
