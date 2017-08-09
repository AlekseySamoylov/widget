package com.alekseysamoylov.widget.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Log messages with reading date
 */
public class LogList {

    private Date logMessagesDate;
    private List<String> logMessagesList = new ArrayList<>();

    public LogList(Date logMessagesDate, List<String> logMessagesList) {
        this.logMessagesDate = logMessagesDate;
        if (logMessagesList != null) {
            this.logMessagesList = logMessagesList;
        } else {
            this.logMessagesList = Collections.emptyList();
        }
    }

    public Date getLogMessagesDate() {
        return logMessagesDate;
    }

    public void setLogMessagesDate(Date logMessagesDate) {
        this.logMessagesDate = logMessagesDate;
    }

    public List<String> getLogMessagesList() {
        return logMessagesList;
    }

    public void setLogMessagesList(List<String> logMessagesList) {
        this.logMessagesList = logMessagesList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogList logList = (LogList) o;

        if (logMessagesDate != null ? !logMessagesDate.equals(logList.logMessagesDate) : logList.logMessagesDate != null)
            return false;
        return logMessagesList != null ? logMessagesList.equals(logList.logMessagesList) : logList.logMessagesList == null;
    }

    @Override
    public int hashCode() {
        int result = logMessagesDate != null ? logMessagesDate.hashCode() : 0;
        result = 31 * result + (logMessagesList != null ? logMessagesList.hashCode() : 0);
        return result;
    }
}
