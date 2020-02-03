package jerceka.workhard.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "performance" , path = "performance")
public interface PerformanceRepo extends JpaRepository<Performance, Integer> {
	@Query(value = "insert into performance(date,method,time) values('Ahmet','make',34324);", nativeQuery = true)
	Performance all();

}
