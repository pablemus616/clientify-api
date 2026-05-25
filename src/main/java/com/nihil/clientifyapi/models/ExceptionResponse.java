package com.nihil.clientifyapi.models;

public record ExceptionResponse(Boolean ok, String message, String path) {
}
