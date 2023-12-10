package com.example.APICSEAIMLIOT.courses;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.APICSEAIMLIOT.exception.CourseNotFoundException;

import jakarta.validation.Valid;

@RestController
public class CoursesController {

	private CoursesDAOService coursesDAOService;
		
	public CoursesController(CoursesDAOService coursesDAOService) {
		super();
		this.coursesDAOService = coursesDAOService;
	}


	@GetMapping(path = "/courses")
	public List<Courses> getCourses() {
		return coursesDAOService.findAllCourses();
	}
	
	@GetMapping(path = "/courses/{id}")
	public Courses getCourseById(@PathVariable Integer id) {
		Courses course = coursesDAOService.findCourseById(id);
		
		if(course == null) throw new CourseNotFoundException("id: "+ id);
			
		return course;
	}
	
	@PostMapping(path = "/courses")
	public ResponseEntity<Courses> postCourse(@Valid @RequestBody Courses course) {
		Courses savedCourse = coursesDAOService.save(course);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(savedCourse.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(path = "/courses/{id}")
	public ResponseEntity<Courses> deleteCourseById(@PathVariable Integer id) {
		coursesDAOService.deleteCoursesById(id);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(id).toUri();
		return ResponseEntity.created(location).build();
	}
}
