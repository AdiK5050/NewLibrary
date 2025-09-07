package io.adik5050.library.storage;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * this class helps user to interact with library
 * Users can either issue or return book one at a time
 */
public class LibraryInteraction {

    BookShelf bookShelf;

    public LibraryInteraction(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
    }

    /**
     * This method returns all books in the library.
     * @return list of all books.
     */
    public List<String> allBooks() {
        return bookShelf.books;
    }

    /**
     * this method returns the book searched for or similar books if exact match is not found.
     * @param bookName name of the book to be searched.
     */
    public List<String> searchBook(String bookName) {
        Pattern pattern = Pattern.compile(".*" + String.join(".*",bookName.trim().split(""))+ ".*");
        return bookShelf.books.stream()
                .filter(name-> {
                    return pattern.matcher(name).matches();
                }).toList();
    }

    /**
     * this method issues book.
     * @param bookName name of the book to be issued.
     * @param username name of the user who issued the book.
     */
    public void issuing(String bookName, String username) {
        bookShelf.books.remove(bookName);
        bookShelf.issuedBooks.add(bookName + " issued by " + username);
    }

    /**
     * this method issues the book if found and updates library;
     * @param bookName name of the book to be issued.
     * @param username name of the user issuing the book.
     * @throws IOException IOException
     */
    public boolean issueBook(String bookName, String username) throws IOException {
        if(!bookShelf.books.contains(bookName.trim())) return false;
        issuing(bookName, username);
        bookShelf.updateLibrary(bookShelf.books,bookShelf.issuedBooks);
        return true;
    }

    /**
     * this method returns the book.
     * @param bookName name of the book to be returned
     * @param username name of the user who is returning the book
     */
    public void returning(String bookName, String username) {
        bookShelf.books.add(bookName);
        bookShelf.issuedBooks.remove(bookName + " issued by " + username);
    }

    /**
     * this method returns the book and updates library.
     * Note:- The user who issues the book and the user who returns the book must be same.
     * @param bookName name of the book to be returned.
     * @param username name of the user returning the book.
     * @throws IOException IOException.
     */
    public boolean returnBook(String bookName, String username) throws IOException {
        if(bookShelf.books.contains(bookName.trim()) || !bookShelf.issuedBooks.contains(bookName + " issued by " + username)) {
            return false;
        }
        returning(bookName, username);
        bookShelf.updateLibrary(bookShelf.books,bookShelf.issuedBooks);
        return true;
    }
}

