package io.adik5050.library.user;

import java.util.List;

public interface Display {

    public void searchOutput(List<String> searchedBooks);

    public void addOutput(boolean alwaysTrue);

    public void removeOutput(boolean bool);

    public void issueOutput(String username,boolean bool);

    public void returnOutput(String username, boolean bool);

    public void showBookOutput(List<String> allBooks);
}
