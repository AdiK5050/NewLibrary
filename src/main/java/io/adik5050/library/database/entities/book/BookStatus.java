package io.adik5050.library.database.entities.book;

public enum BookStatus {
    AVAILABLE("Available"),
    NOT_AVAILABLE("Not_Available");

    String string;
    BookStatus(String string) {
        this.string = string;
    }
}
