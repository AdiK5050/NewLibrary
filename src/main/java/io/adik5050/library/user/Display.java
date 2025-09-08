package io.adik5050.library.user;

import java.util.List;

 public interface Display {

     void displayMenu();

     void searchOutput(List<String> searchedBooks);

     void addOutput(boolean alwaysTrue);

     void removeOutput(boolean bool);

     void issueOutput(String username,boolean bool);

     void returnOutput(String username, boolean bool);

     void showBookOutput(List<String> allBooks);

}
