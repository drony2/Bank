public class Credits {
    private double credit;
    private double interestRate;
    private int creditTermInMonths;

    public Credits(double credit, double interestRate, int creditTermInMonths) {
        if (credit <= 0 || interestRate <= 0 || creditTermInMonths <= 0) {
            throw new IllegalArgumentException("Значения кредита, процентной ставки и срока должны быть положительными.");
        }

        this.credit = credit;
        this.interestRate = interestRate;
        this.creditTermInMonths = creditTermInMonths;
    }

    public double getTotalInterest(){
        return (credit * interestRate * creditTermInMonths) / 12;
    }

    public double getTotalAmountToRepay() {
        return credit + getTotalInterest();
    }

    public double getMonthlyPayment() {
        return getTotalAmountToRepay() / creditTermInMonths;
    }

    public double getCredit() {
        return credit;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getCreditTermInMonths() {
        return creditTermInMonths;
    }

    public void setCredit(double credit) {
        if (credit <= 0) {
            throw new IllegalArgumentException("Значение кредита должно быть положительным.");
        }
        this.credit = credit;
    }

    public void setInterestRate(double interestRate) {
        if (interestRate <= 0) {
            throw new IllegalArgumentException("Значение процентной ставки должно быть положительным.");
        }
        this.interestRate = interestRate;
    }

    public void setCreditTermInMonths(int creditTermInMonths) {
        if (creditTermInMonths <= 0) {
            throw new IllegalArgumentException("Значение срока кредита должно быть положительным.");
        }
        this.creditTermInMonths = creditTermInMonths;
    }
}

