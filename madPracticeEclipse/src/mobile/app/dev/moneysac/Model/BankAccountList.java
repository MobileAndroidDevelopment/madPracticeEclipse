package mobile.app.dev.moneysac.Model;

import java.util.Calendar;
import java.util.Date;

import mobile.app.dev.moneysac.Helpers.LocalPersistence;
import android.content.Context;

/**
 * Created by Kev1n on 06.10.13.
 */
public class BankAccountList {

    /**
     *
     * @param context the context
     * @param accountMonth the BankAccountMonth you want to persist
     */
    public static void addMonthToStorage(Context context, BankAccountMonth accountMonth){
        String fileName = getFileNameFromDate(accountMonth.getMonth());
        LocalPersistence.witeObjectToFile(context, accountMonth, fileName);
    }

    private static String getEditedMonth(int month) {
        //adding +1 because of zero-indexed months.
        //the parsing with a simpledateformatter is 1-based, so im adding +1 here.
        //additionally it reads better if the file is named 201310 in october (10th month).
        month+=1;
        if(month >= 0 && month < 10){
            return "0"+month;
        } else {
            return month+"";
        }
    }

    /**
     *
     * @param context the context
     * @param fileName the months milliseconds as string, get with simpledateformat
     * @return the BankAccountMonth
     */
    public static BankAccountMonth getMonthFromStorage(Context context, Date date){
        String fileName = getFileNameFromDate(date);
        return LocalPersistence.readObjectFromFile(context, fileName);
    }

    public static String getFileNameFromDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return (cal.get(Calendar.YEAR) + getEditedMonth(cal.get(Calendar.MONTH)));

    }
}
