package com.dheeraj.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import com.dheeraj.todo.entity.Todo;
import com.dheeraj.todo.service.TodoService;
import org.junit.jupiter.api.Test;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;

    @Test
    public void testGetAllTodos() throws Exception{
        Todo todo = new Todo("Learn java","Learn core java",false);

        Todo todo1 = new Todo("Learn Spring","Learn Spring Boot",false);

        when(todoService.getAllTodo()).thenReturn(List.of(todo,todo1));

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Learn java"))
                .andExpect(jsonPath("$[1].title").value("Learn Spring"));

        verify(todoService).getAllTodo();
    }

}