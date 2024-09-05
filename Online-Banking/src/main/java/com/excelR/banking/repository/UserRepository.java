package com.excelR.banking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.excelR.banking.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> getByEmail(String email);
	Optional<User> getById(long customerId);
	
}
