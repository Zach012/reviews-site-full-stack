package com.reviews.reviewssitefullstack;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {

	Collection<Review> findByCategoryContains(Category category);

	Collection<Review> findByCategoryId(long id);

}
