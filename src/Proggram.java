import javax.lang.model.type.NullType;
import java.io.IOException;
import java.util.Scanner;
public class Proggram {
        private static final LogReg logReg = new LogReg();
        private static final Scanner scanner = new Scanner(System.in);
        private Account currentAccount;


        public boolean login_vis_run() throws Exception {
            while (true) {
                System.out.println("Введите login: ");
                String login = scanner.nextLine();
                Exit(login);
                if (!logReg.isAccountExists(login)) {
                    System.out.println("login не верен");
                    login_vis_run();
                }

                System.out.println("Введите пароль: ");
                String password = scanner.nextLine();
                Exit(password);

                Account account = logReg.login(login.trim().toLowerCase(), password.trim());
                if (account != null) {
                    currentAccount = account;
                    return true;
                } else {
                    System.out.println("Пользователь не найден");
                }
            }
        }

        public void registration_run(Account account, boolean isBusiness) throws IOException {
            if (!logReg.isAccountExists(account.getFullName(), account.getAccountId(), account.getLogin())) {
                logReg.registerBankAccount((BankAccount) account);

                currentAccount = account;
            }
            else {
                System.out.println("Такой аккаунт уже существует!");
            }
        }

        public void registrationBankAccount_vis() throws Exception {
            System.out.println("Введите login: ");
            String login = scanner.nextLine();
            login = Validation.NotNullString(login);
            Exit(login);


            System.out.println("Введите password: ");
            String password = scanner.nextLine();
            password = Validation.NotNullString(password);
            Exit(password);

            System.out.println("Введите имя: ");
            String name = scanner.nextLine();
            name=  Validation.NotNullString(name);
            Exit(name);

            System.out.println("Введите фамилию: ");
            String surname = scanner.nextLine();
            surname = Validation.NotNullString(surname);
            Exit(surname);

            String accountId = String.valueOf(logReg.getBankAccounts().size() + 1);

            System.out.println("Введите номер телефона(7XXXXXXXXXX): ");
            String phoneNumber = scanner.nextLine();
            phoneNumber = Validation.PhoneNumber(phoneNumber);
            Exit(phoneNumber);

            System.out.println("Введите имя, который будет на карте: ");
            String cardName = scanner.nextLine();
            cardName = Validation.NotNullString(cardName);
            Exit(cardName);

            BankAccount bankAccount = new BankAccount(login.toLowerCase().trim(), password, name.toLowerCase().trim(), surname.toLowerCase().trim(), accountId, phoneNumber, 0, " руб.", logReg.getBankAccounts(), cardName.toLowerCase().trim()) {
                @Override
                public void withdraw(double amount) {
                    DebitCard card = getDebitCard();

                    if (amount <= 0) {
                        throw new IllegalArgumentException("Сумма депозита должна быть больше 0!");
                    }

                    card.setBalance(card.getBalance() - amount);
                    setHistory(getFormatHistory(1, amount, null));
                }
            };
            System.out.println(bankAccount.getLogin());
            boolean isAccount = logReg.isAccountExists(bankAccount.getFullName(), bankAccount.getAccountId(), bankAccount.getLogin());

            if (isAccount) {
                System.out.println("Такой пользователь уже есть");
            } else {
                registration_run(bankAccount, false);
            }
        }

        public static void Exit(String string) throws Exception {
            String ex = "Exit";
            if (string.equals(ex)) {
                Proggram proggram = new Proggram();
                proggram.start();
            }
        }

        public void start() throws Exception {
            while (true) {
                System.out.println("Выберите действие:");
                System.out.println("{1} Зайти в имеющийся аккаунт");
                System.out.println("{2} Создать аккаунт");
                System.out.println("{3} Выйти");
                System.out.print("Ваша цифра: ");

                String answer = scanner.nextLine();
                scanner.nextLine();

                if (answer.trim().equalsIgnoreCase("exit")) {
                    System.out.println("До свидания!");
                    System.exit(0);
                }

                int choice = Integer.parseInt(answer);
                switch (choice) {
                    case 1:
                        if (login_vis_run()) {
                            mainFunction();
                        }
                        else {
                            start();
                        }
                        break;
                    case 2:
                        AddNewAccount();
                        break;
                    case 3:
                        System.out.println("До свидания!");
                        System.exit(0);
                    default:
                        System.out.println("Неверный выбор. Попробуйте еще раз.");
                }

            }
        }

        public void mainFunction() throws Exception {
            if (currentAccount == null) {
                System.out.println("Аккаунт не найден.");
                return;
            }

            while (true) {
                System.out.println("\nВыберите действие:");
                System.out.println("{1} Просмотреть баланс");
                System.out.println("{2} Внести деньги");
                System.out.println("{3} Снять деньги");
                System.out.println("{4} Перевести деньги");
                System.out.println("{5} Выйти");
                System.out.print("Ваш выбор: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        viewBalance();
                        break;
                    case 2:
                        makeDeposit();
                        break;
                    case 3:
                        makeWithdrawal();
                        break;
                    case 4:
                        makeTransfer();
                        break;
                    case 5:
                        System.out.println("Выход из аккаунта");
                        currentAccount = null;
                        return;
                    default:
                        System.out.println("Неверный выбор. Попробуйте еще раз.");
                }
            }
        }

        private void AddNewAccount() throws Exception {
            registrationBankAccount_vis();
        }

        private void viewBalance() {
            if (currentAccount != null) {
                BankAccount account = (BankAccount) getCurrentAccount();
                System.out.println("Баланс вашего аккаунта: " + account.getDebitCard().getBalance() + " " + account.getDebitCard().getCurrency() + "\n");
            }
        }

        private void makeDeposit() {
            System.out.println("Укажите размер депозита: ");
            double num = scanner.nextDouble();
            num = Validation.LimitMoney(num);
            getCurrentAccount().deposit(num);
            System.out.println(getCurrentAccount().getHistoryLast());
        }

        private void makeWithdrawal() {
            System.out.println("Укажите, сколько хотите снять: ");
            double num = scanner.nextDouble();
            num = Validation.LimitMoney(num);
            getCurrentAccount().withdraw(num);
            System.out.println(getCurrentAccount().getHistoryLast());
        }

        private void makeTransfer() throws Exception {
            System.out.println("Укажите, сколько хотите перевести: ");
            int amount = scanner.nextInt();
            amount = Validation.makeTransferValidation(amount);

            String login;
            while (true){
                System.out.println("Укажите, login того, кому хотите перевести: ");
                login = scanner.nextLine().toString();
                if (login != ""){
                    break;
                }

            }

            BankAccount transferee = (BankAccount) logReg.findLoginAccount(login);
            BankAccount account = (BankAccount) currentAccount;
            account.transfer((double)amount, transferee);
            System.out.println(account.getHistoryLast());

        }

        public Account getCurrentAccount() {
            return currentAccount;
        }
    }

