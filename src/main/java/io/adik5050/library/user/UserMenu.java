package io.adik5050.library.user;

import io.adik5050.library.storage.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * This class provides a user menu.
 * Two major roles this class fulfills:- To provide user options in the menu and To take user terminalInput.
 */
public class UserMenu {

    Scanner sc;
    final Input input;
    final Display display;
    final BookShelf bookShelf;
    final LibraryInteraction interactionObj;
    final EditLibrary editLibraryObj;

    /**
     * class constructor to initialise class instances.
     * @param sc scanner object for user input.
     */
    public UserMenu(Scanner sc, Input input, Display display, BookShelf bookShelf, LibraryInteraction libraryInteraction, EditLibrary editLibraryObj) {
        this.sc = sc;
        this.input = input;
        this.display = display;
        this.bookShelf = bookShelf;
        this.interactionObj = libraryInteraction;
        this.editLibraryObj = editLibraryObj;
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

        final String title;

        /**
         * enum constructor to add title for each option to be shown to the user.
         * @param title title for option
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
            display.displayMenu();
            try {
                MenuOptions option = options.get(input.choiceInput()).enumItem;
                switch (option) {
                    case searchBook -> display.searchOutput(interactionObj.searchBook(input.singleBookInput()));

                    case addBooks -> display.addOutput(editLibraryObj.addBooks(input.multipleBookInput()));

                    case removeBooks -> display.removeOutput(editLibraryObj.removeBooks(input.multipleBookInput()));

                    case issueBook -> display.issueOutput(userName, interactionObj.issueBook(input.singleBookInput(), userName));

                    case returnBook -> display.returnOutput(userName, interactionObj.returnBook(input.singleBookInput(), userName));

                    case showBooks -> display.showBookOutput(interactionObj.allBooks());

                    case exit -> running = false;

                    default -> throw new IllegalStateException("Unexpected value: " + option);
                }
            }catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid choice input Try again...");
            }finally {
                bookShelf.updateLibrary(bookShelf.books,bookShelf.issuedBooks);
            }
        }
    }
}
