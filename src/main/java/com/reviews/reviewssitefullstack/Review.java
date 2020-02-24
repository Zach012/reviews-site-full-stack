package com.reviews.reviewssitefullstack;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Review {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private String description;
	private String image;

	@ManyToMany(mappedBy = "reviews")
	private Collection<Category> categories;

	public Review(String name, String description, String image, Category... categories) {
		this.name = name;
		this.description = description;
		this.image = image;
		this.categories = new HashSet<>(Arrays.asList(categories));
	}

	public Review() {

	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getImage() {
		return image;
	}

	public Collection<Category> getCategory() {
		return categories;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}