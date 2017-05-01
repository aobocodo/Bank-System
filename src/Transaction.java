import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class Transaction {

    protected double amount;
    protected Calendar date;
    protected BankControl bankControl;
    
    public Transaction() {
    }
    
    public Transaction(double amount){
    	this.amount = amount;
    }

    
    public int locateAccount(int accNo){
      	
    	ArrayList<String> allAccNo = new ArrayList<String>();
    	//allAccNo=bankControl.allAccNo();
    	
    	try{
    		File myFile = new File("Customer Information.txt");
    		FileReader fileReader = new FileReader(myFile);
    		BufferedReader reader = new BufferedReader(fileReader);
    		String line = null;
    		int i=0;
    		while ((line=reader.readLine())!=null){
    			if((i%11)==0){     //TODO 这里每次都要改，要是能跟Account那里联系起来更好。
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
    	
    	int j=0;
    	boolean checkAccNo=false;
    	String temp = ""+accNo;
    	for(j=0;j<allAccNo.size();j++){
    		if(temp.equals(allAccNo.get(j))){
    			checkAccNo=true;
    			j=j*11;//j原来是第几个账号 *11后变为第几行
    			break;
    		}
    	}
    	/**
    	 * 防止没有匹配的账号，j=0的时刻
    	 */
    	if(j==0 && checkAccNo==false){
    		System.out.println("Error: No account number matches!");
    		return j=-1;
    	}
    	
    	return j;
    }
    
    public abstract void updateBalance(int j);
        
    /**
     * current time
     */
    public String curDate(){    	
    	date = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
    	SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String curDate = s.format(date.getTime());
    	return curDate;
    }
     
    public boolean checkIsSuspended(int accNo){
    	boolean isSuspended = true;
    	int j = this.locateAccount(accNo);	
		try{
			File myFile = new File("Customer Information.txt");
			FileReader fileReader = new FileReader(myFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = null;
			int i=0;
			while((line=reader.readLine())!=null){
				if(i==(j+8)){
					String[] splitStr = line.split(" ");
					isSuspended = Boolean.parseBoolean(splitStr[1]);
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
   	return isSuspended;
    }

    
}