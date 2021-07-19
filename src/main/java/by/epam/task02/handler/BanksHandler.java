package by.epam.task02.handler;

import by.epam.task02.entity.Amount;
import by.epam.task02.entity.CurrencyAmount;
import by.epam.task02.entity.Deposit;
import by.epam.task02.entity.MetalAmount;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class BanksHandler extends DefaultHandler {
    private Set<Deposit> deposits;
    private Deposit current;
    private Amount amount;
    private BankXMLTag currentTag;
    private EnumSet<BankXMLTag> tagsWithBody;

    public BanksHandler() {
        deposits = new HashSet<>();
        tagsWithBody = EnumSet.range(BankXMLTag.BANK, BankXMLTag.TIME);
    }

    public Set<Deposit> getDeposits() {
        return deposits;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (BankXMLTag.DEPOSIT.getTitle().equals(qName)) {
            current = new Deposit();
            for (int i = 0; i < attributes.getLength(); i++) {
                var attributeValue = attributes.getValue(i);
                if (BankXMLTag.ID.getTitle().equals(attributes.getQName(i))) {
                    current.setId(attributes.getValue(i));
                } else if (BankXMLTag.COUNTRY.getTitle().equals(attributes.getQName(i))) {
                    current.setCountry(attributeValue);
                }
            }
        } else if (BankXMLTag.CURRENCY_AMOUNT.getTitle().equals(qName)) {
            var currency = attributes.getValue(0);
            amount = new CurrencyAmount(currency);
            current.setAmount(amount);
            currentTag = BankXMLTag.CURRENCY_AMOUNT;
        } else if (BankXMLTag.METAL_AMOUNT.getTitle().equals(qName)) {
            var metal = attributes.getValue(0);
            amount = new MetalAmount(metal);
            current.setAmount(amount);
            currentTag = BankXMLTag.METAL_AMOUNT;

        } else {
            BankXMLTag temp = BankXMLTag.valueOf(qName.toUpperCase().replace("-", "_"));
            if (tagsWithBody.contains(temp)) {
                currentTag = temp;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (BankXMLTag.DEPOSIT.getTitle().equals(qName)) {
            deposits.add(current);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String tagData = new String(ch, start, length).strip();
        if (currentTag != null && !tagData.isBlank()) {
            switch (currentTag) {
                case BANK:
                    current.setBankName(tagData);
                    break;
                case FIRST_NAME:
                    current.setDepositorFirstName(tagData);
                    break;
                case LAST_NAME:
                    current.setDepositorLastName(tagData);
                    break;
                case TYPE:
                    Deposit.DepositType type = Deposit.DepositType.valueOf(tagData.toUpperCase());
                    current.setType(type);
                    break;
                case CURRENCY_AMOUNT:
                case METAL_AMOUNT:
                    current.setAmountValue(Double.parseDouble(tagData));
                    break;
                case PROFITABILITY:
                    current.setProfitability(Double.parseDouble(tagData.replace("%", "")));
                    break;
                case TIME:
                    current.setDate(LocalDate.parse(tagData));
                    break;
                default:
                    throw new EnumConstantNotPresentException(
                            currentTag.getDeclaringClass(), currentTag.name());
            }
        }
    }
}
