package com.task.userrepositoriestask.controllers;

import com.task.userrepositoriestask.dto.api.GithubRepository;
import com.task.userrepositoriestask.services.UserRepositoriesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRepositoriesController {
    private final UserRepositoriesService userRepositoriesService;

    public UserRepositoriesController(UserRepositoriesService service) {
        this.userRepositoriesService = service;
    }

    @GetMapping("/users/{user}/repositories")
    public List<GithubRepository> getRepositories(@PathVariable String user) {
        return userRepositoriesService.getRepositories(user);
    }
}


