import java.util.Scanner;

public class BankUI {
		

    Scanner scan = new Scanner(System.in); 
    BankControl bankControl = new BankControl();
	public BankUI(){		
	}
		
    public static void main(String args[]){
        BankUI ui = new BankUI();    	
        ui.choice();
    }
    
    public int choice(){
    	int choice=0;		
        
    	System.out.println("-------------------------------------------------------------");
    	System.out.println("               Welcome to BUPT Banking System                ");
    	System.out.println("-------------------------------------------------------------");
    	System.out.println("Please type 1~5 to choose one:                               ");
    	System.out.println("                                                             ");
    	System.out.println("1 Open an account                                            ");
    	System.out.println("2 Deposite funds                                             ");
    	System.out.println("3 Withdraw funds                                             ");
    	System.out.println("4 Suspend account                                            ");
    	System.out.println("5 Close account                                              ");
    	System.out.println("6 Reinstate account                                          ");
    	System.out.println("                                                             ");
    	System.out.println("Your choice:                                                 ");

    	if(this.scan.hasNextInt())
    		choice=this.scan.nextInt();
        
    	if(choice==1){
    		this.openAccount();    		
    	}else if(choice==2){
    		this.depositFunds();
    	}else if(choice==3){
    		this.withdraw();
    	}else if(choice==4){
    		this.suspend();
    	}else if(choice==5){
    		this.closeAccount();
    	}else if(choice==6){
    		this.reinstate();
    	}else{
    		System.out.println("Invalid choice.");
    		this.choice();
    	}
    	this.scan.close();

    	return choice;
    }
    
	public void openAccount(){
	    String name = "";
	    String address = "";
	    String dateOfBirth = "";
	    String typeOfAccount = "";
	    Customer customer;
	    Account account;
	    String[] splitStr;
	    int age;
	    int pin;
	    int accNo;	   
	    boolean confirmCreditStatus;
		/**
		 * collect information
		 */
		System.out.println("Please provide the following information:");
		System.out.println("Name: ");
		if(scan.hasNext())
			name = scan.next();
		System.out.println("Address: ");
		if(scan.hasNext())
			address = scan.next();
		System.out.println("Date of birth(yyyy-MM-dd): ");
		if(scan.hasNext())
			dateOfBirth = scan.next();
		customer = new Customer(name,address,dateOfBirth);
		confirmCreditStatus = bankControl.confirmCreditStatus(customer);
		/**
		 * open an account
		 */
		if(confirmCreditStatus==true){
			System.out.println("Type of account to be opened(Junior/Saver/Current): ");
			if(scan.hasNext())
				typeOfAccount = scan.next();
			accNo = (int)((Math.random()*9+1)*100000);
			pin = (int)((Math.random()*9+1)*100000);
		    splitStr = dateOfBirth.split("-");
		    String year = splitStr[0];
			age = 2017-Integer.parseInt(year);
			if(typeOfAccount.equals("Current")){
				account = new CurrentAccount(accNo,customer,pin);
				bankControl.addCustomer(account);
				System.out.println("Your account number is: "+accNo);
				System.out.println("Your pin is: "+pin);
				System.out.println("Remember!");
				this.choice();
			}
			else if(typeOfAccount.equals("Saver")){
				account = new SaverAccount(accNo,customer,pin);
				bankControl.addCustomer(account);
				System.out.println("Your account number is: "+accNo);
				System.out.println("Your pin is: "+pin);
				System.out.println("Remember!");
				this.choice();
			}
			else if(typeOfAccount.equals("Junior")&&age<=16){
				account = new JuniorAccount(accNo,customer,pin);
				bankControl.addCustomer(account);
				System.out.println("Your account number is: "+accNo);
				System.out.println("Your pin is: "+pin);
				System.out.println("Remember!");
				this.choice();
			}else{
				System.out.println("Open account unsucessful.");
				System.out.println(name+" 1 "+address+" "+dateOfBirth+" "+typeOfAccount);
				System.out.println(" ");
				this.choice();
			}
		}
		else{
			System.out.println("Open account unsucessful.");
			this.choice();
		}	
    }
    
    private void depositFunds() {
    	int accNo = 0;
    	int success = 0;
    	double amount = 0.0;
    	System.out.println("Please provide your account number: ");
    	if(scan.hasNext()){
    		accNo = scan.nextInt();
    	}
    	System.out.println("Please provide the amount:");
    	if(scan.hasNext()){
    		amount = scan.nextDouble();
    	}
    	success = bankControl.deposit(accNo,amount); 	
    	if(success==1){
    		System.out.println("Deposit " + amount+ " successfully.");
    		this.choice();
    	}else{
    		System.out.println("Deposit " + amount+ " unsuccessfully.");
    		this.choice();
    	}
	}

	private void withdraw() {
		int accNo = 0;
		int pin = 0;
		int checkAccNoPin=0;
		double amount=0.0;
		System.out.println("Please provide your account number: ");
		if(scan.hasNext()){
			accNo = scan.nextInt();
		}
		System.out.println("Please provide your pin: ");
		if(scan.hasNext()){
			pin = scan.nextInt();	
		}
    	System.out.println("Please provide the amount:");
    	if(scan.hasNext()){
    		amount = scan.nextDouble();
    	}
		checkAccNoPin=bankControl.withdral(accNo, pin,amount);
		if(checkAccNoPin==0){
			this.choice();
		}	
	}

	private void suspend() {
    	int accNo = 0;
    	int success = 0;
    	System.out.println("Please provide your account number: ");
    	if(scan.hasNext()){
    		accNo = scan.nextInt();
    	}
    	success = bankControl.suspend(accNo); 
    	if(success==1){
    		System.out.println("Suspend account " + accNo+ " successfully.");
    		this.choice();
    	}else{
    		System.out.println("Suspend account " + accNo+ " unsuccessfully.");
    		this.choice();
    	}

	}

	private void closeAccount() {
    	int accNo = 0;
    	int success = 0;
    	System.out.println("Please provide your account number: ");
    	if(scan.hasNext()){
    		accNo = scan.nextInt();
    	}
    	success = bankControl.closeAccount(accNo); 
    	if(success==1){
    		System.out.println("Close account " + accNo+ " successfully.");
    		this.choice();
    	}else{
    		System.out.println("Close account " + accNo+ " unsuccessfully.");
    		this.choice();
    	}
		
	}
	
	private void reinstate(){
    	int accNo = 0;
    	int success = 0;
    	System.out.println("Please provide your account number: ");
    	if(scan.hasNext()){
    		accNo = scan.nextInt();
    	}
    	success = bankControl.reinstate(accNo); 
    	if(success==1){
    		System.out.println("Reinstate account " + accNo+ " successfully.");
    		this.choice();
    	}else{
    		System.out.println("Reinstate account " + accNo+ " unsuccessfully.");
    		this.choice();
    	}

	}
	

    
    
}