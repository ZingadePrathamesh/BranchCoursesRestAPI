package com.example.APICSEAIMLIOT.courses;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.stereotype.Component;

@Component
public class CoursesDAOService {
	static private Integer coursesCount = 0;
	static List<Courses> courses = new ArrayList<Courses>();
	
	static {
		courses.add(new Courses(++coursesCount, "Computer Graphics", true, 3, false));
		courses.add(new Courses(++coursesCount, "Data Structures", true, 3, false));
		courses.add(new Courses(++coursesCount, "Java OOPs", true, 3, false));
	}
	
	public Courses save(Courses course) {
		course.setId(++coursesCount);
		courses.add(course);
		return course;
	}
	
	public List<Courses> findAllCourses(){
		return courses;
	}
	
	public Courses findCourseById(Integer id) {
		Predicate<? super Courses > predicate = course -> course.getId().equals(id);
		return courses.stream().filter(predicate).findFirst().orElse(null);
	}
}
