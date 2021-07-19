package by.epam.task02.builder;

import by.epam.task02.exception.BankException;
import by.epam.task02.handler.BanksErrorHandler;
import by.epam.task02.handler.BanksHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class BankSAXBuilder extends AbstractBankBuilder {

    private static Logger LOG = LogManager.getLogger();
    private BanksHandler handler = new BanksHandler();
    private XMLReader reader;

    public BankSAXBuilder() {
        super();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            reader = saxParser.getXMLReader();
        } catch (ParserConfigurationException | SAXException e) {
            LOG.error("Error in SAX: " + e.getMessage());
        }
        reader.setErrorHandler(new BanksErrorHandler());
        reader.setContentHandler(handler);
    }

    @Override
    public void buildSetDeposits(String filename) throws BankException {
        try {
            reader.parse(filename);
        } catch (SAXException ex) {
            LOG.info("Error in Sax: " + ex.getMessage());
            throw new BankException("Error in Sax: " + ex.getMessage());
        } catch (IOException ex) {
            LOG.error("Error in Sax, check your filename: " + filename);
            throw new BankException("Error in Sax, check your filename: " + filename);
        }
        deposits = handler.getDeposits();
        LOG.info("Minerals from sax builder are:\n" + deposits);
    }
}
