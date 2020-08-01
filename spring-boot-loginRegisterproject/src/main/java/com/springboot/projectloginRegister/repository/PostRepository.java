package com.springboot.projectloginRegister.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.projectloginRegister.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
