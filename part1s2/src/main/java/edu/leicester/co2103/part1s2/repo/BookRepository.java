package edu.leicester.co2103.part1s2.repo;

import edu.leicester.co2103.part1s2.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
}
