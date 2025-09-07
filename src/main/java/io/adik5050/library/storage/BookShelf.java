package io.adik5050.library.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This class works as storage and provides file input output operations.
 */
public class BookShelf {
    public final List<String> books = new ArrayList<>();
    public final List<String> issuedBooks = new ArrayList<>();

    /**
     * The class constructor initialises the data from file to arraylist
     * @throws IOException IOException
     */
    public BookShelf() throws IOException{
        books.addAll(Files.readAllLines(path("Books.txt")));
        issuedBooks.addAll(Files.readAllLines(path("issuedBooks.txt")));
    }

    /**
     * this method returns a path adding the file name from the argument.
     * @param filename name of the file to be opened
     * @return finalPath path of the file to be opened
     * @throws IOException IOException
     */
    private Path path(String filename) throws IOException {
        Path finalPath = Path.of(System.getProperty("user.home"),".newLibrary", filename);
        if(!Files.exists(finalPath)) Files.createDirectories(finalPath.getParent());
        if(!Files.exists(finalPath)) Files.createFile(finalPath);
        return finalPath;
    }

    /**
     * this method writes the updated library in the designated files.
     * @param updatedBooks list of updated books in the library.
     * @param updatedIssuedBooks list of updated issued books in the library.
     * @throws IOException IOException.
     */
    public void updateLibrary(List<String> updatedBooks, List<String> updatedIssuedBooks) throws IOException {
        Files.write(path("Books.txt"),updatedBooks);
        Files.write(path("issuedBooks.txt"), updatedIssuedBooks);
    }
}
