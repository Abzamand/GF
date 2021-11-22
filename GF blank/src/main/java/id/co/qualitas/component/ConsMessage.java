package id.co.qualitas.component;

public interface ConsMessage extends Constants{
	
	public static final String MESSAGE_SUCCESS = "SUCCESS";
	public static final String MESSAGE_FAILED = "FAILED";
	public static final String OVERBID = "your bid has been exceeded";
	public static final String ACCESS_DENIED = "access denied";
	public static final String FILL_EMPTY = "Please fill the empty field";
	public static final String DOUBLE_INVENTORY = " you have the same item in database catalog";
	public static final String DOUBLE_EMAIL = " email has been used";
	public static final String DOUBLE_NICKNAME = " nickname has been used";
	public static final String EMPTY_SESSION = "please add session";
	public static final String EMPTY_ITEM = "please add item";
	public static final String EMPTY_CUSTOMER = "please add customer";
	public static final String EMPTY_APPROVAL = "please add approval";
	public static final String ITEM_USED = " has been used";
	public static final String FAILED_OLD_DATA = "FAILED, please refresh your data";
	public static final String OPEN_BID_FIRST = "Please open bid your tender";
	public static final String DELETE_FAILED = " already running, delete failed";
	
	public static final String TENDER_CANCELED = "tender cancelled";
	public static final String USED_NO = "transaction number has been used";
	
	public static final String ERROR_COMMODITY = "FAILED, please make sure your data is correct";
	public static final String ERROR_WRONG_PASSWORD = "password does not match";
}