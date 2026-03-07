package io.adik5050.library;

import io.adik5050.library.database.DatabaseConnection;
import io.adik5050.library.storage.*;
import io.adik5050.library.user.*;

import java.io.IOException;
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
        final BookShelf bookShelf = new BookShelf();
        final Display display = new Non_TerminalDisplay(bookShelf);
        final Input input = new Non_TerminalInput(sc, display);
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