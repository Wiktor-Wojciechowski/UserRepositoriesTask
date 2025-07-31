package com.task.userrepositoriestask.services;


import com.task.userrepositoriestask.dto.api.Branch;
import com.task.userrepositoriestask.dto.api.GithubRepository;
import com.task.userrepositoriestask.dto.github.RawBranch;
import com.task.userrepositoriestask.dto.github.RawGithubRepository;
import com.task.userrepositoriestask.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRepositoriesService {

    private final RestTemplate restTemplate;
    private final String githubApiUrl;

    public UserRepositoriesService(RestTemplate restTemplate, @Value("${github.api-url}") String githubApiUrl) {
        this.restTemplate = restTemplate;
        this.githubApiUrl = githubApiUrl;
    }

    public List<GithubRepository> getRepositories(String username) {
        String url = githubApiUrl + "/users/" + username + "/repos";
        try {
            ResponseEntity<List<RawGithubRepository>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );

            List<GithubRepository> repositories = new ArrayList<>();

            assert response.getBody() != null;
            for (RawGithubRepository rawRepo : response.getBody()) {
                if (rawRepo.fork()) continue;

                String repoName = rawRepo.name();
                String ownerLogin = rawRepo.owner().login();

                List<Branch> branches = getBranches(username, rawRepo.name());

                repositories.add(new GithubRepository(repoName, ownerLogin, branches));
            }
            return repositories;
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException(username);
        }
    }

    private List<Branch> getBranches(String user, String repo) {
        String url = githubApiUrl + "/repos/" + user + "/" + repo + "/branches";

        ResponseEntity<List<RawBranch>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );

        List<Branch> branches = new ArrayList<>();

        assert response.getBody() != null;
        for (RawBranch rawBranch : response.getBody()) {
            branches.add(new Branch(rawBranch.name(), rawBranch.commit().sha()));
        }

        return branches;
    }
}