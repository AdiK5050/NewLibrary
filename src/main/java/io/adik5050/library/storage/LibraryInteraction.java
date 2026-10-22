package io.adik5050.library.storage;

import io.adik5050.library.database.entities.book.Book;
import io.adik5050.library.database.entities.book.BookStatus;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
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
    public List<Book> allBooks() {
        return bookShelf.bookDao.getAllBooks();
    }

    /**
     * this method returns the book searched for or similar books if exact match is not found.
     * @param bookName name of the book to be searched.
     */
    public List<Book> searchBook(String bookName) {
        return bookShelf.bookDao.searchBookByName(bookName);
    }

    /**
     * this method issues book.
     * @param bookName name of the book to be issued.
     * @param username name of the user who issued the book.
     */
    public void issuing(String bookName, String username) {

    }

    /**
     * this method issues the book if found and updates library;
     * @param bookName name of the book to be issued.
     * @param username name of the user issuing the book.
     * @throws IOException IOException
     */
    public boolean issueBook(String bookName, String username) {
        try {
            Book selectedBook = bookShelf.bookDao.searchBookByName(bookName).getFirst();
            bookShelf.bookDao.updateBookStatus(selectedBook, BookStatus.ISSUED);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Couldn't Issue Book: Book Not Found.", e);
        }
        return true;
    }

    /**
     * this method returns the book.
     * @param bookName name of the book to be returned
     * @param username name of the user who is returning the book
     */
    public void returning(String bookName, String username) {

    }

    /**
     * this method returns the book and updates library.
     * Note:- The user who issues the book and the user who returns the book must be same.
     * @param bookName name of the book to be returned.
     * @param username name of the user returning the book.
     * @throws IOException IOException.
     */
    public boolean returnBook(String bookName, String username) throws IOException {
        return true;
    }
}

