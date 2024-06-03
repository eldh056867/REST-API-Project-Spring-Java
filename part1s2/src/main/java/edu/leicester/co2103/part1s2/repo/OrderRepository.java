package edu.leicester.co2103.part1s2.repo;

import edu.leicester.co2103.part1s2.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
