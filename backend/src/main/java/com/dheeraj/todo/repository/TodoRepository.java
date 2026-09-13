package com.dheeraj.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dheeraj.todo.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
