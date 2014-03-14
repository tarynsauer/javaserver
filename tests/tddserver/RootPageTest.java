package tddserver;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/13/14.
 */
public class RootPageTest extends TestHelpers {
    private RootPage root;

    @Before
    public void setUp() throws Exception {
        setUpParserForResponseTesting();
        root = new RootPage("/");
    }

    @Test
    public void testGetContent() throws Exception {
        String expectedResponse = "<h1>Hey, there!</h1>" +
                "<li><a href='file1'>file1</a></li>" +
                "<li><a href='file2'>file2</a></li>" +
                "<li><a href='image.gif'>image.gif</a></li>" +
                "<li><a href='image.jpeg'>image.jpeg</a></li>" +
                "<li><a href='image.png'>image.png</a></li>" +
                "<li><a href='partial_content.txt'>partial_content.txt</a></li>" +
                "<li><a href='text-file.txt'>text-file.txt</a></li>";
        assertEquals(root.getContent(requestParser), expectedResponse);
    }
}
