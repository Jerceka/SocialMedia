package jerceka.workhard.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "account" , path = "account")
public interface Repo extends JpaRepository<Account, Integer>{

}
