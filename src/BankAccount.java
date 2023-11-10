import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public abstract class BankAccount extends Account implements Serializable {
    private static final String nameTypeAccount = "Bank Account";
    private static final LocalDateTime timeOpenAccount = LocalDateTime.now();
    private final DebitCard debitCard;

    BankAccount(String login, String password, String name, String surname, String accountID, String phoneNumber, double balance, String currency, ArrayList<BankAccount> listAccounts, String cardHolderName) throws Exception {
        super(login, password, name, surname, accountID, phoneNumber);
        this.debitCard = new DebitCard(cardHolderName, addNumberCard(listAccounts), String.format("%02d/%02d", timeOpenAccount.getMonthValue(), timeOpenAccount.getDayOfMonth()), balance, currency);
        setTypeAccount(nameTypeAccount);

        for (BankAccount account : listAccounts) {
            if (account.getLogin().equals(this.getLogin())) {
                throw new IllegalArgumentException("Такой логин уже есть!");
            }
        }
    }

    // getter and setter
    public static LocalDateTime getTimeOpenAccount(){
        return timeOpenAccount;
    }

    public DebitCard getDebitCard() {
        return debitCard;
    }

    public String getNumberCard() {
        return debitCard.getCardNumber();
    }

    private String addNumberCard(ArrayList<BankAccount> listAccounts){
        String number;
        int maxAttempts = 100;  // Максимальное количество попыток

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            number = generateNumberCard();
            boolean isUnique = true;

            for (BankAccount account : listAccounts) {
                if (account.getNumberCard().equals(number)) {
                    isUnique = false;
                    break;
                }
            }

            if (isUnique) {
                return number;
            }
        }

        throw new IllegalStateException("Не удалось найти уникальный номер для карты");
    }

    // основные операции

    public void deposit(double amount) {
        DebitCard card = getDebitCard();

        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма депозита должна быть больше 0!");
        }

        card.setBalance(card.getBalance() + amount);
        setHistory(getFormatHistory(0, amount, null));
    }

    public void transfer(double amount, BankAccount transferee) {
        DebitCard card = getDebitCard();
        DebitCard transfereeCard = transferee.getDebitCard();
        double difference = card.getCashWithdrawalLimit() - amount;

        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма перевода должна быть больше 0!");
        }

        if (card.getBalance() < amount) {
            throw new IllegalArgumentException("Не хватает средств!");
        }

        if (!card.getTransferEnabled()){
            throw new IllegalArgumentException("В этой карте отключена возможность переводов и покупок");
        }

        // Проверка на разрешение переводов для карты transfereeCard
        if (!transfereeCard.getTransferEnabled()) {
            throw new IllegalArgumentException("Карта получателя не разрешает переводы.");
        }

        if (difference > 0){
            if (this != transferee){
                card.setCashWithdrawalLimit(difference);

                transfereeCard.setBalance(amount + transfereeCard.getBalance());
                card.setBalance(card.getBalance() - amount);

                transferee.setHistory(getFormatHistory(0, amount, this.getFullName()));
                setHistory(getFormatHistory(1, amount, transferee.getFullName()));
            }
            else {
                card.setCashWithdrawalLimit(difference);

                transfereeCard.setBalance(amount + transfereeCard.getBalance());
                card.setBalance(card.getBalance() - amount);

                transferee.setHistory(getFormatHistory(0, amount, "Кому ты там переводишь?"));
                setHistory(getFormatHistory(1, amount, "Кому ты там переводишь?"));
            }
        }
        else {
            transfereeCard.setBalance(amount + transfereeCard.getBalance());
            card.setBalance(card.getBalance() - amount - (amount * 0.05));

            transferee.setHistory(getFormatHistory(0, amount, this.getFullName()));
            setHistory(getFormatHistory(1, amount, transferee.getFullName()));
        }
    }

    public String getFormatHistory(int method, double amount, String actingPerson){
        DebitCard card = getDebitCard();
        if (actingPerson != null){
            method += 2;
        }

        switch (method){
            case 0 -> { return "У вас +" + amount + card.getCurrency(); }
            case 1 -> { return "У вас -" + amount + card.getCurrency(); }
            case 2 -> { return "У вас +" + amount + card.getCurrency() + ", Отправитель: " + actingPerson; }
            case 3 -> { return "У вас -" + amount + card.getCurrency() + ", Получатель: " + actingPerson; }
            default -> { return "Ошибка, неверный метод"; }
        }
    }

    public String getAccountDetails(boolean extended) {
        DebitCard card = getDebitCard();
        String details = "AccountId: " + this.getAccountId() + "\n" +
                "Баланс: " + card.getBalance() + card.getCurrency() + "\n" +
                "Пользователь: " + this.getFullName() + "\n" +
                "Номер карты: " + this.getNumberCard() + "\n";

        if (extended) {
            details += card.getMainCardDetails() + "\n" +
                    "Номер телефона: " + this.getPhoneNumber() + "\n" +
                    "Тип аккаунта: " + getTypeAccount() + "\n" +
                    "Последняя операция: " + getHistoryLast() + "\n" +
                    "Время создания аккаунта: " + getTimeOpenAccount() + "\n";
        }

        return details;
    }

    // остальные методы
    private String generateNumberCard() {
        String result = "2806";

        while(result.length() < 16) {
            int randomNum = ThreadLocalRandom.current().nextInt(10);
            result += randomNum;
        }

        return result;
    }
}
