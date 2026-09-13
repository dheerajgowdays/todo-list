package com.dheeraj.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dheeraj.todo.entity.Todo;
import com.dheeraj.todo.repository.TodoRepository;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    public TodoService(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }
    public List<Todo> getAllTodo(){
        return todoRepository.findAll();
    }
    public Optional<Todo> getTodoById(long id){
        return todoRepository.findById(id);
    }
    public Todo createTodo(Todo todo){
        return todoRepository.save(todo);
    }
    public Todo updateTodo(long id,Todo todo){
        Todo existingTodo = todoRepository.findById(id)
                                            .orElseThrow(()->new RuntimeException("Tdod not found with Id:"+id));   

        existingTodo.setTitle(todo.getTitle());
        existingTodo.setDescription(todo.getDescription());
        existingTodo.setCompleted(todo.isCompleted());
        return todoRepository.save(existingTodo);
    }
    public void deleteAllTodo() {
        if (todoRepository.count() == 0) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No todos found to delete");
        }
        todoRepository.deleteAll();
    }
    public void deleteTodoById(long id){
        Todo todo = todoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found with id: " + id));

    todoRepository.delete(todo);
    }

}
