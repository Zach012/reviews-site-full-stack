package com.reviews.reviewssitefullstack;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class JPAMappingTest {

	@Resource
	private TestEntityManager entityManager;

	@Resource
	private CategoryRepository categoryRepo;

	@Resource
	private ReviewRepository reviewRepo;

	@Test
	public void shouldSaveAndLoadCategory() {
		Category category = categoryRepo.save(new Category("category"));
		Long categoryId = category.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<Category> result = categoryRepo.findById(categoryId);
		category = result.get();
		assertThat(category.getName(), is("category"));
	}

	@Test
	public void shouldGenerateCategoryId() {
		Category category = categoryRepo.save(new Category("category"));
		Long categoryId = category.getId();

		entityManager.flush();
		entityManager.clear();

		assertThat(categoryId, is(greaterThan(0L)));
	}

	@Test
	public void shouldSaveAndLoadReviews() {
		Review review = new Review("review name", "description", "image");
		review = reviewRepo.save(review);
		Long reviewId = review.getId();

		entityManager.flush();
		entityManager.clear();

		Optional<Review> result = reviewRepo.findById(reviewId);
		review = result.get();
		assertThat(review.getName(), is("review name"));
	}

	@Test
	public void shouldEstablishReviewToCategoryrelationship() {
		Category category1 = categoryRepo.save(new Category("category1"));
		Category category2 = categoryRepo.save(new Category("category2"));

		Review review = new Review("review1", "description", "image", category1, category2);
		review = reviewRepo.save(review);
		long reviewId = review.getId();

		Optional<Review> result = reviewRepo.findById(reviewId);
		review = result.get();

		assertThat(review.getCategory(), containsInAnyOrder(category1, category2));
	}

	@Test
	public void shouldFindReviewsForCategory() {
		Category category = categoryRepo.save(new Category("category"));

		Review review1 = new Review("review1", "description", "image", category);
		Review review2 = new Review("review2", "description", "image", category);

		entityManager.flush();
		entityManager.clear();

		Collection<Review> reviewsForCategories = reviewRepo.findByCategoryContains(category);

		assertThat(reviewsForCategories, containsInAnyOrder(review1, review2));
	}

	@Test
	public void shouldFindReviewsForCategoryId() {
		Category category = categoryRepo.save(new Category("category"));
		long categoryId = category.getId();

		Review review1 = new Review("review1", "description", "image", category);
		Review review2 = new Review("review2", "description", "image", category);

		entityManager.flush();
		entityManager.clear();

		Collection<Review> reviewsForCategories = reviewRepo.findByCategoryId(categoryId);

		assertThat(reviewsForCategories, containsInAnyOrder(review1, review2));
	}
}
