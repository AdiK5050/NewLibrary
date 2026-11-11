package io.adik5050.library.storage;

import io.adik5050.library.database.dao.BookDao;
import io.adik5050.library.database.dao.HistoryDao;
import io.adik5050.library.database.entities.book.Book;
import io.adik5050.library.database.entities.book.BookStatus;
import io.adik5050.library.database.entities.issuedBook.Action;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * this class helps user to interact with library
 * Users can either issue or return book one at a time
 */
public class LibraryInteraction {

    BookDao bookDao;
    HistoryDao historyDao;

    public LibraryInteraction(BookDao bookDao, HistoryDao historyDao)  {
        this.bookDao = bookDao;
        this.historyDao = historyDao;
    }

    /**
     * This method returns all books in the library.
     * @return list of all books.
     */
    public List<Book> allBooks() {
        return bookDao.getAllBooks();
    }

    /**
     * this method returns the book searched for or similar books if exact match is not found.
     * @param bookName name of the book to be searched.
     */
    public List<Book> searchBook(String bookName) {
        return bookDao.searchBookByName(bookName);
    }

    /**
     * this method issues the book if found and updates library;
     * @param bookName name of the book to be issued.
     * @param username name of the user issuing the book.
     */
    public boolean issueBook(String bookName, String username) {
        try {
            Book selectedBook = bookDao.searchBookByNameAndStatus(bookName, BookStatus.AVAILABLE).getFirst();
            bookDao.updateBookStatus(selectedBook.uuid(), BookStatus.NOT_AVAILABLE);
            historyDao.insertHistory(selectedBook.uuid(), username, Action.ISSUE);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Couldn't Issue Book: Either Book Not Available Or Doesn't Belong To The Library.", e);
        }
        return true;
    }

    /**
     * this method returns the book and updates library.
     * Note:- The user who issues the book and the user who returns the book must be same.
     * @param bookName name of the book to be returned.
     * @param username name of the user returning the book.
     */
    public boolean returnBook(String bookName, String username) {
        try {
            Book selectedBook = bookDao.searchBookByNameAndStatus(bookName, BookStatus.NOT_AVAILABLE).getFirst();
            bookDao.updateBookStatus(selectedBook.uuid(), BookStatus.AVAILABLE);
            historyDao.insertHistory(selectedBook.uuid(), username, Action.RETURN);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("Couldn't Issue Book: Either Book Already Available Or Doesn't Belong To The Library.", e);
        }
        return true;
    }
}

