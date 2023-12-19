package com.example.APICSEAIMLIOT.courses;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.APICSEAIMLIOT.exception.CourseNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

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
	public MappingJacksonValue getCourses() {		
		List<Courses> allCourses = coursesJpaRepository.findAll();
		if(allCourses == null) throw new CourseNotFoundException("No courses available");
		MappingJacksonValue mappingJacksonValue = mappingJacksonValueProvider(allCourses, "Courses", "courseId", "name", "branch", "semester", "isElective");
		return mappingJacksonValue;
	}
	
	@GetMapping(path = "/courses/list")
	public MappingJacksonValue filteringCourses(){
		List<Courses> allCourses = coursesJpaRepository.findAll();
		if(allCourses == null) throw new CourseNotFoundException("No courses available");
		MappingJacksonValue mappingJacksonValue = mappingJacksonValueProvider(allCourses,"Courses" ,"name", "courseId");
		return mappingJacksonValue;
	}
	
	private MappingJacksonValue mappingJacksonValueProvider(List<?> listOfFilterBeanExample, String id ,String...strings ) {
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(listOfFilterBeanExample);
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(strings);

		FilterProvider filterProvider = new SimpleFilterProvider().addFilter(id, filter);

		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}
	private MappingJacksonValue mappingJacksonValueProvider(EntityModel<Courses> course, String id ,String...strings ) {
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(course);
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(strings);
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter(id, filter);
		mappingJacksonValue.setFilters(filterProvider);
		return mappingJacksonValue;
	}
	
	// This method retrieves a course matching the provided ID.
	@GetMapping(path = "/courses/{courseId}")
	public ResponseEntity<MappingJacksonValue> getCourseById(@PathVariable Integer courseId) {

	    // Search for the course by ID.
	    Optional<Courses> course = coursesJpaRepository.findById(courseId);

	    // Handle non-existent course.
	    if (!course.isPresent()) {
	        throw new CourseNotFoundException("Course with ID " + courseId + " not found.");
	    }

	    // Build an entity model with the found course.
	    EntityModel<Courses> entityModel = EntityModel.of(course.get());

	    // Add a link to all courses for convenient navigation.
	    WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getCourses());
	    entityModel.add(link.withRel("All Courses:"));

	    MappingJacksonValue mappingJacksonValue = mappingJacksonValueProvider(entityModel, "Courses","courseId", "name", "branch", "semester", "isElective");
	    
	    // Return the entity model with a successful status code.
	    return ResponseEntity.ok(mappingJacksonValue);
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
