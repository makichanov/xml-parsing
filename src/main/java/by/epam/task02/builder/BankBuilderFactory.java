package by.epam.task02.builder;

import java.util.Locale;

public class BankBuilderFactory {
    private enum TypeParser {
        SAX, STAX, DOM
    }

    private BankBuilderFactory() {
    }

    public static AbstractBankBuilder createBankBuilder(String parserType) {
        TypeParser type = TypeParser.valueOf(parserType.toUpperCase());
        switch (type) {
            case DOM:
                return new BankDOMBuilder();
            case SAX:
                return new BankSAXBuilder();
            case STAX:
                return new BankStaxBuilder();
            default:
                throw new EnumConstantNotPresentException(
                        type.getDeclaringClass(), type.name());
        }
    }
}
