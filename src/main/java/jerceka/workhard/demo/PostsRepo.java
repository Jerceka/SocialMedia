package jerceka.workhard.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "post" , path = "post")
public interface PostsRepo extends JpaRepository<Posts, Integer> {

}
