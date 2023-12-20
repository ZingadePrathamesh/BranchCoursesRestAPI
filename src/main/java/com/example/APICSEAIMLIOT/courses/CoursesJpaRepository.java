package com.example.APICSEAIMLIOT.courses;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursesJpaRepository extends JpaRepository<Courses, Integer>{
	Optional<List<Courses>> findAllByBranch(Integer branch);

	Optional<List<Courses>> findAllBySemester(Integer semester);
}
