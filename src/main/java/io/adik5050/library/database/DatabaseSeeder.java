package io.adik5050.library.database;

import io.adik5050.library.database.entities.book.Book;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseSeeder {
    DatabaseOperations databaseOperations;

    public DatabaseSeeder(DatabaseOperations databaseOperations) {
        this.databaseOperations = databaseOperations;
    }
    public  void seedBooks() throws IOException {
        List<String> allBooksString = new ArrayList<>();
        List<Book> allBooks = new ArrayList<>();
        allBooksString.addAll(Files.readAllLines(Path.of("/home/adi/Personal/Work/Maven Project/NewLibrary/src/main/resources/books.csv")));
        for(String bookString: allBooksString) {
            Book book = parseBookStringToBook(bookString);
            allBooks.add(book);
            System.out.println(allBooks.size());
        }
        databaseOperations.insertBook(allBooks);
    }

    public  Book parseBookStringToBook(String bookString) {
        String bookParams[] = bookString.split(";");
        return new Book(UUID.randomUUID().toString(),bookParams[0], bookParams[1], bookParams[2], bookParams[3]);
    }
}
