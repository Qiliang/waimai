package com.xiaoql.web;

import com.xiaoql.domain.User;
import com.xiaoql.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {


    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> users() {
        return userRepository.findAll();
    }

    @PutMapping("/users/{id}")
    public void users(@PathVariable String id, @RequestBody Map<String, Object> toUser) {
        User user = userRepository.getOne(id);
        user.update(toUser);
        userRepository.save(user);
    }

    @PostMapping("/users")
    public Object users(@RequestBody User toUser, HttpServletResponse response) {
        if (userRepository.findByLoginName(toUser.getLoginName()).size() > 0) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return new RestResponse(true, "登录名重复");
        }
        userRepository.save(toUser);
        return new RestResponse();
    }

    @DeleteMapping("/users/{id}")
    public Object usersDel(@PathVariable String id, HttpServletResponse response) {
        userRepository.delete(id);
        return new RestResponse();
    }

}
