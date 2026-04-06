package com.example.task4.lazy.repository;

import com.example.task4.lazy.entity.LazyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LazyUserRepository extends JpaRepository<LazyUser, Long> {
}
