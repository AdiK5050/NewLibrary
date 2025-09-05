package io.adik5050.library.user;

import io.adik5050.library.storage.BookShelf;
import io.adik5050.library.util.Input;

import java.io.IOException;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * This class provides a user menu.
 * Two major roles this class fulfills:- To provide user options in the menu and To take user input.
 */
public class UserMenu {

    Scanner sc;
    final Input input;
    final BookShelf bookShelf;
    final BookShelf.Interaction interactionObj;
    final BookShelf.EditLibaray editLibraryObj;

    /**
     * class constructor to initialise class instances.
     * @param sc scanner object for user input.
     * @throws IOException IOException
     */
    public UserMenu(Scanner sc) throws IOException {
        this.sc = sc;
        this.input = new Input(sc);
        this.bookShelf = new BookShelf();
        this.interactionObj = bookShelf.new Interaction();
        this.editLibraryObj = bookShelf.new EditLibaray();
    }

    /**
     * Options stored as enum item for easy addition and removal of options.
     */
    enum MenuOptions {
        searchBook("Search a Book"),
        addBooks("Add Books"),
        removeBooks("Remove Books"),
        issueBook("Issue a Book"),
        returnBook("Return a Book"),
        showBooks("Show Books"),
        exit("Exit");

        private final String title;

        /**
         * enum constructor to add title for each option to be shown to the user.
         * @param title
         */
        MenuOptions(String title) {
            this.title = title;
        }
    }

    /**
     * Options to be stored as data and then displayed later.
     * @param title title of enumItem to be displayed.
     * @param enumItem enumItem.
     * @param <T> Generic
     */
    record Options<T> (String title, T enumItem) {
    }

    /**
     * to display menu options.
     */
    private void displayMenu() {
        int i = 0;
        System.out.println("Choose an option:- ");
        for (MenuOptions option : MenuOptions.values()) {
            System.out.println("\nPress "+ i + " to " + option.title);
            i++;
        }
    }

    /**
     * main menu method to summarise all operations for user.
     * @throws IOException IOException
     */
    public void menu() throws IOException {
        String userName = input.usernameInput();
        boolean running = true;
                List<Options<MenuOptions>> options = Stream.of(MenuOptions.values())
                        .map(menuItem -> new Options<>(menuItem.title, menuItem))
                        .toList();
        while(running) {
            displayMenu();
            try {
                MenuOptions option = options.get(input.choiceInput()).enumItem;
                switch (option) {
                    case searchBook -> interactionObj.searchBook(input.singleBookInput());

                    case addBooks -> editLibraryObj.addBooks(input.multipleBookInput());

                    case removeBooks -> editLibraryObj.removeBooks(input.multipleBookInput());

                    case issueBook -> interactionObj.issueBook(input.singleBookInput(), userName);

                    case returnBook -> interactionObj.returnBook(input.singleBookInput(), userName);

                    case showBooks -> interactionObj.showBooks();

                    case exit -> running = false;
                }
            }catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid choice input. Try again...");
            }
        }
    }
}
