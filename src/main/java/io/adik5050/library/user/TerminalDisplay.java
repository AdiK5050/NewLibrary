package io.adik5050.library.user;

import java.util.List;
import java.util.StringJoiner;

/**
 * This class provides outputs.
 * The soul purpose of this class is to deal with output according to the situation.
 * Also, to help operation methods(issueBook, removeBooks...) to be unbothered by output handing.
 */
public class TerminalDisplay implements Display{

    /**
     * this method displays menu options.
     */
    @Override
    public void displayMenu() {
        int i = 0;
        for (UserMenu.MenuOptions option : UserMenu.MenuOptions.values()) {
            System.out.println("\nPress "+ i + " to " + option.title);
            i++;
        }
        System.out.print("Choose an option:- ");
    }

    /**
     * this method returns if searched book or similar books are present.
     * @param searchedBooks name of the books similar to searched books.
     */
    @Override
    public void searchOutput(List<String> searchedBooks) {
        if(searchedBooks.isEmpty()) { System.out.println("No Such Book Found."); return; }
        System.out.println(searchedBooks.size() + " Similar Books Found.");
        searchedBooks.forEach(System.out::println);
    }

    /**
     * this method always provides the same output when books are added.
     * @param alwaysTrue a fake flag.
     */
    @Override
    public void addOutput(boolean alwaysTrue) {
        if(alwaysTrue) System.out.println("Added Books Successfully.");
    }

    /**
     * this method provides output if book removal was successful.
     * @param bool success status of removal book operation.
     */
    @Override
    public void removeOutput(boolean bool) {
        if(!bool) { System.out.println("Books Unavailable \nCouldn't remove Books!"); return; }
        System.out.println("Removed Books Successfully");
    }

    /**
     * this class provides output if book issuing was successful.
     * @param username of the user issuing book
     * @param bool success status of issuing book operation.
     */
    @Override
    public void issueOutput(String username,boolean bool) {
        if(!bool) { System.out.println("Exact Match Not Found! \nCouldn't issue book."); return; }
        System.out.println("Match Found!" + "\nBook issued by " + username);
    }

    /**
     * this method provides output if book returning was successful.
     * @param username username of the user returning book.
     * @param bool success status of returning book operation.
     */
    @Override
    public void returnOutput(String username, boolean bool) {
        if(!bool) { System.out.println("Exact Match Not Found! \nCouldn't return book."); return; }
        System.out.println("Match Found!" + "\nBook returned by " + username);
    }

    /**
     * this class provides output of all the books available in the library.
     * @param allBooks a list of all books available in the library.
     */
    @Override
    public void showBookOutput(List<String> allBooks) {
        for(int i = 0; i < allBooks.size() - 1; i += 2) {
            System.out.println(new StringJoiner(" ; ", "[ ", " ]").add(allBooks.get(i)).add(allBooks.get(i + 1)));
        }
        if(allBooks.size() % 2 != 0)
            System.out.println("[ " + allBooks.getLast() + " ]");
    }
}

