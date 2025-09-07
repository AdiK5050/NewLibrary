package io.adik5050.library;

import io.adik5050.library.storage.*;
import io.adik5050.library.user.Input;
import io.adik5050.library.user.TerminalDisplay;
import io.adik5050.library.user.UserMenu;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Aditya
 * @version 0.1
 * @since September,2025
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        final Input input = new Input(sc);
        final TerminalDisplay terminalDisplay = new TerminalDisplay();
        final BookShelf bookShelf = new BookShelf();
        final LibraryInteraction interactionObj = new LibraryInteraction(bookShelf);
        final EditLibrary editLibraryObj = new EditLibrary(bookShelf);
        UserMenu userMenu = new UserMenu(sc, input, terminalDisplay, bookShelf,interactionObj, editLibraryObj);
        userMenu.menu();
    }
}