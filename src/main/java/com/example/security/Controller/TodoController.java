package com.example.security.Controller;

import com.example.security.Api.ApiResponse;
import com.example.security.Model.Todo;
import com.example.security.Model.User;
import com.example.security.Service.TodoService;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/todo")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/get-all")
    public ResponseEntity getAllTodos(){
        return ResponseEntity.ok().body(todoService.getAllTodos());
    }

    //AuthenticationPrincipla >> take username and password from login ! << only things with security stuff!
    //fetch info that have relation with security!
    @GetMapping("/get")
    public ResponseEntity geMyTodos(@AuthenticationPrincipal User user){
        return ResponseEntity.ok().body(todoService.getMyTodos(user.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity addTodos(@AuthenticationPrincipal User user, @RequestBody @Valid Todo todo){
        todoService.addTodo(user.getId(), todo);
        return ResponseEntity.ok().body(new ApiResponse("todo added successfully!"));
    }

    @PutMapping("/update/{todo_id}")
    public ResponseEntity updateTodo(@AuthenticationPrincipal User user, @PathVariable Integer todo_id, @RequestBody Todo todo){
        todoService.updateTodo(user.getId(), todo_id, todo);
        return ResponseEntity.ok().body(new ApiResponse("todo updated successfully!"));
    }
    @DeleteMapping("/delete/{todo_id}")
    public ResponseEntity deleteTodo(@AuthenticationPrincipal User user,@PathVariable Integer todo_id){
        todoService.deleteTodo(user.getId(), todo_id);
        return ResponseEntity.ok().body(new ApiResponse("todo deleted successfully!"));
    }
}
