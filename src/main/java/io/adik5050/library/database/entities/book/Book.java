package io.adik5050.library.database.entities.book;

public record Book (
        String uuid,
        String name,
        String author,
        String type,
        String status
) { }
