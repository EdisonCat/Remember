package com.edgtech.rest.webservices.restfulwebservices.todo;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TodoHardcodedService {
    private static List<Todo> todos = new ArrayList<>();
    private static long idCounter = 0;
    static {
        todos.add(new Todo(++idCounter, "do leetcode", "edisoncat", new Date(), false));
        todos.add((new Todo(++idCounter, "interview", "edisoncat", new Date(), false)));
        todos.add((new Todo(++idCounter, "internship", "edisoncat", new Date(), false)));
    }
    public List<Todo> findAll() {
        return todos;
    }

    public Todo deleteById(long id) {
        Todo toBeRemoved = findById(id);
        if(toBeRemoved == null) return null;
        todos.remove(toBeRemoved);
        return toBeRemoved;
    }
    public Todo save(Todo todo) {
        if(todo.getId() == -1 || todo.getId() == 0) todo.setId(++idCounter);
        else deleteById(todo.getId());
        todos.add(todo);
        return todo;
    }

    public Todo findById(long id) {
        for(Todo todo: todos)
            if(todo.getId() == id) return todo;
        return null;
    }
}
