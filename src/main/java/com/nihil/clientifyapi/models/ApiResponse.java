package com.nihil.clientifyapi.models;

public record ApiResponse<T>(boolean ok, String message, T data) {}