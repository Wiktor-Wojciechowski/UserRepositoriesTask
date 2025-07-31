package com.task.userrepositoriestask;

import com.task.userrepositoriestask.dto.api.Branch;
import com.task.userrepositoriestask.dto.api.GithubRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRepositoriesIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void shouldFetchNonForkRepositoriesWithNameOwnerAndBranches() {
        // Given
        String username = "octocat"; // octocat is an existing user

        // When
        String url = "http://localhost:" + port + "/users/" + username + "/repositories";
        ResponseEntity<GithubRepository[]> response = restTemplate.getForEntity(url, GithubRepository[].class);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        GithubRepository[] repositories = response.getBody();

        assertThat(repositories).isNotNull();
        assertThat(repositories.length).isEqualTo(6); //octocat has 8 repos but 2 are forks

        for (GithubRepository repo : repositories) {
            assertThat(repo.name()).isNotBlank();
            assertThat(repo.ownerLogin()).isEqualTo(username);
            assertThat(repo.branches()).isNotNull();

            for (Branch branch : repo.branches()) {
                assertThat(branch.name()).isNotBlank();
                assertThat(branch.sha()).isNotBlank();
            }
        }
    }
}