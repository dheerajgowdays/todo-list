package com.dheeraj.todo.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.dheeraj.todo.entity.Todo;
import com.dheeraj.todo.repository.TodoRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TodoServiceTest{

	@Mock
	private TodoRepository todoRepository;

	@InjectMocks
	private TodoService todoService;

	@Test 
	void shouldGetAllTodos(){
		Todo todo1 = new Todo("Learn java","Learn core java",false);
		Todo todo2 = new Todo("Learn Spring","Learn Spring Boot",false);

		when(todoRepository.findAll()).thenReturn(List.of(todo1,todo2));

		List<Todo> result = todoService.getAllTodo();
		assertEquals(2,result.size());
		verify(todoRepository).findAll();
	}

	@Test 
	void shouldCreateTodo(){
		Todo todo1 = new Todo("Learn java","Learn core java",false);

		when(todoRepository.save(todo1)).thenReturn(todo1);
		
		Todo result = todoService.createTodo(todo1);
		assertEquals(todo1,result);	
		verify(todoRepository).save(todo1);
	}
	@Test 
	void shouldGetTodoById(){

		Todo todo = new Todo("Learn Spring","Learn Spring Boot",false);
		when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

		Optional<Todo> result = todoService.getTodoById(1L);

		assertEquals(todo,result.get());
		verify(todoRepository).findById(1L);

	}
	@Test 
	void shouldUpdateTodo(){
		Todo existingTodo = new Todo("Lern java","Learn core java",false);
		Todo updatedTodo = new Todo("Learn java","Learn core java",true);

		when(todoRepository.findById(1L)).thenReturn(Optional.of(existingTodo));
		when(todoRepository.save(existingTodo)).thenReturn(updatedTodo);

  		Todo result = todoService.updateTodo(1L, updatedTodo);

		assertEquals(updatedTodo.getTitle(), result.getTitle());
		assertEquals(updatedTodo.getDescription(), result.getDescription());

		verify(todoRepository).findById(1L);
		verify(todoRepository).save(existingTodo);
	}


	@Test
	void shouldDeleteTodoById() {
    	Long todoId = 1L;
    	Todo todo = new Todo("Learn java", "Description", false);
    	when(todoRepository.findById(todoId)).thenReturn(Optional.of(todo));

    	todoService.deleteTodoById(todoId);

    	verify(todoRepository, times(1)).delete(todo); // or deleteById(todoId)
	}
	@Test
	void shouldThrowExceptionWhenUpdateingNonExistingTodoById() {
		when(todoRepository.findById(999L)).thenReturn(Optional.empty());

		RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(
			RuntimeException.class,
			() -> todoService.updateTodo(999L, new Todo("Non-existing", "This todo does not exist", false))
		);
		assertEquals("Tdod not found with Id:999", exception.getMessage());
		verify(todoRepository).findById(999L);
	}
	
}
