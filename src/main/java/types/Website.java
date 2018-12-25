package types;

import core.AbstractCron;
import core.MonitorHelper;
import util.AppUtil;

import java.util.ArrayList;
import java.util.List;

public class Website extends AbstractCron {
    String address;
    int checkIntervalInSecond;
    int maxResponseStatLimit;
    List<ResponseStat> responseStatList = new ArrayList<ResponseStat>();

    MonitorHelper monitorHelper = new MonitorHelper();

    public void run() {
        ResponseStat rs = new ResponseStat(monitorHelper.isSiteAvailable(address),
                monitorHelper.getResponseTime(this.address));
        this.responseStatList.add(rs);

        if (this.responseStatList.size() == maxResponseStatLimit) {
            responseStatList.remove(0);
        }
    }

    public Website(String address, int checkIntervalInSecond) {
        this.address = address;
        this.checkIntervalInSecond = checkIntervalInSecond;
        this.maxResponseStatLimit = (AppUtil.MAX_HISTORICAL_DATA_INTERVAL / this.checkIntervalInSecond);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCheckIntervalInSecond() {
        return checkIntervalInSecond;
    }

    public void setCheckIntervalInSecond(int checkIntervalInSecond) {
        this.checkIntervalInSecond = checkIntervalInSecond;
    }

    public List<ResponseStat> getResponseStatList() {
        return responseStatList;
    }

    public void setResponseStatList(List<ResponseStat> responseStatList) {
        this.responseStatList = responseStatList;
    }

    public int getMaxResponseStatLimit() {
        return maxResponseStatLimit;
    }

    public void setMaxResponseStatLimit(int maxResponseStatLimit) {
        this.maxResponseStatLimit = maxResponseStatLimit;
    }
}
