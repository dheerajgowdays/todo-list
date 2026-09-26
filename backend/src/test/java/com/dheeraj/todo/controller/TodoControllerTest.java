package com.dheeraj.todo.controller;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dheeraj.todo.entity.Todo;
import com.dheeraj.todo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TodoService todoService;

    @Test
    void shouldGetAllTodos() throws Exception {
        Todo todo1 = new Todo("Learn Java", "Learn Core Java", false);
        Todo todo2 = new Todo("Learn Spring", "Learn Spring Boot", false);

        when(todoService.getAllTodo()).thenReturn(List.of(todo1, todo2));

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Learn Java"))
                .andExpect(jsonPath("$[1].title").value("Learn Spring"));

        verify(todoService).getAllTodo();
    }

    @Test
    void shouldGetTodoById() throws Exception {
        Todo todo = new Todo("Learn Java", "Learn Core Java", false);

        when(todoService.getTodoById(1L)).thenReturn(Optional.of(todo));

        mockMvc.perform(get("/api/todos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Learn Java"))
                .andExpect(jsonPath("$.description").value("Learn Core Java"));

        verify(todoService).getTodoById(1L);
    }

    @Test
    void shouldCreateTodo() throws Exception {
        Todo inputTodo = new Todo("Learn Java", "Learn Core Java", false);
        Todo createdTodo = new Todo("Learn Java", "Learn Core Java", false);

        when(todoService.createTodo(any(Todo.class))).thenReturn(createdTodo);

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputTodo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Learn Java"))
                .andExpect(jsonPath("$.description").value("Learn Core Java"));

        verify(todoService).createTodo(any(Todo.class));
    }

    @Test
    void shouldUpdateTodo() throws Exception {
        Todo updatedPayload = new Todo("Learn Spring Boot", "Deep dive into Spring", true);

        // Service returns Todo, but Controller returns a String message
        when(todoService.updateTodo(eq(1L), any(Todo.class))).thenReturn(updatedPayload);

        mockMvc.perform(put("/api/todos/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedPayload)))
                .andExpect(status().isOk())
                .andExpect(content().string("Updated Title Successfully"));

        verify(todoService).updateTodo(eq(1L), any(Todo.class));
    }

    @Test
    void shouldDeleteAllTodos() throws Exception {
        mockMvc.perform(delete("/api/todos"))
                .andExpect(status().isOk());

        verify(todoService).deleteAllTodo();
    }

    @Test
    void shouldDeleteTodoById() throws Exception {
        mockMvc.perform(delete("/api/todos/{id}", 1L))
                .andExpect(status().isOk());

        verify(todoService).deleteTodoById(1L);
    }
}