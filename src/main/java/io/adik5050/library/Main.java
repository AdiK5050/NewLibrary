package io.adik5050.library;

import io.adik5050.library.database.DatabaseConnection;
import io.adik5050.library.database.DatabaseOperations;
import io.adik5050.library.storage.BookShelf;
import io.adik5050.library.storage.EditLibrary;
import io.adik5050.library.storage.LibraryInteraction;
import io.adik5050.library.user.*;

import java.util.Scanner;

/**
 * @author Aditya
 * @version 1.01
 * @since September,2025
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        final DatabaseConnection databaseConnection = new DatabaseConnection(args[0]);
        final DatabaseOperations databaseOperations = new DatabaseOperations(databaseConnection);
        final BookShelf bookShelf = new BookShelf(databaseOperations.getBookDao());
        final Display display = new TerminalDisplay();
        final Input input = new TerminalInput(sc, display);
        final LibraryInteraction interactionObj = new LibraryInteraction(bookShelf);
        final EditLibrary editLibraryObj = new EditLibrary(bookShelf);

        UserMenu userMenu = new UserMenu(
                sc,
                input,
                display,
                bookShelf,
                interactionObj,
                editLibraryObj);
        userMenu.menu();
    }
}