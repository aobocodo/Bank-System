import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author weiyiming
 * 需要：账号，pin，amount
 * 判断：overDraftLimit+balance 与 amount 的关系
 * 重写：重写Customer Information.txt 文件
 */

public class Withdraw extends Transaction{
	BankUI ui = new BankUI();
	
    public Withdraw() {
    }

    public Withdraw(double amount){
    	super(amount);
    }
    
    /**
     * 	Method1.得到overdraftlimit：读取Customer Information.txt 文件中的信息
     * 	Method2.得到balance：读取Customer Information.txt 文件中的信息
     * 比较：isOverDraft:overDraftLimit+balance 与 this.amount 的关系 
     * 	Method3.得到noticeneeded：读取customer Information.txt 文件中信息
     * 判断：noticeNeeded 是否为真
     *
     * isOverDraft为假且noticeNeeded为假则updateBalance 
     * isOverDraft为假且noticeNeeded为真则 
     * 	Method4.获取今日时间
     * 	Method5.今日时间与noticeDate比较，
     * 	Method6.amount与NoticeAmount比较
     * 比较成功则updateBalance
     * 否则withdraw失败
     *       @param accNo
     */
    public void check(int accNo){
    	if(this.checkIsSuspended(accNo)==false){  	
	    	double overDraftLimit = checkOverDraftLimit(accNo);
	    	double balance = checkBalance(accNo);
	    	
	    	boolean isOverDraft = true;
	    	if(overDraftLimit+balance>=this.amount){
	    		isOverDraft = false;
	    	}
	    	
	    	boolean isNoticeNeeded = checkNoticeNeeded(accNo);
	    	
	    	if(isOverDraft==false && isNoticeNeeded==false){
	    		this.recordWithdraw(accNo);
	    		System.out.println("Withdraw " + this.amount+ " successfully.");
	    		ui.choice();
	    	}
	    	else if(isOverDraft==false && isNoticeNeeded==true){  		
	    		boolean checkNoticeDate = checkNoticeDate(accNo);
	    		boolean checkNoticeAmount = checkNoticeAmount(accNo);
	    		if(checkNoticeDate==true&&checkNoticeAmount==true){
	    			this.recordWithdraw(accNo);
	        		System.out.println("Withdraw " + this.amount+ " successfully.");
	        		ui.choice();
	    		}
	    		else{
	    			System.out.println("Withdraw " + this.amount+ " Unsuccessfully. Notice date and amount do not match.");
	    			ui.choice();
	    		}
	    	}else{
	    		System.out.println("Withdraw " + this.amount+ " Unsuccessfully. Do not have enough available funds.");
	    		ui.choice();
	    	}
    	}else{
    		System.out.println("Withdraw " + this.amount+ " Unsuccessfully. This account is suspended.");
    	}
    }
    
    public void recordWithdraw(int accNo){
    	String curDate = this.curDate();        	
    	/**
    	 * RecordFile, File name is account number(one file for each account).
    	 */   	
    	try{
    		BufferedWriter writer = new BufferedWriter(new FileWriter(accNo+".txt",true));
    		writer.write(curDate + " Withdraw: " + this.amount);
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
		ArrayList<String> temp = new ArrayList<String>();
		int j = this.locateAccount(accNo);
		try{
			File myFile = new File("Customer Information.txt");
			FileReader fileReader = new FileReader(myFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = null;
			int i=0;
			while((line=reader.readLine())!=null){
				if(i==j+2){
					String[] splitStr = line.split(" ");
					double newBalance = Double.parseDouble(splitStr[1])-this.amount;
					line=splitStr[0]+" "+newBalance;
					temp.add(line);
					i++;
				}
				else{
					temp.add(line);
					i++;
				}
				
			}
			reader.close();
		}
		catch(IOException e1){
			e1.printStackTrace();
		}
		
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter("Customer Information.txt",false));
			for(int x=0;x<temp.size();x++){
				writer.write(temp.get(x));
				writer.newLine();
			}
			writer.close();
		}
		catch (IOException e1){
			e1.printStackTrace();
		}		
	}
   
    /**
     * Method1 overDraftLimit 在j+5
     * @return
     */
    public double checkOverDraftLimit(int accNo){
    	double overDraftLimit=0;
    	int j = this.locateAccount(accNo);	
		try{
			File myFile = new File("Customer Information.txt");
			FileReader fileReader = new FileReader(myFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = null;
			int i=0;
			while((line=reader.readLine())!=null){
				if(i==(j+5)){
					String[] splitStr = line.split(" ");
					overDraftLimit = Double.parseDouble(splitStr[1]);
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
   	return overDraftLimit;
    }
    
    /**
     * Method 2 balance 在j+2
     * @return
     */
    public double checkBalance(int accNo){
    	double balance=0;
    	int j = this.locateAccount(accNo);	
		try{
			File myFile = new File("Customer Information.txt");
			FileReader fileReader = new FileReader(myFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = null;
			int i=0;
			while((line=reader.readLine())!=null){
				if(i==(j+2)){
					String[] splitStr = line.split(" ");
					balance = Double.parseDouble(splitStr[1]);
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
    	return balance;
    }
    
    /**
     * Method 3 noticeNeeded 在j+6
     * @return
     */
    public boolean checkNoticeNeeded(int accNo){
    	boolean noticeNeeded=false;
    	int j = this.locateAccount(accNo);	
		try{
			File myFile = new File("Customer Information.txt");
			FileReader fileReader = new FileReader(myFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = null;
			int i=0;
			while((line=reader.readLine())!=null){
				if(i==(j+6)){
					String[] splitStr = line.split(" ");
					noticeNeeded = Boolean.parseBoolean(splitStr[1]);
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
    	return noticeNeeded;
    }
    
    /**
     * Method 5
     * @return
     */
    public boolean checkNoticeDate(int accNo){
    	boolean checkNoticeDate = false;
    	/**
    	 * get currDate
    	 */
    	String curDate = this.curDate();    	
    	String[] splitStr1 = curDate.split(" ");
    	curDate = splitStr1[0];
    	/**
    	 * get noticeDate
    	 */
    	int j = this.locateAccount(accNo);
    	String noticeDate="";
		try{
			File myFile = new File("Customer Information.txt");
			FileReader fileReader = new FileReader(myFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = null;
			int i=0;
			while((line=reader.readLine())!=null){
				if(i==(j+9)){
					String[] splitStr = line.split(" ");
					noticeDate = splitStr[1];
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
		/**
		 * compare
		 */
    	if(noticeDate.equals(curDate))
    		checkNoticeDate=true;
    	else
    		checkNoticeDate=false;
  
    	return checkNoticeDate;	
    }
    
    /**
     * Method 6
     * @return
     */
    public boolean checkNoticeAmount(int accNo){
    	boolean checkNoticeAmount = false;
    	/**
    	 * get noticeAmount
    	 */
    	int j = this.locateAccount(accNo);
    	double noticeAmount=0;
		try{
			File myFile = new File("Customer Information.txt");
			FileReader fileReader = new FileReader(myFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = null;
			int i=0;
			while((line=reader.readLine())!=null){
				if(i==(j+10)){
					String[] splitStr = line.split(" ");
					noticeAmount = Double.parseDouble(splitStr[1]);
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
		
		if(this.amount==noticeAmount)
			checkNoticeAmount = true;
		else
			checkNoticeAmount = false;
				
    	return checkNoticeAmount;
    }
   
}