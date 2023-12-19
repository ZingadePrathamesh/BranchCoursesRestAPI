package com.example.APICSEAIMLIOT.courses;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.APICSEAIMLIOT.exception.CourseNotFoundException;

import jakarta.validation.Valid;

@RestController
public class CoursesController {

	private CoursesJpaRepository coursesJpaRepository;
		
	public CoursesController(CoursesDAOService coursesDAOService, CoursesJpaRepository coursesJpaRepository) {
		super();
		this.coursesJpaRepository = coursesJpaRepository;
	}

	//gets you all the courses
	@GetMapping(path = "/courses")
	public List<Courses> getCourses() {		
		List<Courses> allCourses = coursesJpaRepository.findAll();
		if(allCourses == null) throw new CourseNotFoundException("No courses available");
		return allCourses;
	}
	
	//Gets you a course that matches the provided id
	@GetMapping(path = "/courses/{courseId}")
	public EntityModel<Courses> getCourseById(@PathVariable Integer courseId) {
		Optional<Courses> course = coursesJpaRepository.findById(courseId);
		if(course == null) throw new CourseNotFoundException("id: "+ courseId);
		EntityModel<Courses> entityModel = EntityModel.of(course.get());
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getCourses());
		entityModel.add(link.withRel("All Courses:"));
		return entityModel;
	}
	
	
	//gets you all the courses of specific branch
	@GetMapping(path = "/courses/branch/{branch}")
	public List<Courses> getCoursesOfAiml(@PathVariable Integer branch) {
		Optional<List<Courses>> courses = coursesJpaRepository.findAllByBranch(branch);
		return courses.get();
	}

	@PostMapping(path = "/courses")
	public ResponseEntity<Courses> postCourse(@Valid @RequestBody Courses course) {
		Courses savedCourse = coursesJpaRepository.save(course);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(savedCourse.getCourseId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping(path = "/courses/{courseId}")
	public ResponseEntity<Courses> postCourse(@Valid @RequestBody Courses course, @PathVariable Integer courseId) {
		Optional<Courses> oldCourse = coursesJpaRepository.findById(courseId);
		if(oldCourse.isEmpty()) throw new CourseNotFoundException("course Id: "+courseId);
		oldCourse.get().setBranch(course.getBranch());
		oldCourse.get().setIsElective(course.getIsElective());
		oldCourse.get().setName(course.getName());
		oldCourse.get().setSemester(course.getSemester());

		Courses savedCourse = coursesJpaRepository.save(oldCourse.get());
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(savedCourse.getCourseId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(path = "/courses/{courseId}")
	public ResponseEntity<Courses> deleteCourseById(@PathVariable Integer courseId) {
		coursesJpaRepository.deleteById(courseId);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(courseId).toUri();
		return ResponseEntity.created(location).build();
	}
}
