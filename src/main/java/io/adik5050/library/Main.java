package io.adik5050.library;

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
    public static void main(String[] args) throws IOException {
        ProcessBuilder notepad;
        notepad = new ProcessBuilder("notepad.exe","C:/Users/kumra/.newLibrary/Screen.txt");
        notepad.start();
        Scanner sc = new Scanner(System.in);
        final BookShelf bookShelf = new BookShelf();
        final Non_TerminalDisplay display = new Non_TerminalDisplay(bookShelf);
        final Non_TerminalInput input = new Non_TerminalInput(sc, display);
        final LibraryInteraction interactionObj = new LibraryInteraction(bookShelf);
        final EditLibrary editLibraryObj = new EditLibrary(bookShelf);
        UserMenu userMenu = new UserMenu(sc, input, display, bookShelf,interactionObj, editLibraryObj);
        userMenu.menu();
    }
}