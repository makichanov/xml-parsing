package by.epam.task02.builder;

import by.epam.task02.entity.Deposit;
import by.epam.task02.exception.BankException;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractBankBuilder {
    protected Set<Deposit> deposits;

    public AbstractBankBuilder() {
        deposits = new HashSet<>();
    }

    public Set<Deposit> getDeposits() {
        return deposits;
    }

    public abstract void buildSetDeposits(String filename) throws BankException;
}
