package com.task.userrepositoriestask.dto.github;

public record RawGithubRepository(
        String name,
        Owner owner,
        Boolean fork
) {}
