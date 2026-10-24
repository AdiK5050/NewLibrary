package io.adik5050.library;

import io.adik5050.library.database.DatabaseConnection;
import io.adik5050.library.database.DatabaseOperations;
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
        final Display display = new TerminalDisplay();
        final Input input = new TerminalInput(sc, display);
        final LibraryInteraction interactionObj = new LibraryInteraction(databaseOperations.getBookDao(), databaseOperations.getHistoryDao());
        final EditLibrary editLibraryObj = new EditLibrary(databaseOperations.getBookDao(), databaseOperations.getHistoryDao());

        UserMenu userMenu = new UserMenu(
                sc,
                input,
                display,
                interactionObj,
                editLibraryObj);
        userMenu.menu();
    }
}