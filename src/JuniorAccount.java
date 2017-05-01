
public class JuniorAccount extends Account {

	 public JuniorAccount(int accNo, Customer customer, int pin) {
	    	super(accNo, customer, pin);
	    	this.overDraftLimit = 0;
	    	this.noticeNeeded = false;
	    }
	 
}