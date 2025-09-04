package io.adik5050.library.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Pattern;

/**
 * This class deals with all the storage library functions.
 */
public class BookShelf {
    private final List<String> books = new ArrayList<>();
    private final List<String> issuedBooks = new ArrayList<>();

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
    private static Path path(String filename) throws IOException {
        Path finalPath = Path.of(System.getProperty("user.home"),".newLibrary", filename);
        if(!Files.exists(finalPath)) Files.createDirectories(finalPath.getParent());
        if(!Files.exists(finalPath)) Files.createFile(finalPath);
        return finalPath;
    }

    public static void updateLibrary(List<String> books, List<String> issuedBooks) throws IOException {
        Files.write(path("Books.txt"),books);
        Files.write(path("issuedBooks.txt"), issuedBooks);
        System.out.println("Library Refreshed!");
    }

    /**
     * this class helps user to interact with library
     * Users can either issue or return book one at a time
     */
    public static class Interaction {
        private final BookShelf bookShelf;

        public Interaction(BookShelf bookShelf) {
            this.bookShelf = bookShelf;
        }
        /**
         * This method shows available books in the library
         */
        public void showBooks() {
            StringJoiner bookPair = new StringJoiner(" ; ", "[", "]");
            int i = 0;
            for(String book : bookShelf.books) {
                if(book.isEmpty())continue;
                if(i == 2) { i = 0; bookPair.add("\n"); }
                bookPair.add(book);
                i++;
            }
            System.out.println(bookPair);
        }

        /**
         * This method searches and prints the name of the book or similar books.
         * @param bookName name of the book to be searched.
         */
        public void searchBook(String bookName) {
            if (bookShelf.books.contains(bookName)) {
                System.out.println("Book Found:- " + bookName);
                return;
            }
            System.out.println("Exact Match Not Found.");
            Pattern pattern = Pattern.compile(".*" + String.join(".*",bookName.trim().split(""))+ ".*");
            bookShelf.books.stream()
                    .filter(name-> {
                        return pattern.matcher(name).matches();
                    }).forEach(System.out::println);
            }

        /**
         * This method issues book and updates the library
         * @param bookName name of the book to be issued
         * @param username name of the user who issued the book
         */
        public void issueBook(String bookName, String username) throws IOException{
            if(!bookShelf.books.contains(bookName)) System.out.println("Book Unavailable");
            bookShelf.books.remove(bookName);
            bookShelf.issuedBooks.add(bookName + " issued by " + username);
            System.out.println(bookName + " issued by " + username);
            updateLibrary(bookShelf.books,bookShelf.issuedBooks);
        }

        /**
         * this method returns the book to the library if it was earlier issued
         * Note:- The user who issues the book and the user who returns the book must be same.
         * @param bookName name of the book to be returned
         * @param username name of the user who is returning the book
         */
        public void returnBook(String bookName, String username) throws IOException{
            if(bookShelf.books.contains(bookName) || !bookShelf.issuedBooks.contains(bookName + " issued by " + username)){
                System.out.println("Book doesn't belong to the library");
                return;
            }
            bookShelf.books.add(bookName);
            bookShelf.issuedBooks.remove(bookName + " issued by " + username);
            System.out.println(bookName + " returned by " + username);
            updateLibrary(bookShelf.books,bookShelf.issuedBooks);
        }
    }

    /**
     * this class helps the user to edit the library
     * users can add or remove a number of books
     */
    public static class EditLibaray {
        private final BookShelf bookShelf;

        public EditLibaray(BookShelf bookShelf) {
            this.bookShelf = bookShelf;
        }

        /**
         * this method adds more than one book to the library
         * @param addBooks name of the books to be added
         * @throws IOException IOException
         */
        public void addBooks(List<String> addBooks) throws IOException {
            bookShelf.books.addAll(addBooks);
            updateLibrary(bookShelf.books, bookShelf.issuedBooks);
        }

        /**
         * this method removes more than one book to the library
         * @param removeBooks name of the books to be removed
         * @throws IOException IOException
         */
        public void removeBooks(List<String> removeBooks) throws IOException {
            if(!bookShelf.books.containsAll(removeBooks)) { System.out.println("Books Not Present in the library!"); return;}
            bookShelf.books.removeAll(removeBooks);
            updateLibrary( bookShelf.books, bookShelf.issuedBooks);
        }
    }
}
