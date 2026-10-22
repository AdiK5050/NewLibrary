package io.adik5050.library.storage;

import io.adik5050.library.database.entities.book.Book;
import java.io.IOException;
import java.util.List;

/**
 * this class helps the user to edit the library
 * users can add or remove a number of books
 */
public class EditLibrary {

    BookShelf bookShelf;

    public EditLibrary(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
    }

    /**
     * this method add books and updates library..
     * @param addBooks list of the books to be added.
     * @throws IOException IOException
     */
    public boolean addBooks(List<Book> addBooks) throws IOException {
        bookShelf.bookDao.insertBook(addBooks);
        return true;
    }

    /**
     * this method removes books if possible and updates library.
     * @param removeBooks list of the books to be removed
     * @throws IOException IOException
     */
    public boolean removeBooks(List<Book> removeBooks) throws IOException {
        bookShelf.bookDao.deleteBook(removeBooks);
        return true;
    }
}
