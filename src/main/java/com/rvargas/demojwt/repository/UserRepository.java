package com.rvargas.demojwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rvargas.demojwt.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);
}
