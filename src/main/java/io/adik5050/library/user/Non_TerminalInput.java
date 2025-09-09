package io.adik5050.library.user;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Non_TerminalInput implements Input {

    Scanner sc;
    Non_TerminalDisplay non_terminalDisplay;

    public Non_TerminalInput(Scanner sc ,Non_TerminalDisplay non_terminalDisplay) {
        this.sc = sc;
        this.non_terminalDisplay = non_terminalDisplay;
    }

    /**
     * to input username.
     * @return returns username.
     */
    @Override
    public String usernameInput() {
        String input;
        non_terminalDisplay.askUserInput("Enter your name: ");
        input = sc.nextLine();
        non_terminalDisplay.askUserInput(input + "\n");
        return input;
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
                non_terminalDisplay.askUserInput("\nChoose an option:- ");
                input = sc.nextLine();
                non_terminalDisplay.askUserInput(input + "\n");
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                if(i > 2) break;
                non_terminalDisplay.askUserInput("\nInvalid Input! Please enter a valid Integer.");
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
        String input;
        non_terminalDisplay.askUserInput("\nEnter the name of the book: ");
        input = sc.nextLine();
        non_terminalDisplay.askUserInput(input + "\n");
        return input;
    }

    /**
     * to input multiple book names for add and remove operations.
     * @return list of book names.
     */
    @Override
    public List<String> multipleBookInput() {
        List<String> books = new ArrayList<>();
        String inputBookName;
        int inputNumberOfBooks = -1, count = 2, i = 1;
        while (count != 0) {
            try {
                non_terminalDisplay.askUserInput("\nHow many books do you want to add/remove?: ");
                inputNumberOfBooks = sc.nextInt();
                non_terminalDisplay.askUserInput(String.valueOf(inputNumberOfBooks));
                break;
            } catch (InputMismatchException e) {
                non_terminalDisplay.askUserInput("\nInvalid Input! Please enter a valid Integer.");
                count--;
            }
        }
        sc.nextLine();
        while(i <= inputNumberOfBooks) {
            non_terminalDisplay.askUserInput("\nEnter name of the book " + i + ": ");
            inputBookName = sc.nextLine();
            non_terminalDisplay.askUserInput(inputBookName + "\n");
            books.add(inputBookName);
            i++;
        }
        return books;
    }
}

