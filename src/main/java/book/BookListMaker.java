package book;

import java.util.*;

/**
 * Created by Ksenia on 16.04.2017.
 */
public class BookListMaker {

    private String allLinesOfAllbooks[];
    private String[] allBookFields;
    private int bookCounter;
    private static final String AUTHOR = "author: ";
    private static final String LANGUAGE = "language: ";
    private static final String EDITION = "edition: ";
    List<Book> bookList;
    private Book book;

    public BookListMaker(String message) {
        this.allLinesOfAllbooks = message.replace("Author: ", AUTHOR).replace("Language: ", LANGUAGE).replace("Edition: ", EDITION).split("\n");
        bookCounter = 1;
        bookList = new ArrayList<Book>();
    }


    public List<Book> createBookMap() {
        int startOfaBookIndex;
        int endOfaBookIndex=0;

        System.out.println();
        for (int i = 0; i < allLinesOfAllbooks.length; i++) {
            if (allLinesOfAllbooks[i].startsWith(AUTHOR)) {
                book = new Book();
                startOfaBookIndex = i;
                for (int j = i + 1; j < allLinesOfAllbooks.length; j++) {
                    if (allLinesOfAllbooks[j].startsWith(AUTHOR) && j != allLinesOfAllbooks.length-1) {
                        endOfaBookIndex = j;
                        break;
                    }else {
                        endOfaBookIndex = allLinesOfAllbooks.length;
                    }
                }
                allBookFields = new String[endOfaBookIndex - startOfaBookIndex];
                int fieldCounter = i;
                for (int j = 0; j < allBookFields.length; j++) {
                    allBookFields[j] = allLinesOfAllbooks[fieldCounter];
                    fieldCounter++;
                }
                for (int j = 0; j < allBookFields.length; j++) {
                    if (allBookFields[j].startsWith(AUTHOR)) {
                        book.setAuthor(allBookFields[j].replace(AUTHOR, ""));
                    }
                    if (allBookFields[j].startsWith(LANGUAGE)) {
                        book.setLanguage(allBookFields[j].replace(LANGUAGE, ""));
                    }
                    if (allBookFields[j].startsWith(EDITION)) {
                        book.setEdition(allBookFields[j].replace(EDITION, ""));
                    }
                }
                book.setId(bookCounter);
                bookList.add(book);
                bookCounter++;
            }
        }
        return bookList;
    }
}
