

public class Customer {
  
    protected String name;
    protected String address;
    protected String dateOfBirth;
    protected boolean creditStatus;
     
    public Customer(String name, String address, String dateOfBirth) {
    	this.name = name;
    	this.address = address;
    	this.dateOfBirth = dateOfBirth;
    	creditStatus =  confirmCreditStatus();
    }

    public String getName(){
    	return this.name;
    }

    public String getAddress(){
    	return this.address;
    }
        
    public String getDateOfBirth(){
    	return this.dateOfBirth;
    }
    
    public boolean getCreditStatus(){
    	return this.creditStatus;
    }

	public void setName(String name){
    	this.name = name;
    }
    
    public boolean confirmCreditStatus() {
      	return true;
    }

    

}