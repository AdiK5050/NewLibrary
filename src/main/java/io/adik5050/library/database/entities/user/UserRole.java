package io.adik5050.library.database.entities.user;

public enum UserRole {
    ADMINISTRATOR("Administrator"),
    LIBRARIAN("Librarian"),
    USER("User");

    String string;
    UserRole(String string) {
        this.string = string;
    }
}
