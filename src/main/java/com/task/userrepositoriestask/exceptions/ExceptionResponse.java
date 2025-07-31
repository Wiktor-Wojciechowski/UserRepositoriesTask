package com.task.userrepositoriestask.exceptions;

public record ExceptionResponse (
   int status,
   String message
) {}
