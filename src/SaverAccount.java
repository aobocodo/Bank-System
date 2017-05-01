
import java.util.*;

public class SaverAccount extends Account{

    public Date noticeDate;
    public double noticeAmount;
    
    public SaverAccount(){
    	
    }
    
    public SaverAccount(int accNo, Customer customer, int pin) {
    	super(accNo, customer, pin);
    	this.overDraftLimit = 0;
    	this.noticeNeeded = true;
    }

    public Date getNoticeDate(){
    	return this.noticeDate;
    }
    
    public double getNoticeAmount(){
    	return this.noticeAmount;
    }
    /**
     * 短信通知银行店员什么时候取多少钱，再由店员设置，这里直接设置，这个函数用不到
     * @param noticeDate
     * @param noticeAmount
     */
    public void setNotice(Date noticeDate, double noticeAmount) {
    	this.noticeDate = noticeDate;
    	this.noticeAmount = noticeAmount;
    }

}