package converter;

import book.Book;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

/**
 * Created by Ksenia on 17.04.2017.
 */
public class XmlConverter {

    private List<Book> bookList;

    private static final String ID_TAG_NAME = "id";
    private static final String AUTHOR_TAG_NAME = "author";
    private static final String LANGUAGE_TAG_NAME = "language";
    private static final String EDITION_TAG_NAME = "edition";

    private static final String ROOT_TAG_NAME = "Book_List";
    private static final String ELEMENT_TAG_NAME = "Book";
    private static final String PATH = "\\src\\main\\resources\\book_list.xml";

    private Element element;
    private Element id;
    private Element author;
    private Element language;
    private Element edition;
    private Document document;

    public XmlConverter(List<Book> bookList) {
        this.bookList = bookList;
    }

    public void getReadyFile(){
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            Element theRoot = document.createElement(ROOT_TAG_NAME);
            document.appendChild(theRoot);

            for (Book book:bookList) {

                element = document.createElement(ELEMENT_TAG_NAME);
                theRoot.appendChild(element);

                id = document.createElement(ID_TAG_NAME);
                id.appendChild(document.createTextNode(book.getId()));
                element.appendChild(id);

                author = document.createElement(AUTHOR_TAG_NAME);
                author.appendChild(document.createTextNode(book.getAuthor()));
                element.appendChild(author);

                language = document.createElement(LANGUAGE_TAG_NAME);
                language.appendChild(document.createTextNode(book.getLanguage()));
                element.appendChild(language);

                edition = document.createElement(EDITION_TAG_NAME);
                edition.appendChild(document.createTextNode(book.getEdition()));
                element.appendChild(edition);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(System.getProperty("user.dir") + PATH));
            transformer.transform(source, streamResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
