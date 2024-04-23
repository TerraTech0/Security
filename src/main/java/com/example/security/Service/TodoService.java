package com.example.security.Service;

import com.example.security.Api.ApiException;
import com.example.security.Model.Todo;
import com.example.security.Model.User;
import com.example.security.Repository.AuthRepository;
import com.example.security.Repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final AuthRepository authRepository;

    public List<Todo> getAllTodos(){
        return todoRepository.findAll();
    }

    public List<Todo> getMyTodos(Integer userId){
        User user = authRepository.findUserById(userId);
        return todoRepository.findAllByUser(user);
    }

    public void addTodo(Integer userId, Todo todo){
        User user = authRepository.findUserById(userId);
        todo.setUser(user);
        todoRepository.save(todo);
    }
//==============================EXPLAIN LOGIC=====================================================
    //update todo >> get userId and todo_id, and then update todo!
    //User >> find user By Id!
    //todo >> find Todo by id!
    // if todo != null >> update , else throw new ApiException("not found!")
//    =====================================Method=====================================================
    public void updateTodo(Integer userId, Integer todo_id, Todo newTodo){
        User user = authRepository.findUserById(userId);
        Todo todo = todoRepository.findTodoById(todo_id);
        if (todo == null){
            throw new ApiException("todo not found!");
        } else if (user == null) {
            throw new ApiException("user not found!");
        }
        todo.setTitle(newTodo.getTitle());
        todoRepository.save(todo);
    }
//==========================================================================================

    //delete todo
    //get userId and get todoId
    // check if the todoId is exist or not!<< using findTodoById
    // not get the doto and delete it
    public void deleteTodo(Integer user_id, Integer todo_id){
        User user = authRepository.findUserById(user_id);
        Todo todo = todoRepository.findTodoById(todo_id);
        if (todo == null){
            throw new ApiException("todo not found!");
        } else if (user == null) {
            throw new ApiException("user not found!");
        }
        todoRepository.delete(todo);
    }



}
