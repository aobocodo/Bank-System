
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BankControl {
	private ArrayList<Account> account = new ArrayList<Account>();

    public BankControl() {
    }

    public void addCustomer(Account a) {
      account.add(a);
      try{
          BufferedWriter addCustomer = new BufferedWriter(new FileWriter("Customer Information.txt",true));//接着写
          addCustomer.write(a.toString());
          addCustomer.close();
      }
      catch (IOException e1){
    	  e1.printStackTrace();
      }     
    }

    public boolean confirmCreditStatus(Customer customer) {
    	boolean confirm = customer.getCreditStatus();
    	if(confirm==true){
    		System.out.println("This credit status has been confirmed as good.");
    		return true;
    	}
    	else
    		System.out.println("This credit status has been confirmed as bad.");
    		return false;
    }

    public int deposit(int accNo,double amount) {
    	int success=0; 
    	/**
    	 * Get all account numbers
    	 */
    	ArrayList<String> allAccNo = new ArrayList<String>();
    	allAccNo = this.allAccNo();
    	/**
    	 * Compare input to all the account numbers
    	 */
    	boolean checkAccNo = this.checkAccNo(accNo, allAccNo);
    	
    	
    	/**
    	 * set cleared and record deposit
    	 */
    	if(checkAccNo==true){
    		Deposit d = new Deposit(amount);
    		if(d.checkIsSuspended(accNo)==true){
    			success = 0;
    		}else{
	    		d.setCleared(true);
	    		d.check(accNo);
	    		success = 1;
    		}
    	}else{
    		success = 0;
    		System.out.println("Error: No account number matches!");
    	}
    	
    	return success;
    }
	    
    /**
     * An external bank clearing system periodically clears uncleared funds.
     * Once cleared, they are immediately credited to the account.
     * @param a
     * @return
     */		
    public boolean clearFunds(Account a) {
    	return a.clearFunds();
    }

    /*
    public void giveNotice(Account a) {
        // TODO implement here
    }
*/
    
    public int withdral(int accNo,int pin,double amount) {
	   	int correctPin = 0;	  
	   	Withdraw w = new Withdraw(amount);
	    /**
	   	 * Get all account numbers
	   	 */
	   	ArrayList<String> allAccNo = new ArrayList<String>();
	   	allAccNo = this.allAccNo();
	   	
	   	/**
	   	 * Compare input to all the account numbers
	   	 */
	   	boolean checkAccNo = this.checkAccNo(accNo, allAccNo);
	   	
	   	
	   
	   	/**
	   	 * find correct pin
	   	 */
	   	if(checkAccNo==true){
	   		if(w.checkIsSuspended(accNo)==true){
	   			System.out.println("Withdraw " + amount+ " Unsuccessfully. This account is suspended.");
    			return 0;
    		}else{
		   		int j = w.locateAccount(accNo);	
				try{
					File myFile = new File("Customer Information.txt");
					FileReader fileReader = new FileReader(myFile);
					BufferedReader reader = new BufferedReader(fileReader);
					String line = null;
					int i=0;
					while((line=reader.readLine())!=null){
						if(i==(j+1)){
							String[] splitStr = line.split(" ");
							correctPin = Integer.parseInt(splitStr[1]);
							break;
						}
						else{					
							i++;
						}				
					}
					reader.close();
				}
				catch(IOException e1){
					e1.printStackTrace();
				}
    		}
	   	}else{
			System.out.println("Invalid account number or pin.");
	   		return 0;
	   	}
	   	
	   	/**
	   	 *  check pin
	   	 */
	   	if(pin==correctPin){
	   		w.check(accNo);
	   		return 1;
	   	}else{
	   		return 0;
	   	}
    }

    public int suspend(int accNo) {
    	int success = 0;
    	String temp = ""+accNo;
    	int j;

    	/**
    	 * Get all account numbers
    	 */
    	ArrayList<String> allAccNo = new ArrayList<String>();
    	allAccNo = this.allAccNo();
    	/**
    	 * Compare input to all the account numbers
    	 */
    	boolean checkAccNo = this.checkAccNo(accNo, allAccNo);

    	/**
    	 * find account and set suspend.
    	 */
    	if(checkAccNo==true){
    		/**
    		 * account at j
    		 */
        	for(j=0;j<allAccNo.size();j++){
        		if(temp.equals(allAccNo.get(j))){
        			checkAccNo=true;
        			j=j*11;//j原来是第几个账号 *11后变为第几行
        			break;
        		}
        	}
        	/**
        	 * isSuspended at j+8
        	 */
    		ArrayList<String> temp2 = new ArrayList<String>();
    		try{
    			File myFile = new File("Customer Information.txt");
    			FileReader fileReader = new FileReader(myFile);
    			BufferedReader reader = new BufferedReader(fileReader);
    			String line = "";
    			int i=0;
    			while((line=reader.readLine())!=null){
    				if(i==(j+8)){
    					String[] splitStr = line.split(" ");
    					line=splitStr[0]+" "+true;
    					temp2.add(line);
    					i++;
    				}
    				else{
    					temp2.add(line);
    					i++;
    				}
    				
    			}
    			reader.close();
    		}
    		catch(IOException e1){
    			e1.printStackTrace();
    		}
    		
    		try{
    			BufferedWriter writer = new BufferedWriter(new FileWriter("Customer Information.txt",false));//重写
    			for(int x=0;x<temp2.size();x++){
    				writer.write(temp2.get(x));
    				writer.newLine();
    			}
    			writer.close();
    		}
    		catch (IOException e1){
    			e1.printStackTrace();
    		}		
    		success = 1;
    	}else{
    		success = 0;
    		System.out.println("Error: No account number matches!");
    	}
    	
    	return success;
    }

    public int reinstate(int accNo) {
    	int success = 0;
    	String temp = ""+accNo;
    	int j;

    	/**
    	 * Get all account numbers
    	 */
    	ArrayList<String> allAccNo = new ArrayList<String>();
    	allAccNo = this.allAccNo();
    	/**
    	 * Compare input to all the account numbers
    	 */
    	boolean checkAccNo = this.checkAccNo(accNo, allAccNo);

    	/**
    	 * find account and set suspend.
    	 */
    	if(checkAccNo==true){
    		/**
    		 * account at j
    		 */
        	for(j=0;j<allAccNo.size();j++){
        		if(temp.equals(allAccNo.get(j))){
        			checkAccNo=true;
        			j=j*11;//j原来是第几个账号 *11后变为第几行
        			break;
        		}
        	}
        	/**
        	 * isSuspended at j+8
        	 */
    		ArrayList<String> temp2 = new ArrayList<String>();
    		try{
    			File myFile = new File("Customer Information.txt");
    			FileReader fileReader = new FileReader(myFile);
    			BufferedReader reader = new BufferedReader(fileReader);
    			String line = "";
    			int i=0;
    			while((line=reader.readLine())!=null){
    				if(i==(j+8)){
    					String[] splitStr = line.split(" ");
    					line=splitStr[0]+" "+false;
    					temp2.add(line);
    					i++;
    				}
    				else{
    					temp2.add(line);
    					i++;
    				}
    				
    			}
    			reader.close();
    		}
    		catch(IOException e1){
    			e1.printStackTrace();
    		}
    		
    		try{
    			BufferedWriter writer = new BufferedWriter(new FileWriter("Customer Information.txt",false));//重写
    			for(int x=0;x<temp2.size();x++){
    				writer.write(temp2.get(x));
    				writer.newLine();
    			}
    			writer.close();
    		}
    		catch (IOException e1){
    			e1.printStackTrace();
    		}			
    		success = 1;
    	}else{
    		success = 0;
    		System.out.println("Error: No account number matches!");
    	}
    	
    	return success;
      }

    public int closeAccount(int accNo) {
    	int success = 0;
    	String temp = ""+accNo;
    	int j;

    	/**
    	 * Get all account numbers
    	 */
    	ArrayList<String> allAccNo = new ArrayList<String>();
    	allAccNo = this.allAccNo();
    	/**
    	 * Compare input to all the account numbers
    	 */
    	boolean checkAccNo = this.checkAccNo(accNo, allAccNo);

    	/**
    	 * find account and set suspend.
    	 */
    	if(checkAccNo==true){
    		/**
    		 * account at j
    		 */
        	for(j=0;j<allAccNo.size();j++){
        		if(temp.equals(allAccNo.get(j))){
        			checkAccNo=true;
        			j=j*11;//j原来是第几个账号 *11后变为第几行
        			break;
        		}
        	}
        	/**
        	 * j 到j+10 都为空
        	 */
    		ArrayList<String> temp2 = new ArrayList<String>();
    		try{
    			File myFile = new File("Customer Information.txt");
    			FileReader fileReader = new FileReader(myFile);
    			BufferedReader reader = new BufferedReader(fileReader);
    			String line = "";
    			int i=0;
    			while((line=reader.readLine())!=null){
    				if(i==j||i==j+1||i==j+2||i==j+3||i==j+4||i==j+5||i==j+6||i==j+7||i==j+8||i==j+9||i==j+10){				
    					line="";
    					i++;
    				}
    				else{
    					temp2.add(line);
    					i++;
    				}
    				
    			}
    			reader.close();
    		}
    		catch(IOException e1){
    			e1.printStackTrace();
    		}
    		
    		try{
    			BufferedWriter writer = new BufferedWriter(new FileWriter("Customer Information.txt",false));//重写
    			for(int x=0;x<temp2.size();x++){
    				writer.write(temp2.get(x));
    				writer.newLine();
    			}
    			writer.close();
    		}
    		catch (IOException e1){
    			e1.printStackTrace();
    		}				
    		success = 1;
    	}else{
    		success = 0;
    		System.out.println("Error: No account number matches!");
    	}
    	
    	return success;

    }
    
    /**
	 * Get all account numbers
	 */   
    public ArrayList<String> allAccNo(){
    	ArrayList<String> allAccNo = new ArrayList<String>();
    	try{
    		File myFile = new File("Customer Information.txt");
    		FileReader fileReader = new FileReader(myFile);
    		BufferedReader reader = new BufferedReader(fileReader);
    		String line = null;
    		int i=0;
    		while ((line=reader.readLine())!=null){
    			if(i%11==0){     //TODO 这里每次都要改，要是能跟Account那里联系起来更好。
    				String[] splitStr = line.split(" ");
    				allAccNo.add(splitStr[1]);			
    			}
    			i++;
    		}
    		reader.close();
    	}
    	catch(Exception ex){
    		ex.printStackTrace();
    	}
    	return allAccNo;
    }
    
    /**
	 * Compare input to all the account numbers
	 */
    public boolean checkAccNo(int accNo,ArrayList<String> allAccNo){
    	int j=0;
    	boolean checkAccNo=false;
    	String temp = ""+accNo;
    	while(j<allAccNo.size()){
    		if(temp.equals(allAccNo.get(j))){
    			checkAccNo=true;
    			break;
    		}
    		j++;
    	}
    	
    	return checkAccNo;
    }


}