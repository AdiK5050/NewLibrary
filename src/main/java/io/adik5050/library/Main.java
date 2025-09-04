package io.adik5050.library;

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
        UserMenu userMenu = new UserMenu(sc);
        userMenu.menu();
    }
}