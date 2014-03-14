package tddserver;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/13/14.
 */
public class FormPageTest extends TestHelpers {
    private FormPage formPage;

    @Before
    public void setUp() throws Exception {
      setUpParserForResponseTesting();
      formPage = new FormPage("/form");
    }

    @Test
    public void testGetContentsDefault() throws Exception {
        assertEquals(formPage.getContent(requestParser), "<p data = >There may be a hidden name value here.</p>");
    }

    @Test
    public void testGetContentsAfterSettingDataValue() throws Exception {
        formPage.setData("cosby");
        assertEquals(formPage.getContent(requestParser), "<p data = cosby>There may be a hidden name value here.</p>");
    }
}
