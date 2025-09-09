package io.adik5050.library.user;

import java.util.List;

public interface Input {

    /**
     * to input user choice.
     * @return returns user choice.
     */
    int choiceInput();

    /**
     * to input username.
     * @return returns username.
     */
    String usernameInput();

    /**
     * to input multiple book names for add and remove operations.
     * @return list of book names.
     */
    List<String> multipleBookInput();

    String singleBookInput();
}
