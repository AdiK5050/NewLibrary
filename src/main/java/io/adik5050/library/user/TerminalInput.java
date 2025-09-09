package io.adik5050.library.user;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TerminalInput implements Input {
    Scanner sc;
    public TerminalInput(Scanner sc) {
        this.sc = sc;
    }

    /**
     * to input username.
     * @return returns username.
     */
    @Override
    public String usernameInput() {
        System.out.print("Enter your name:");
        return sc.nextLine();
    }

    /**
     * to input user choice.
     * @return returns user choice.
     */
    @Override
    public int choiceInput() {
        String input; int i = 0;
        while (true) {
            try {
                System.out.println("\nChoose an option:- ");
                return sc.nextInt();
            } catch (InputMismatchException e) {
                if(i > 2) break;
                System.out.println("\nInvalid Input! Please enter a valid Integer.");
                sc.nextLine();
                i++;
            }
        }
        return -1;
    }

    /**
     * to input single book name for issue, return and search operations.
     * @return returns book name.
     */
    @Override
    public String singleBookInput() {
        sc.nextLine();
        System.out.print("Enter the name of the book: ");
        return sc.nextLine();
    }

    /**
     * to input multiple book names for add and remove operations.
     * @return list of book names.
     */
    @Override
    public List<String> multipleBookInput() {
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
}
