package io.userinterface.creditcard;

public class CreditCard {
    private long number;
    private int expirationMonth;
    private int expirationYear;
    private int safetyNumber;

    public CreditCard() {}

    public CreditCard(long number, int expirationMonth, int expirationYear, int safetyNumber) {
        this.number = number;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.safetyNumber = safetyNumber;
    }

    public long getNumber() {
        return number;
    }
    public int getExpirationMonth() {
        return expirationMonth;
    }
    public int getExpirationYear() {
        return expirationYear;
    }
    public int getSafetyNumber() {
        return safetyNumber;
    }
    public void setNumber(long number) {
        this.number = number;
    }
    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }
    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }
    public void setSafetyNumber(int safetyNumber) {
        this.safetyNumber = safetyNumber;
    }
}
