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
	//adding the dependency
	private CoursesJpaRepository coursesJpaRepository;
	//performing dependency injection using constructor
	public CoursesController(CoursesJpaRepository coursesJpaRepository) {
		super();
		this.coursesJpaRepository = coursesJpaRepository;
	}

	//gets you all the courses
	@GetMapping(path = "/courses")
	public MappingJacksonValue getCourses() {		
		//retrieving the courses
		List<Courses> allCourses = coursesJpaRepository.findAll();
		//handling exception
		if(allCourses == null) throw new CourseNotFoundException("No courses available");
		//as we have added the annotation @JSONFilter on the Courses entity, it is now need to wrap the courses into MappingJacksonValue
		//I have made this function so as to reuse the code again and again while performing filter. You can find it below.
		return mappingJacksonValueProvider(allCourses, "Courses", "courseId", "name", "branch", "semester", "isElective");
	}
	
	//filters all the members except name and courseId. just to implement filtering
	@GetMapping(path = "/courses/list")
	public MappingJacksonValue filteringCourses(){
		//retrieving the courses
		List<Courses> allCourses = coursesJpaRepository.findAll();
		//handling exception
		if(allCourses == null) throw new CourseNotFoundException("No courses available");
		return mappingJacksonValueProvider(allCourses,"Courses" ,"name", "courseId");
	}
	
	//Function to implement filtering. Same method has been implemented differently based on
	//different argument. This was done understanding the requirement. 
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
	

	@GetMapping(path = "/courses/{courseId}")
	public ResponseEntity<MappingJacksonValue> getCourseById(@PathVariable Integer courseId) {

	    // 1. Search for the course by ID
	    Optional<Courses> course = coursesJpaRepository.findById(courseId);

	    // 2. Handle missing course
	    if (!course.isPresent()) {
	        throw new CourseNotFoundException("Course with ID " + courseId + " not found.");
	    }

	    // 3. Extract and wrap the course object
	    Courses foundCourse = course.get(); // extract the course from Optional
	    EntityModel<Courses> entityModel = EntityModel.of(foundCourse);

	    // 4. Add link to "All Courses" resource
	    WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getCourses());
	    entityModel.add(link.withRel("All Courses:"));

	    // 5. Serialize the entity model with specified properties
	    MappingJacksonValue mappingJacksonValue = mappingJacksonValueProvider(
	        entityModel,
	        "Courses", // root element name
	        "courseId", "name", "branch", "semester", "isElective" // serialized properties
	    );

	    // 6. Return the found course
	    return ResponseEntity.ok(mappingJacksonValue);
	}

	
	
	//gets you all the courses of specific branch
	@GetMapping(path = "/courses/branch/{branch}")
	public ResponseEntity<MappingJacksonValue> getCoursesOfAiml(@PathVariable Integer branch) {
		Optional<List<Courses>> courses = coursesJpaRepository.findAllByBranch(branch);
		if(courses.isEmpty()) throw new CourseNotFoundException("Courses with branch id: "+ branch +", were not found!");
		List<Courses> course = courses.get();
	    MappingJacksonValue mappingJacksonValue = mappingJacksonValueProvider(
		        course,
		        "Courses", // root element name
		        "courseId", "name", "semester", "isElective" // serialized properties
		    );
	    return ResponseEntity.ok(mappingJacksonValue);
	}
	
	//gets you all the courses of specific branch
	@GetMapping(path = "/courses/semester/{semester}")
	public ResponseEntity<MappingJacksonValue> getCoursesOfSemester(@PathVariable Integer semester) {
		Optional<List<Courses>> courses = coursesJpaRepository.findAllBySemester(semester);
		if(courses.isEmpty()) throw new CourseNotFoundException("Courses with branch id: "+ semester +", were not found!");
		List<Courses> courseList = courses.get();
		MappingJacksonValue mappingJacksonValue = mappingJacksonValueProvider(
				courseList,
				"Courses", // root element name
				"courseId", "name", "branch", "semester", "isElective" // serialized properties
				);
		return ResponseEntity.ok(mappingJacksonValue);
	}
	
	

	@PostMapping(path = "/courses")
	public ResponseEntity<Courses> postCourse(@Valid @RequestBody Courses course) {
		//this saves the course in the database after performing validations
		Courses savedCourse = coursesJpaRepository.save(course);
		//creates a uri to provide to the user after successfully posting the data. this will help the user to 
		//locate it back.
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(savedCourse.getCourseId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	//this is to update an already existing data.
	@PutMapping(path = "/courses/{courseId}")
	public ResponseEntity<Courses> postCourse(@Valid @RequestBody Courses course, @PathVariable Integer courseId) {
		Optional<Courses> oldCourse = coursesJpaRepository.findById(courseId);
		if(oldCourse.isEmpty()) throw new CourseNotFoundException("Course with course Id: "+courseId +" ,not found!");
		oldCourse.get().setBranch(course.getBranch());
		oldCourse.get().setIsElective(course.getIsElective());
		oldCourse.get().setName(course.getName());
		oldCourse.get().setSemester(course.getSemester());

		Courses savedCourse = coursesJpaRepository.save(oldCourse.get());
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(savedCourse.getCourseId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	//to delete the data
	@DeleteMapping(path = "/courses/{courseId}")
	public ResponseEntity<Courses> deleteCourseById(@PathVariable Integer courseId) {
		coursesJpaRepository.deleteById(courseId);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(courseId).toUri();
		return ResponseEntity.created(location).build();
	}
}
