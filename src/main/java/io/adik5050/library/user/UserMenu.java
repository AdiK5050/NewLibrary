package io.adik5050.library.user;

import io.adik5050.library.storage.BookShelf;

import java.awt.print.Book;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * This class provides a user menu.
 * Two major roles this class fulfills:- To provide user options in the menu and To take user input.
 */
public class UserMenu {

    Scanner sc;
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
        bookShelf = new BookShelf();
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
     * to input user choice.
     * @return returns user choice.
     */
    private int choiceInput() {
        int input = -1,count = 0;
        while(count < 3) {
            try {
                input = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input! Please Enter a valid integer...");
                sc.nextLine();
                count++;
            }
        }
        return input;
    }

    /**
     * to input username.
     * @return returns username.
     */
     private String usernameInput() {
        System.out.print("Enter your name:");
        return sc.nextLine();
    }

    /**
     * to input multiple book names for add and remove operations.
     * @return list of book names.
     */
    private List<String> multipleBookInput() {
        List<String> bookNames = new ArrayList<>();
        int numOfBooks = 0, count = 0;
        while(count < 3) {
            try {
                System.out.print("How Many books do you want to add/remove? :");
                numOfBooks = sc.nextInt();
                sc.nextLine();
                break;
            } catch (InputMismatchException e1) {
                System.out.println("Invalid Input!");
                sc.nextLine();
                count++;
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }
        for(int i = 0; i < numOfBooks; i++) {
            String bookName;
            System.out.print("Enter Name of the Book:");
            bookName = sc.nextLine();
            bookNames.add(bookName);
        }
        return bookNames;
    }

    /**
     * to input single book name for issue, return and search operations.
     * @return returns book name.
     */

     private String singleBookInput() {
        sc.nextLine();
        System.out.print("Enter the name of the book: ");
        return sc.nextLine();
    }

    /**
     * main menu method to summarise all operations for user.
     * @throws IOException IOException
     */
    public void menu() throws IOException {
        String userName = usernameInput();
        boolean running = true;
                List<Options<MenuOptions>> options = Stream.of(MenuOptions.values())
                        .map(menuItem -> new Options<>(menuItem.title, menuItem))
                        .toList();
        while(running) {
            displayMenu();
            try {
                MenuOptions option = options.get(choiceInput()).enumItem;
                switch (option) {
                    case searchBook -> interactionObj.searchBook(singleBookInput());

                    case addBooks -> editLibraryObj.addBooks(multipleBookInput());

                    case removeBooks -> editLibraryObj.removeBooks(multipleBookInput());

                    case issueBook -> interactionObj.issueBook(singleBookInput(), userName);

                    case returnBook -> interactionObj.returnBook(singleBookInput(), userName);

                    case showBooks -> interactionObj.showBooks();

                    case exit -> running = false;
                }
            }catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid choice input. Try again...");
            }
        }
    }
}
