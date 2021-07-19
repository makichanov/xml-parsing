package by.epam.task02.builder;

import by.epam.task02.entity.Amount;
import by.epam.task02.entity.CurrencyAmount;
import by.epam.task02.entity.Deposit;
import by.epam.task02.entity.MetalAmount;
import by.epam.task02.exception.BankException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class BankBuilderTest {

    private static final String PATH_TO_TEST_XML = "/data/banks.xml";
    private static final List<Deposit> testDeposits = new ArrayList<>();
    private AbstractBankBuilder builder;

    @BeforeClass
    public static void setUp() {
        Deposit testDeposit1 = new Deposit();
        testDeposit1.setId("q1w5");
        testDeposit1.setBankName("Belarusbank");
        testDeposit1.setDepositorFirstName("Egor");
        testDeposit1.setDepositorLastName("Vasiliev");
        testDeposit1.setType(Deposit.DepositType.DEMAND);
        Amount amount1 = new CurrencyAmount("BYR");
        amount1.setAmount(500);
        testDeposit1.setAmount(amount1);
        testDeposit1.setProfitability(0.11);
        testDeposit1.setDate(LocalDate.parse("2021-06-11"));

        Deposit testDeposit2 = new Deposit();
        testDeposit2.setId("mjut7y");
        testDeposit2.setCountry("DE");
        testDeposit2.setBankName("Deutsche Bank");
        testDeposit2.setDepositorFirstName("Daniil");
        testDeposit2.setDepositorLastName("Shevchenkovich");
        testDeposit2.setType(Deposit.DepositType.METAL);
        Amount amount2 = new MetalAmount("XAU");
        amount2.setAmount(500);
        testDeposit2.setAmount(amount2);
        testDeposit2.setProfitability(0.12);
        testDeposit2.setDate(LocalDate.parse("2019-09-17"));

        testDeposits.add(testDeposit1);
        testDeposits.add(testDeposit2);
    }

    @Test
    public void domBuilderTest() throws BankException {
        builder = new BankDOMBuilder();

        List<Deposit> expected = testDeposits.stream()
                .sorted(Comparator.comparing(Deposit::getId))
                .collect(Collectors.toList());

        builder.buildSetDeposits(getClass().getResource(PATH_TO_TEST_XML).getFile());
        Set<Deposit> depositsFromXML = builder.getDeposits();
        List<Deposit> actual = depositsFromXML.stream()
                .sorted(Comparator.comparing(Deposit::getId))
                .collect(Collectors.toList());
        assertEquals(expected, actual);
    }

    @Test
    public void saxBuilderTest() throws BankException {
        builder = new BankSAXBuilder();

        List<Deposit> expected = testDeposits.stream()
                .sorted(Comparator.comparing(Deposit::getId))
                .collect(Collectors.toList());

        builder.buildSetDeposits(getClass().getResource(PATH_TO_TEST_XML).getFile());
        Set<Deposit> depositsFromXML = builder.getDeposits();
        List<Deposit> actual = depositsFromXML.stream()
                .sorted(Comparator.comparing(Deposit::getId))
                .collect(Collectors.toList());
        assertEquals(expected, actual);
    }

    @Test
    public void staxBuilderTest() throws BankException {
        builder = new BankStaxBuilder();

        List<Deposit> expected = testDeposits.stream()
                .sorted(Comparator.comparing(Deposit::getId))
                .collect(Collectors.toList());

        builder.buildSetDeposits(getClass().getResource(PATH_TO_TEST_XML).getFile());
        Set<Deposit> depositsFromXML = builder.getDeposits();
        List<Deposit> actual = depositsFromXML.stream()
                .sorted(Comparator.comparing(Deposit::getId))
                .collect(Collectors.toList());
        assertEquals(expected, actual);
    }

    @Test(expected = BankException.class)
    public void buildSetDepositsIncorrectFilepathTest() throws BankException {
        builder = new BankSAXBuilder();
        builder.buildSetDeposits(("no such filepath"));
    }
}
