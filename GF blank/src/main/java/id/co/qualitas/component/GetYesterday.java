package id.co.qualitas.component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetYesterday {
	
	public String getYesterday(int param) {
		Date date = Calendar.getInstance().getTime();
		Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, param);
        date = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String result = "'"+String.valueOf(sdf.format(date))+"'";
		return result;
	}
}
