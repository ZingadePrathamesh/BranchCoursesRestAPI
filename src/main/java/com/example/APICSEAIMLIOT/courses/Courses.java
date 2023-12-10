package com.example.APICSEAIMLIOT.courses;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class Courses {
	
	private Integer id;
	@Size(min = 2, message = "Enter atleast 2 Characters in name.")
	private String  name;
	private Boolean isAIML;
	
	@Min(value = 1, message = "Value must be above 0!")
	@Max(value = 8, message = "Value must be below 9!")
	private Integer semester;
	
	private Boolean isElective;
	
	public Courses(Integer id, String name, Boolean isAIML, Integer semester, Boolean isElective) {
		super();
		this.id = id;
		this.name = name;
		this.isAIML = isAIML;
		this.semester = semester;
		this.isElective = isElective;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsAIML() {
		return isAIML;
	}
	public void setIsAIML(Boolean isAIML) {
		this.isAIML = isAIML;
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
		return "Courses [id=" + id + ", name=" + name + ", isAIML=" + isAIML + ", semester=" + semester
				+ ", isElective=" + isElective + "]";
	}
	
	

}
