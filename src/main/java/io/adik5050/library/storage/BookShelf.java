package io.adik5050.library.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    }

    /**
     * this class helps user to interact with library
     * Users can either issue or return book one at a time
     */
    public class Interaction {

        /**
         * This method shows available books in the library
         */
        public void showBooks() {
            StringJoiner bookPair = new StringJoiner(" ; ", "[", "]");
            int i = 0;
            for(String book : books) {
                if(book.isEmpty())continue;
                if(i == 2) { i = 0; bookPair.add("\n"); }
                bookPair.add(book);
                i++;
            }
            System.out.println(bookPair);
        }

        /**
         * this method returns the book searched for or similar books if exact match is not found.
         * @param bookName name of the book to be searched.
         */
        public List<String> searchBook(String bookName) {
            Pattern pattern = Pattern.compile(".*" + String.join(".*",bookName.trim().split(""))+ ".*");
            return books.stream()
                    .filter(name-> {
                        return pattern.matcher(name).matches();
                    }).toList();
            }

        /**
         * this method searches for a book and print the book or similar books found.
          * @param bookName name of the book to be searched.
         */
        public void booksMatched(String bookName) {
            new Output().searchOutput(searchBook(bookName).size());
            searchBook(bookName).forEach(System.out::println);
        }

        /**
         * this method issues book and updates the library
         * @param bookName name of the book to be issued
         * @param username name of the user who issued the book
         */
        public void issuing(String bookName, String username) {
            books.remove(bookName);
            issuedBooks.add(bookName + " issued by " + username);
        }

        /**
         * this method issues the book if found, updates library and prints output.
         * @param bookName name of the book to be issued.
         * @param username name of the user issuing the book.
         * @throws IOException IOException
         */
        public void issueBook(String bookName, String username) throws IOException {
            if(!new Output().matchFound(bookName)) {
                new Output().issueOutput(bookName,username, false);
                return;
            }
            issuing(bookName, username);
            new Output().issueOutput(bookName,username,true);
            updateLibrary(books,issuedBooks);
        }

        /**
         * this method returns the book to the library if it was earlier issued
         * Note:- The user who issues the book and the user who returns the book must be same.
         * @param bookName name of the book to be returned
         * @param username name of the user who is returning the book
         */
        public void returning(String bookName, String username) {
            books.add(bookName);
            issuedBooks.remove(bookName + " issued by " + username);

        }

        /**
         * this method returns the book, updates library and prints output.
         * @param bookName name of the book to be returned.
         * @param username name of the user returning the book.
         * @throws IOException IOException.
         */
        public void returnBook(String bookName, String username) throws IOException {
            if(books.contains(bookName) || !issuedBooks.contains(bookName + " issued by " + username)) {
                new Output().returnOutput(bookName,username, false);
                return;
            }
            returning(bookName, username);
            new Output().returnOutput(bookName, username,true);
            updateLibrary(books,issuedBooks);
        }
    }

    /**
     * this class helps the user to edit the library
     * users can add or remove a number of books
     */
    public class EditLibaray {
        /**
         * this method adds more than one book to the library.
         * @param addBooks list of the books to be added.
         */
        public void add(List<String> addBooks) {
            books.addAll(addBooks);
        }

        /**
         * this method add books, updates library and prints output.
         * @param addBooks list of the books to be added.
         * @throws IOException IOException
         */
        public void addBooks(List<String> addBooks) throws IOException {
            add(addBooks);
            updateLibrary(addBooks,issuedBooks);
            new Output().bookAvailable(addBooks);
        }

        /**
         * this method removes more than one book to the library.
         * @param removeBooks list of the books to be removed.
         * @throws IOException IOException
         */
        public void remove(List<String> removeBooks) {
            books.removeAll(removeBooks);
        }

        /**
         * this method removes books if possible, updates library and prints output.
         * @param removeBooks list of the books to be removed
         * @throws IOException IOException
         */
        public void removeBooks(List<String> removeBooks) throws IOException {
            if(!new Output().bookAvailable(removeBooks))  return;
            remove(removeBooks);
            updateLibrary(books, issuedBooks);
            System.out.println("Library Refreshed!");
        }
    }

    /**
     * This class provides outputs.
     * The soul purpose of this class is to deal with output according to the situation.
     * Also, to help operation methods(issueBook, removeBooks...) to be unbothered by output handing.
     */
    private class Output {
        /**
         * returns true if match found and false if not.
         * @param bookName name of book to be compared.
         * @return boolean.
         */
        public boolean matchFound(String bookName) {
            if(!books.contains(bookName)) {
                System.out.println("Exact Match Not Found!");
                return false;
            }
            System.out.println("Match Found:-" + bookName);
            return true;
        }

        /**
         * return true if all books from arguments are available and false if not.
         * @param bookNames a list of book names passed as arguments.
         * @return boolean.
         */
        public boolean bookAvailable(List<String> bookNames) {
            if(!books.containsAll(bookNames)) {
                System.out.println("Books Unavailable!");
                return false;
            }
            System.out.println("Books Available. Addition/Removal successful");
            return true;
        }

        /**
         * prints output if books are issued or not.
         * @param bookName name of the book to be issued.
         * @param username name of the user issuing the book.
         * @param bool boolean.
         */
        public void issueOutput(String bookName, String username,boolean bool) {
            if(bool) System.out.println(bookName + " issued by " + username);
            else System.out.println("Couldn't issue book.");
        }

        /**
         * prints output if returning the book was a success or not.
         * @param bookName name of the book to be returned.
         * @param username name of the user returning the book.
         * @param bool boolean.
         */
        public void returnOutput(String bookName, String username, boolean bool) {
            if(bool) System.out.println(bookName + " returned by " + username);
            else System.out.println("Couldn't return book.");
        }

        public void searchOutput(int size) {
            if(size == 0) {
                System.out.println("No Such Book Found.");
                return;
            }
            System.out.println(size +" Similar Book Found.");
        }
    }
}
