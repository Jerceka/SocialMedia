package jerceka.workhard.demo.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "account" , path = "account")
public interface AccountRepo extends JpaRepository<Account, Integer>{
	boolean existsByName(String name);
	boolean existsByPassword(String password);
	List<Account> findByName(String name);
	List<Account> findByPersonid(int id);
}
