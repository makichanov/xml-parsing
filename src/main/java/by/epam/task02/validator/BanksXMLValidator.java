package by.epam.task02.validator;

import by.epam.task02.handler.BanksErrorHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class BanksXMLValidator {
    private static Logger LOG = LogManager.getLogger();
    private final String BANKS_SCHEMA_NAME = getClass().getResource("data/banks.xsd").getFile();

    public boolean validate(String xmlFilePath) {
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);
        File schemaLocation = new File(BANKS_SCHEMA_NAME);
        try {
            Schema schema = factory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(xmlFilePath);
            validator.setErrorHandler(new BanksErrorHandler());
            validator.validate(source);
        } catch (SAXException | IOException ex) {
            LOG.log(Level.ERROR, "File can't be validate because: " + ex.getMessage());
            return false;
        }
        return true;
    }
}
