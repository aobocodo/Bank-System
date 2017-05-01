
public class CurrentAccount extends Account{
	
    public CurrentAccount(int accNo, Customer customer, int pin) {
    	super(accNo, customer, pin);
    	this.overDraftLimit = 500;
    	this.noticeNeeded = false;
    }
   
    public CurrentAccount(int accNo, Customer customer, int pin, double overDraftLimit) {
    	super(accNo, customer, pin);
    	this.setOverdraftLimit(overDraftLimit);
    	this.noticeNeeded = false;
    }
  
    public double getOverdraftLimit() {
		return overDraftLimit;
	}

	public void setOverdraftLimit(double overDraftLimit) {
		this.overDraftLimit = overDraftLimit;
	}
			
}