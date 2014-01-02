package mobile.app.dev.moneysac.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Kev1n on 06.10.13.
 */
public class BankAccountMonth implements Serializable {

    private LinkedList<ListEntry> entries;
    private Date month;

    public BankAccountMonth(LinkedList<ListEntry> entries, Date month) {
        this.entries = entries;
        this.month = month;
    }

    public BankAccountMonth(Date month){
        this.month = month;
        this.entries = new LinkedList<ListEntry>();
    }

    public LinkedList<ListEntry> getEntries() {
        return entries;
    }

    public void setEntries(LinkedList<ListEntry> entries) {
        this.entries = entries;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }
}
