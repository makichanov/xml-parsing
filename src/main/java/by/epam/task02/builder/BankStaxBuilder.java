package by.epam.task02.builder;

import by.epam.task02.entity.Amount;
import by.epam.task02.entity.CurrencyAmount;
import by.epam.task02.entity.Deposit;
import by.epam.task02.entity.MetalAmount;
import by.epam.task02.exception.BankException;
import by.epam.task02.handler.BankXMLTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;

public class BankStaxBuilder extends AbstractBankBuilder {
    private static Logger LOG = LogManager.getLogger();
    private XMLInputFactory inputFactory;

    public BankStaxBuilder() {
        super();
        inputFactory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildSetDeposits(String filename) throws BankException {
        XMLStreamReader reader;
        String name;
        try (FileInputStream inputStream = new FileInputStream(new File(filename))) {
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (name.equals(BankXMLTag.DEPOSIT.getTitle())) {
                        Deposit deposit = buildDeposit(reader);
                        deposits.add(deposit);
                    }
                }
            }
        } catch (XMLStreamException | FileNotFoundException e) {
            LOG.error("Error in StAX: " + e.getMessage());
            throw new BankException("Error in Stax: " + e.getMessage(), e);
        } catch (IOException e) {
            LOG.error("Error in Stax, check your filename: " + filename);
            throw new BankException("Error in Stax, check your filename: " + filename, e);
        }
        LOG.info("Deposits from stax builder are:\n" + deposits);
    }

    private Deposit buildDeposit(XMLStreamReader reader) throws XMLStreamException {
        Deposit deposit = new Deposit();
        deposit.setId(reader.getAttributeValue(null, BankXMLTag.ID.getTitle()));
        var country = reader.getAttributeValue(null, BankXMLTag.COUNTRY.getTitle());
        if (country != null && !country.isEmpty()) {
            deposit.setCountry(country);
        }
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    parseTag(deposit, reader, name);
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (BankXMLTag.valueOf(name.toUpperCase().replaceAll("-", "_")) == BankXMLTag.DEPOSIT) {
                        return deposit;
                    }
            }
        }
        throw new XMLStreamException("Unknown element in tag <deposit>");
    }

    private void parseTag(Deposit deposit, XMLStreamReader reader, String tagName) throws XMLStreamException {
        String value;
        switch (BankXMLTag.valueOf(tagName.toUpperCase().replaceAll("-", "_"))) {
            case BANK:
                value = getXMLText(reader);
                deposit.setBankName(value);
                break;
            case DEPOSITOR:
                setDepositorProperties(deposit, reader);
                break;
            case TYPE:
                value = getXMLText(reader);
                Deposit.DepositType type = Deposit.DepositType.valueOf(value.toUpperCase()
                        .replaceAll("-", "_"));
                deposit.setType(type);
                break;
            case CURRENCY_AMOUNT:
                Amount currencyAmount = new CurrencyAmount(
                        reader.getAttributeValue(null, BankXMLTag.CURRENCY.getTitle()));
                value = getXMLText(reader);
                currencyAmount.setAmount(Double.parseDouble(value));
                deposit.setAmount(currencyAmount);
                break;
            case METAL_AMOUNT:
                Amount metalAmount = new MetalAmount(
                        reader.getAttributeValue(null, BankXMLTag.METAL.getTitle()));
                value = getXMLText(reader);
                metalAmount.setAmount(Double.parseDouble(value));
                deposit.setAmount(metalAmount);
                break;
            case PROFITABILITY:
                value = getXMLText(reader);
                deposit.setProfitability(Double.parseDouble(value.replace("%", "")));
                break;
            case TIME:
                value = getXMLText(reader);
                deposit.setDate(LocalDate.parse(value));
                break;
        }
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }

    private void setDepositorProperties(Deposit deposit, XMLStreamReader reader) throws XMLStreamException {
        int type;
        String name;
        while (reader.hasNext()) {
            type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (BankXMLTag.valueOf(name.toUpperCase().replaceAll("-", "_"))) {
                        case FIRST_NAME:
                            deposit.setDepositorFirstName(getXMLText(reader));
                            break;
                        case LAST_NAME:
                            deposit.setDepositorLastName(getXMLText(reader));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (BankXMLTag.valueOf(name.toUpperCase()
                            .replaceAll("-", "_")) == BankXMLTag.DEPOSITOR) {
                        return;
                    }
            }
        }
        throw new XMLStreamException("Unknown element in tag <deposit>");
    }

}
