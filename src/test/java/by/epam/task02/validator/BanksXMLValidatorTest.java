package by.epam.task02.validator;

import org.junit.Test;

import static org.junit.Assert.*;

public class BanksXMLValidatorTest {

    private final String PATH_TO_XML = getClass().getResource("/data/banks.xml").getFile();

    @Test
    public void xmlDocumentValidAccordingSchemaTest() {
        boolean condition = BanksXMLValidator.validate(PATH_TO_XML);
        assertTrue(condition);
    }

}