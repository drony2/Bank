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


    Account(String login, String password, String name, String surname, String accountID, String phoneNumber){
        this.login = login;
        this.password = password;
        this.accountId = accountID;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.history = new ArrayList<>();
    }

    public String getHistoryLast() {
        try { return history.getLast(); }
        catch (Exception e){ return  "Nothing"; }
    }


    public void setHistory(String str){
        history.add(str);
        historyCount++;
    }

    public int getHistoryCount() { return historyCount; }
    public String getTypeAccount() { return typeAccount; }
    public void setTypeAccount(String typeAccount) { this.typeAccount = typeAccount; }
    public String getAccountId() { return accountId; }

    public String getPhoneNumber() { return phoneNumber; }

    public String getName() { return name; }

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
