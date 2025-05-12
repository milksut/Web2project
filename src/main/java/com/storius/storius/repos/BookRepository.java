package com.storius.storius.repos;

import com.storius.storius.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitleContainingIgnoreCase(String keyword);

    // Fetch top 5 books by rating
    List<Book> findTop5ByOrderByRatingDesc();

    // Fetch books with progress greater than 0 (in-progress books)
    @Query("SELECT b FROM Book b WHERE b.progress > 0 and b.progress < 100")
    List<Book> findInProgressBooks();

    // Fetch trending books (example: based on popularity or recent activity)
    @Query("SELECT b FROM Book b ORDER BY b.rating DESC, b.id DESC")
    List<Book> findTrendingBooks();
}
