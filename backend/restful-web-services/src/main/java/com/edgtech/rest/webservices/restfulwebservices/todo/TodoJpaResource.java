package com.edgtech.rest.webservices.restfulwebservices.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TodoJpaResource {

    @Autowired
    private TodoJpaRepository todoJpaRepository;

    @GetMapping(path = "/jpa/users/{username}/todos")
    public List<Todo> getAllTodos(@PathVariable String username) {
        return todoJpaRepository.findByUsername(username);
    }

    @DeleteMapping(path = "/jpa/users/{username}/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long id) {
        todoJpaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/jpa/users/{username}/todos/{id}")
    public Todo getTodo(@PathVariable String username, @PathVariable long id) {
        return todoJpaRepository.findById(id).isPresent() ? todoJpaRepository.findById(id).get() : null;
    }

    @PutMapping(path = "/jpa/users/{username}/todos/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable String username,
                                           @PathVariable long id,
                                           @RequestBody Todo todo) {
        todo.setUsername(username);
        Todo todoUpdated = todoJpaRepository.save(todo);
        //return ResponseEntity.ok(todo);
        return new ResponseEntity<Todo>(todo, HttpStatus.OK);
    }

    @PostMapping(path = "/jpa/users/{username}/todos")
    public ResponseEntity<Void> createTodo(@PathVariable String username,
                                           @RequestBody Todo todo) {
        todo.setUsername(username);
        Todo createdTodo = todoJpaRepository.save(todo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
