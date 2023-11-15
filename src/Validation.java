import java.util.Scanner;

public class Validation {
    private static final Scanner scanner = new Scanner(System.in);;

    public static String NotNullString(String str){
        if (str.isEmpty())
        {
            while (true)
            {
                System.out.println("Введите строку повторно: ");
                String string = scanner.nextLine();
                if (!string.isBlank()){
                    return string;
                }

            }
        }
        return str;
    }

    public static double LimitMoney(double num){
        if (num <=0 || num >= 20000000)
        {
            while (true)
            {
                System.out.println("Введите значение повторно: ");
                int number = scanner.nextInt();
                if (number != 0){
                    return number;
                }
            }

        }

        return num;
    }

    public static String PhoneNumber(String number) throws Exception {
        final String PhoneNum;
        PhoneNum = "7\\d{10}";

        if (!number.matches(PhoneNum)) {
            while (true)
            {
                System.out.println("Неверный номер телефона, попробуйте заново");
                String string = scanner.nextLine();
                if (!number.matches(PhoneNum)) {
                    return string;
                }

            }
        }
        return number;
    }

    public static int makeTransferValidation(int num) throws Exception{
        if (num <=0 || num >= 20000000)
        {
            while (true)
            {
                System.out.println("Введите значение повторно: ");
                int number = scanner.nextInt();
                if (number != 0){
                    return number;
                }
            }

        }
        return num;
    }

}
