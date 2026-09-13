package com.dheeraj.todo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dheeraj.todo.entity.Todo;
import com.dheeraj.todo.service.TodoService;
@RestController
@RequestMapping ("/api/todos") 
public class TodoController {

    TodoService todoService;
    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }
    @GetMapping 
    public List<Todo> getAllTodos(){
        return todoService.getAllTodo();
    }
    @GetMapping ("{id}")
    public Optional<Todo> getTodoById(@PathVariable long id){
        return todoService.getTodoById(id);
    }
    @PostMapping
    public Todo createTodo(@RequestBody Todo todo){
        return todoService.createTodo(todo);
    }

    @PutMapping ("{id}")
    public String updateTodo(@PathVariable long id,@RequestBody  Todo todo){
        todoService.updateTodo(id,todo);
        return "Updated Title Successfully";
    }
    
    @DeleteMapping 
    public void deleteAllTodo(){
        todoService.deleteAllTodo();
        
    }
    @DeleteMapping ("/{id}")
    public void deleteTodoById(@PathVariable long id){
        todoService.deleteTodoById(id);
       
    }

}
