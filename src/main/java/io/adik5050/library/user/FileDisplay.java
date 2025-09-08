package io.adik5050.library.user;


import io.adik5050.library.storage.BookShelf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.StringJoiner;

public class FileDisplay implements Display{

    BookShelf bookShelf;
    Path outputPath;
    ProcessBuilder notepad;
    StringBuilder output = new StringBuilder();


    public FileDisplay(BookShelf bookShelf) throws IOException {
        this.bookShelf = bookShelf;
        this.outputPath = bookShelf.path("Screen.txt");
        this.notepad = new ProcessBuilder("notepad.exe","C:/Users/kumra/.newLibrary/Screen.txt");
        notepad.start();
    }
    /**
     * this method displays menu options.
     */
    @Override
    public void displayMenu() {
        int i = 0;
        for (UserMenu.MenuOptions option : UserMenu.MenuOptions.values()) {
            output.append("\nPress ").append(i).append(" to ").append(option.title);
            i++;
        }
        output.append("\nChoose an option:- ");
        newFrame(output.toString());
        output.setLength(0);
    }

    /**
     * this method overrides the output on the screen.
     * @param output output to be displayed on screen.
     */
    public void newFrame(String output) {
        try {
            Files.write(outputPath, output.getBytes());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * this method returns if searched book or similar books are present.
     * @param searchedBooks name of the books similar to searched books.
     */
    @Override
    public void searchOutput(List<String> searchedBooks) {
        if(searchedBooks.isEmpty()) { output.append("No Such Book Found.\n"); return; }
        output.append(searchedBooks.size()).append(" Similar Books Found.\n");
        searchedBooks.forEach(str-> output.append(str).append("\n"));
        newFrame(output.toString());
    }

    /**
     * this method always provides the same output when books are added.
     * @param alwaysTrue a fake flag.
     */
    @Override
    public void addOutput(boolean alwaysTrue) {
        if(alwaysTrue) output.append("Added books Successfully.\n");
        newFrame(output.toString());
    }

    /**
     * this method provides output if book removal was successful.
     * @param bool success status of removal book operation.
     */
    @Override
    public void removeOutput(boolean bool) {
        if(!bool) { output.append("Books Unavailable \nCouldn't remove Books!\n"); return; }
        output.append("Removed Books Successfully\n");
        newFrame(output.toString());
    }

    /**
     * this class provides output if book issuing was successful.
     * @param username of the user issuing book
     * @param bool success status of issuing book operation.
     */
    @Override
    public void issueOutput(String username, boolean bool) {
        if(!bool) { output.append("Exact Match Not Found! \nCouldn't issue book."); return; }
        output.append("Match Found!\nBook issued by ").append(username);
        newFrame(output.toString());
    }

    /**
     * this method provides output if book returning was successful.
     * @param username username of the user returning book.
     * @param bool success status of returning book operation.
     */
    @Override
    public void returnOutput(String username, boolean bool) {
        if(!bool) { output.append("Exact Match Not Found! \nCouldn't return book."); return; }
        output.append("Match Found!\nBook returned by ").append(username);
        newFrame(output.toString());
    }

    /**
     * this class provides output of all the books available in the library.
     * @param allBooks a list of all books available in the library.
     */
    @Override
    public void showBookOutput(List<String> allBooks) {
        for(int i = 0; i < allBooks.size() - 1; i += 2) {
            output.append(new StringJoiner(" ; ", "[ ", " ]\n").add(allBooks.get(i)).add(allBooks.get(i + 1)));
        }
        if(allBooks.size() % 2 != 0)
            output.append("[ ").append(allBooks.getLast()).append(" ]\n");
        newFrame(output.toString());
    }
}
