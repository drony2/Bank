import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LogReg {
    private static final String ACCOUNTS_DATA_DIR = "AccountsData";
    private List<Account> accounts = new ArrayList<>();

    public void registerBankAccount(BankAccount account) {
        createAccountDirectory(account);
        accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Account login(String login, String password) {
        if (isAccountExists(login)){
            Account account = findLoginAccount(login);

            if (Objects.equals(account.getPassword(), password)){
                return account;
            }
        }

        return null;
    }

    private void createAccountDirectory(Account account) {
        String accountDirName = ACCOUNTS_DATA_DIR + File.separator + account.getFullName() + "_" + account.getAccountId() + "_" + account.getLogin();
        File accountDirectory = new File(accountDirName);
        if (!accountDirectory.exists()) {
            if (accountDirectory.mkdirs()) {
                saveAccountData(account);
            } else {
                throw new RuntimeException("Не удалось создать директорию аккаунта или уже существует");
            }
        }
    }

    public ArrayList<BankAccount> getBankAccounts() {
        ArrayList<BankAccount> bankAccounts = new ArrayList<>();
        for (Account account : accounts) {
            if (account instanceof BankAccount) {
                bankAccounts.add((BankAccount) account);
            }
        }
        return bankAccounts;
    }

    public String getNamesBankAccounts() {
        String businessAccounts = "";
        for (Account account : accounts) {
            if (account instanceof BankAccount) {
                businessAccounts += account.getFullName() + "(" + account.getLogin() + ")" + "\n";
            }
        }

        return businessAccounts;
    }

    public void saveAccountData(Account account) {
        String fileName = ACCOUNTS_DATA_DIR + File.separator + account.getFullName() + "_" + account.getAccountId() + "_" + account.getLogin() + File.separator + "account.txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("AccountId: " + account.getAccountId());
            writer.println("Login: " + account.getLogin());
            writer.println("Password: " + account.getPassword());
            writer.println("Full name: " + account.getFullName());
            writer.println("Phone Number: " + account.getPhoneNumber());

            if (account instanceof BankAccount) {
                saveBankAccountData((BankAccount) account, writer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBankAccountData(BankAccount bankAccount, PrintWriter writer) {
        writer.println("Bank Account Data:");
        writer.println("AccountId: " + bankAccount.getAccountId());
        writer.println("Login: " + bankAccount.getLogin());
        writer.println("Password: " + bankAccount.getPassword());
        writer.println("Full name: " + bankAccount.getFullName());
        writer.println("Phone Number: " + bankAccount.getPhoneNumber());
        writer.println("\nDebit Card Data:");
        writer.println("CardHolderName: " + bankAccount.getDebitCard().getCardHolderName());
        writer.println("Card Number: " + bankAccount.getDebitCard().getCardNumber());
        writer.println("Expiration Date: " + bankAccount.getDebitCard().getExpirationDate());
        writer.println("Balance: " + bankAccount.getDebitCard().getBalance());
        writer.println("Currency: " + bankAccount.getDebitCard().getCurrency());
    }
    public boolean isAccountExists(String login) {
        for (Account account : accounts) {
            if (account.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAccountExists(String fullName, String accountId, String login) {
        for (Account account : accounts) {
            if (account.getFullName().equals(fullName) && account.getAccountId().equals(accountId) && account.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public Account findLoginAccount(String login) {
        for (Account account : accounts) {
            if (account.getLogin().equals(login)) {
                return account;
            }
        }
        return null;
    }
}
