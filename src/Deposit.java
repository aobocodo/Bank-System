import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Deposit extends Transaction {
    
	public boolean cleared;

    public Deposit() {
    }
    
    public Deposit(double amount){
    	super(amount);
    }   
   
    public void setCleared(boolean cleared){
    	this.cleared = cleared;
    }
    
    public boolean getCleared(){
    	return this.cleared;
    }
    
    public void check(int accNo){
    	if(this.getCleared()==true&&this.checkIsSuspended(accNo)==false)
    		this.recordDeposit(accNo);
    	else if(this.getCleared()==true&&this.checkIsSuspended(accNo)==true)
    		System.out.println("Deposit " + this.amount+ " Unsuccessfully. This account is suspended.");
    	else
    		System.out.println("Funds are uncleared and will be immediately credited to the account once cleared.");   	
    }

    public void recordDeposit(int accNo){
    	String curDate = this.curDate();        	
    	/**
    	 * RecordFile, File name is account number(one file for each account).
    	 */   	
    	try{
    		BufferedWriter writer = new BufferedWriter(new FileWriter(accNo+".txt",true));
    		writer.write(curDate + " Deposit: " + this.amount);
    		writer.newLine();
    		writer.close();
    	}
    	catch(IOException e1){
    		e1.printStackTrace();
    	}
    	
    	this.updateBalance(accNo);
    }
   
	@Override
	public void updateBalance(int accNo) {
		/**
		 * 账号在j行
		 */
    	
		int j = this.locateAccount(accNo);


		/**
		 * 修改j+2行的balance
		 */
		ArrayList<String> temp2 = new ArrayList<String>();
		try{
			File myFile = new File("Customer Information.txt");
			FileReader fileReader = new FileReader(myFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = "";
			int i=0;
			while((line=reader.readLine())!=null){
				if(i==(j+2)){
					String[] splitStr = line.split(" ");
					double newBalance = Double.parseDouble(splitStr[1])+this.amount;
					line=splitStr[0]+" "+newBalance;
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
	}
    
}