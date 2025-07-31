package com.task.userrepositoriestask.dto.github;

public record RawBranch(
        String name,
        Commit commit
) {
}
