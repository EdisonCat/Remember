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
public class TodoResource {
    @Autowired
    private TodoHardcodedService todoHardcodedService;

    @GetMapping(path = "/users/{username}/todos")
    public List<Todo> getAllTodos(@PathVariable String username) {
        return todoHardcodedService.findAll();
    }

    @DeleteMapping(path = "users/{username}/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long id) {
        Todo todo = todoHardcodedService.deleteById(id);
        if(todo != null) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/users/{username}/todos/{id}")
    public Todo getTodo(@PathVariable String username, @PathVariable long id) {
        return todoHardcodedService.findById(id);
    }

    @PutMapping(path = "/users/{username}/todos/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable String username,
                                           @PathVariable long id,
                                           @RequestBody Todo todo) {
        Todo todoUpdated = todoHardcodedService.save(todo);
        //return ResponseEntity.ok(todo);
        return new ResponseEntity<Todo>(todo, HttpStatus.OK);
    }

    @PostMapping(path = "/users/{username}/todos")
    public ResponseEntity<Void> createTodo(@PathVariable String username,
                                           @RequestBody Todo todo) {
        Todo createdTodo = todoHardcodedService.save(todo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
