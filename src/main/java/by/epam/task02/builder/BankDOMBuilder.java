package by.epam.task02.builder;

import by.epam.task02.entity.CurrencyAmount;
import by.epam.task02.entity.Deposit;
import by.epam.task02.entity.MetalAmount;
import by.epam.task02.exception.BankException;
import by.epam.task02.handler.BankXMLTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;

public class BankDOMBuilder extends AbstractBankBuilder {

    private static Logger LOG = LogManager.getLogger();
    private DocumentBuilder documentBuilder;

    public BankDOMBuilder() {
        super();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOG.error("Error in DOM: " + e.getMessage());
        }
    }

    @Override
    public void buildSetDeposits(String filename) throws BankException {
        Document doc;
        try {
            doc = documentBuilder.parse(filename);
            Element root = doc.getDocumentElement();
            NodeList depositsList = root.getElementsByTagName(BankXMLTag.DEPOSIT.getTitle());
            for (int i = 0; i < depositsList.getLength(); i++) {
                Element depositElement = (Element) depositsList.item(i);
                Deposit deposit = buildDeposit(depositElement);
                deposits.add(deposit);

            }
        } catch (IOException e) {
            LOG.error("Error in DOM parser, filename: " + filename);
            throw new BankException("Error in DOM parser, filename: " + filename, e);
        } catch (SAXException e) {
            LOG.error("Error in DOM parser" + filename);
            throw new BankException("Error in DOM parser" + filename, e);
        }
    }

    private Deposit buildDeposit(Element element) throws BankException {
        Deposit deposit = new Deposit();
        String id = element.getAttribute(BankXMLTag.ID.getTitle());
        if (id.isEmpty()) {
            throw new BankException("Attribute ID not found in element " + element);
        }
        deposit.setId(id);
        String country = element.getAttribute(BankXMLTag.COUNTRY.getTitle());
        if (country != null && !country.isBlank()) {
            deposit.setCountry(country);
        }
        Element bankElement = getChildElement(element, BankXMLTag.BANK.getTitle());
        deposit.setBankName(getElementTextContent(bankElement));
        Element depositorElement = (Element) element.getElementsByTagName(BankXMLTag.DEPOSITOR.getTitle()).item(0);

        Element depositorFirstNameElement = getChildElement(depositorElement, BankXMLTag.FIRST_NAME.getTitle());
        deposit.setDepositorFirstName(getElementTextContent(depositorFirstNameElement));
        Element depositorLastNameElement = getChildElement(depositorElement, BankXMLTag.LAST_NAME.getTitle());
        deposit.setDepositorLastName(getElementTextContent(depositorLastNameElement));

        Element typeElement = getChildElement(element, BankXMLTag.TYPE.getTitle());
        Deposit.DepositType type = Deposit.DepositType.valueOf(getElementTextContent(typeElement).toUpperCase());
        deposit.setType(type);

        Element amountElement = getChildElement(element, BankXMLTag.CURRENCY_AMOUNT.getTitle());
        if (amountElement != null) {
            String currency = amountElement.getAttribute(BankXMLTag.CURRENCY.getTitle());
            double amount = Double.parseDouble(getElementTextContent(amountElement));
            deposit.setAmount(new CurrencyAmount(currency, amount));
        }
        amountElement = getChildElement(element, BankXMLTag.METAL_AMOUNT.getTitle());
        if (amountElement != null) {
            String metal = amountElement.getAttribute(BankXMLTag.METAL.getTitle());
            double amount = Double.parseDouble(getElementTextContent(amountElement));
            deposit.setAmount(new MetalAmount(metal, amount));
        }

        Element profitabilityElement = getChildElement(element, BankXMLTag.PROFITABILITY.getTitle());
        String profitabilityString = getElementTextContent(profitabilityElement);
        double profitability = Double.parseDouble(profitabilityString.substring(0, profitabilityString.length() - 1));
        deposit.setProfitability(profitability);

        Element timeElement = getChildElement(element, BankXMLTag.TIME.getTitle());
        String timeString = getElementTextContent(timeElement);
        deposit.setDate(LocalDate.parse(timeString));

        return deposit;

    }

    private static Element getChildElement(Element parent, String elementName) {
        NodeList nodeList = parent.getElementsByTagName(elementName);
        return (Element) nodeList.item(0);
    }

    private static String getElementTextContent(Element element) throws BankException {
        String content = element.getTextContent();
        if (content != null && !content.isEmpty()) {
            return content;
        } else {
            throw new BankException("No content found in element");
        }
    }

}
