package io.adik5050.library.database.entities.issuedBook;

public enum Action {
    ISSUE("Issue"),
    RETURN("Return"),
    ADD("Add"),
    REMOVE("Remove");

    String string;
    Action(String string) {
        this.string = string;
    }
}
