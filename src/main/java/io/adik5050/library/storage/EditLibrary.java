package io.adik5050.library.storage;

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
    public boolean addBooks(List<String> addBooks) throws IOException {
        bookShelf.books.addAll(addBooks);
        bookShelf.updateLibrary(bookShelf.books, bookShelf.issuedBooks);
        return true;
    }

    /**
     * this method removes books if possible and updates library.
     * @param removeBooks list of the books to be removed
     * @throws IOException IOException
     */
    public boolean removeBooks(List<String> removeBooks) throws IOException {
        if(!bookShelf.books.containsAll(removeBooks)) return false;
        bookShelf.books.removeAll(removeBooks);
        bookShelf.updateLibrary(bookShelf.books, bookShelf.issuedBooks);
        return true;
    }
}
