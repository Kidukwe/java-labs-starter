package edu.course.lab02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankAccountTest {

    @Test
    void withdrawDecreasesBalance() {
        BankAccount account = new BankAccount(100);

        account.withdraw(40);

        assertEquals(60.0, account.getBalance());
    }

    @Test
    void throwsExceptionWhenWithdrawExceedsBalance() {
        BankAccount account = new BankAccount(100);

        assertThrows(IllegalArgumentException.class, () -> account.withdraw(150));
    }

    @Test
    void throwsExceptionForNegativeInitialBalance() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount(-100));
    }
}
