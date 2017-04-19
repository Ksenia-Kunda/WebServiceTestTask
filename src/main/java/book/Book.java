package book;

/**
 * Created by Ksenia on 17.04.2017.
 */
public class Book {

    private String id;
    private String language;
    private String edition;
    private String author;

    public void setId(String id) {
        this.id = id;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public String getLanguage() {
        if (language == null){
            language = "";
        }
        return language;
    }

    public String getEdition() {
        if (edition == null){
            edition = "";
        }
        return edition;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", language='" + language + '\'' +
                ", edition='" + edition + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
