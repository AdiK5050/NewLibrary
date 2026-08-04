package io.adik5050.library.database.entities.user;

public record User (
   String uuid,
   String name,
   String username,
   String password,
   String role
) {}
