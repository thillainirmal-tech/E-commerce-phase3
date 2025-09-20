package com.example.minor_project_01.repo;

import com.example.minor_project_01.entity.Order;
import com.example.minor_project_01.entity.OrderStatus;
import com.example.minor_project_01.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,Long> {
    List<Order> findByStatusAndUser(OrderStatus status, User user);
}
