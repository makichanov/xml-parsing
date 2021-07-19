package by.epam.task02.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class BanksErrorHandler implements ErrorHandler {

    private static Logger LOG = LogManager.getLogger();

    @Override
    public void warning(SAXParseException e) {
        LOG.warn(getLineColumnNumber(e) + "-" + e.getMessage());
    }

    @Override
    public void error(SAXParseException e) {
        LOG.error(getLineColumnNumber(e) + " - " + e.getMessage());
    }

    @Override
    public void fatalError(SAXParseException e) {
        LOG.fatal(getLineColumnNumber(e) + " - " + e.getMessage());
    }

    private String getLineColumnNumber(SAXParseException e) {
        return e.getLineNumber() + " : " + e.getColumnNumber();
    }
}
