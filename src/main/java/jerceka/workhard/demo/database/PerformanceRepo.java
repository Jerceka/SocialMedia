package jerceka.workhard.demo.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "performance" , path = "performance")
public interface PerformanceRepo extends JpaRepository<Performance, Integer> {
	
}
