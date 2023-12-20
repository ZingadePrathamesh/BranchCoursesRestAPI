package com.example.APICSEAIMLIOT.courses;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


//You can customize the repository exporting behavior using the 
//@RepositoryRestResource annotation on your repositories. 
//For example, you can disable repository exporting for a specific repository.

//the HAL dependency were exposing this endpoints too to perform CRUD so I disabled it explicitly
@RepositoryRestResource(exported = false)
public interface CoursesJpaRepository extends JpaRepository<Courses, Integer>{
	Optional<List<Courses>> findAllByBranch(Integer branch);
	Optional<List<Courses>> findAllBySemester(Integer semester);
}
