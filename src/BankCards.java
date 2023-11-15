public class BankCards {
    private String cardNumber;
    private String expirationDate;
    private String cvv;
    private double balance;
    private String currency;
    private String cardHolderName;

    public BankCards(String cardHolderName, String cardNumber, String expirationDate, double balance, String currency) throws Exception {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        setCvv(generateCvv());
        this.balance = balance;
        this.currency = currency;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) throws Exception {
        if (cvv.length() != 3){
            throw new Exception("сvv не больше 3 значений");
        }

        this.cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public double getBalance() {
        return balance;
    }


    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    private String generateCvv(){
        String cardNumber = getCardNumber();
        String expirationDate = getExpirationDate();

        char cvv1 = cardNumber.charAt(cardNumber.length() - 1);
        char cvv2 = expirationDate.charAt(0);
        char cvv3 = cardNumber.charAt(cardNumber.length() - 2);

        return String.valueOf(cvv1 + cvv2 + cvv3);
    }

    public String getMainCardDetails(){
        return "Баланс: " + getBalance() + getCurrency() + "\n" +
                "Номер карты: " + getCardNumber() + "\n" +
                "Срок действия: " + getExpirationDate() + "\n" +
                "CVV: " + getCvv() + "\n" +
                "Cardholder's Name: " + getCardHolderName();
    }
}

