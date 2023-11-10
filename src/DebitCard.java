import java.awt.datatransfer.Transferable;
import java.io.Serializable;

public class DebitCard extends BankCards implements Serializable {
    private double cashWithdrawalLimit;
    private boolean transferEnabled;

    public DebitCard(String cardHolderName, String cardNumber, String expirationDate, double balance, String currency) throws Exception {
        super(cardHolderName, cardNumber, expirationDate, balance, currency);
        this.cashWithdrawalLimit = 100000;
        this.transferEnabled = true;
    }


    // getter and setter
    public double getCashWithdrawalLimit() {
        return cashWithdrawalLimit;
    }

    public void setCashWithdrawalLimit(double limit) {
        if (limit > 0){
            this.cashWithdrawalLimit = limit;
        } else {
            this.cashWithdrawalLimit = 0;
        }
    }

    public boolean getTransferEnabled(){
        return transferEnabled;
    }

    public void setTransferEnabled(boolean transferEnabled) {
        this.transferEnabled = transferEnabled;
    }
}