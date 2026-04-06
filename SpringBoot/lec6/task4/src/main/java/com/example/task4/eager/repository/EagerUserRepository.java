package com.example.task4.eager.repository;

import com.example.task4.eager.entity.EagerUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EagerUserRepository extends JpaRepository<EagerUser, Long> {
}
