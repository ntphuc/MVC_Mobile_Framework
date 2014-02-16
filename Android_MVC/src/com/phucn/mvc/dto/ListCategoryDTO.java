package com.phucn.mvc.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.phucn.mvc.constants.Constants;

public class ListCategoryDTO implements Serializable {
	public ListCategoryDTO(String string) {
		// TODO Auto-generated constructor stub
		title = string;
	}

	/**
	 * 
	 */
	public ListCategoryDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String title;
	public String thumbUrl = "";

	// client
	public String type = "Other";
	private static final String[] categories_english_name = {
			"IELTS", "TOEIC",
			"TOEFL", "SPEAKING",
			"LISTENING", "READING",
			"WRITING", "GRAMMAR",
			"VOCABULARY", "ENGLISH BUSINESS",
			"VOA LEARN ENGLISH",
			"BBC LEARN ENGLISH",
			"LEARNING ENGLISH BY FILMS",
			"LEARNING ENGLISH BY SONGS" };

	public static List<String> getListCategories() {

		// TODO Auto-generated method stub
		
		List<String> sections = new ArrayList<String>();
		for (int i = 0; i < categories_english_name.length; i++) {
			sections.add(categories_english_name[i]);

		}
		return sections;

	}
}
