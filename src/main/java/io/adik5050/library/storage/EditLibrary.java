package io.adik5050.library.storage;

import io.adik5050.library.database.dao.BookDao;
import io.adik5050.library.database.dao.HistoryDao;
import io.adik5050.library.database.entities.book.Book;
import io.adik5050.library.database.entities.issuedBook.Action;
import io.adik5050.library.database.entities.issuedBook.History;

import java.io.IOException;
import java.util.List;

/**
 * this class helps the user to edit the library
 * users can add or remove a number of books
 */
public class EditLibrary {

    BookDao bookDao;
    HistoryDao historyDao;
    public EditLibrary(BookDao bookDao, HistoryDao historyDao) {
        this.bookDao = bookDao;
        this.historyDao = historyDao;
    }

    /**
     * this method add books and updates library..
     * @param books list of the books to be added.
     */
    public boolean addBooks(List<Book> books) {
        bookDao.insertBook(books);
        for(Book book: books) {
            historyDao.insertHistory(book.uuid(), "abcd1234efgh5678ijkl", Action.ADD);
        }
        return true;
    }

    /**
     * this method removes books if possible and updates library.
     * @param books list of the books to be removed
     */
    public boolean removeBooks(List<Book> books) {
        bookDao.deleteBook(books);
        for(Book book: books) {
            historyDao.insertHistory(book.uuid(), "abcd1234efgh5678ijkl", Action.ADD);
        }
        return true;
    }
}
