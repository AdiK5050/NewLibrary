package io.adik5050.library.user;

import io.adik5050.library.database.entities.book.Book;

import java.util.List;

 public interface Display {

     void displayMenu();

     void userPrompts(String prompts);

     void searchOutput(List<Book> searchedBooks);

     void addOutput(boolean alwaysTrue);

     void removeOutput(boolean bool);

     void issueOutput(String username,boolean bool);

     void returnOutput(String username, boolean bool);

     void showBookOutput(List<Book> allBooks);

}
