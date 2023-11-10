import java.util.ArrayList;

abstract class Account {
    private String login;
    private String password;
    private String accountId;
    private final String name;
    private final String surname;
    private String phoneNumber;
    private final ArrayList<String> history;
    private static int historyCount = 0;
    private String typeAccount;
    private Credits credits;

    Account(String login, String password, String name, String surname, String accountID, String phoneNumber){
        this.login = login;
        this.password = password;
        this.accountId = accountID;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.history = new ArrayList<>();
        this.credits = null;
    }


    // getter and setter
    public double getCreditBalance() {
        return credits != null ? credits.getCredit() : 0;
    }
    public double getCreditInterestRate() {
        return credits != null ? credits.getInterestRate() : 0;
    }
    public int getCreditTermInMonths() {
        return credits != null ? credits.getCreditTermInMonths() : 0;
    }

    public String getHistoryLast() {
        try { return history.getLast(); }
        catch (Exception e){ return  "Nothing"; }
    }

    public String getHistoryAll() {
        try {
            String result = "";
            for (String str: history) { result += str; }
            return result;
        }
        catch (Exception e){ return "Nothing"; }
    }

    public void setHistory(String str){
        history.add(str);
        historyCount++;
    }

    public boolean hasCredit() {
        return credits == null;
    }
    public int getHistoryCount() { return historyCount; }
    public String getTypeAccount() { return typeAccount; }
    public void setTypeAccount(String typeAccount) { this.typeAccount = typeAccount; }
    public String getAccountId() { return accountId; }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getFullName() { return surname + " " + name; }


    // основные операции
    public abstract void withdraw(double amount);
    public abstract void deposit(double amount);


    // остальные методы
    public abstract String getFormatHistory(int method, double amount, String actingPerson);

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
