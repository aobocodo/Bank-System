public class Account {
	   
    protected int accNo;
    protected int pin;    
    protected Customer customer;
    protected double balance;
    protected double overDraftLimit;
    protected boolean isActive;
    protected boolean isSuspended;
    protected boolean noticeNeeded;
    protected Deposit deposit;
    protected Withdraw withdraw;
    
   
    public Account() {
    }
    
    public Account(int accNo, Customer customer, int pin) {
		this.accNo = accNo;
		this.customer = customer;
		this.balance = 0.0;
		this.isActive = true;
		this.pin = pin;
    }

	public int getAccNo() {
		return this.accNo;
	}
	
	public int getPin(){
		return this.pin;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public double getOverDraftLimit(){
		return this.overDraftLimit;
	}
		
	public boolean isActive(){
		return this.isActive;
	}
	
	public boolean isSuspended(){
		return this.isSuspended;
	}
	
	public boolean noticeNeeded(){
		return this.noticeNeeded;
	}
   
    public boolean clearFunds() {
    	return true;
    }
    
    public void setSuspended(boolean setSuspended) {
    	this.isSuspended = setSuspended;
    }

    public void close() {
    	this.isActive = false;
    }
    
    public String toString(){
    	// 11 lines for each customer
    	return  "AccNo: " + this.getAccNo() + "\n"						//j
    		   +"PIN: " + this.getPin() + "\n"							//j+1
    		   +"Balance: " + this.getBalance() + "\n"					//j+2
    		   +"Name: " + customer.getName() + "\n"					//j+3
    		   +"DateOfBirth: "+ customer.getDateOfBirth() +"\n"		//j+4
    		   +"OverDraftLimit: "+ this.getOverDraftLimit() + "\n"		//j+5
    		   +"NoticeNeeded: " + this.noticeNeeded() + "\n"			//j+6
    		   +"IsActive: " + this.isActive() + "\n"					//j+7
    		   +"IsSuspended: " + this.isSuspended() +"\n"				//j+8
    		   +"NoticeDate: "+ "2017-07-05" +"\n"						//j+9
    		   +"NoticeAmount: "+"520.0" +"\n"							//j+10
    		   															
    			;
    }
    
}