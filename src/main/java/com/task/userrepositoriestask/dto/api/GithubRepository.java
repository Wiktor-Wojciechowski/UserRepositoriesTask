package com.task.userrepositoriestask.dto.api;

import java.util.List;

public record GithubRepository(
        String name,
        String ownerLogin,
        List<Branch> branches
) {}
