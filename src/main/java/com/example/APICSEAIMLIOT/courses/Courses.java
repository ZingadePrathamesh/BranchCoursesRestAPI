package com.example.APICSEAIMLIOT.courses;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFilter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

//to perform filtering with this class
@JsonFilter("Courses")
@Entity
public class Courses {
	
	@Id
	@GeneratedValue
	private Integer courseId;
	
	@Size(min = 2, message = "Enter atleast 2 Characters in name.")
	private String  name;
	
	@Range(min = 1, max = 2)
	private Integer branch;
	
	@Min(value = 1, message = "Value must be above 0!")
	@Max(value = 8, message = "Value must be below 9!")
	private Integer semester;
	
	private Boolean isElective;
	
	public Courses() {
		super();
	}
	
	public Courses(Integer courseId, String name, Integer branch, Integer semester, Boolean isElective) {
		super();
		this.courseId = courseId;
		this.name = name;
		this.branch = branch;
		this.semester = semester;
		this.isElective = isElective;
	}
	
	
	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getBranch() {
		return branch;
	}

	public void setBranch(Integer branch) {
		this.branch = branch;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSemester() {
		return semester;
	}
	public void setSemester(Integer semester) {
		this.semester = semester;
	}
	public Boolean getIsElective() {
		return isElective;
	}
	public void setIsElective(Boolean isElective) {
		this.isElective = isElective;
	}

	@Override
	public String toString() {
		return "Courses [id=" + courseId + ", name=" + name + ", branch Code=" + branch + ", semester=" + semester
				+ ", isElective=" + isElective + "]";
	}

}
