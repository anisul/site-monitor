package types;

import util.AppUtil;

import java.util.*;

public class MonitorRequest {
    private static MonitorRequest monitorRequest = new MonitorRequest();

    List<Website> websiteList = new ArrayList<Website>();
    Date dateRequested;

    private MonitorRequest(){
    }

    public void load(String input) {
        this.dateRequested = new Date(System.currentTimeMillis());

        if (input != null) {
            List<String> requestList = Arrays.asList(input.split(","));

            Timer t = new Timer();
            for (String request: requestList) {
                List<String> r = Arrays.asList(request.trim().split(" "));
                Website website = new Website(AppUtil.buildWebsiteAddress(r.get(0)), Integer.parseInt(r.get(1)));

                t.scheduleAtFixedRate(website, 0, Integer.parseInt(r.get(1)) * 1000);
                this.websiteList.add(website);
            }
        }
    }

    public static MonitorRequest getInstance() {
        return  monitorRequest;
    }

    public List<Website> getWebsiteList() {
        return websiteList;
    }

    public void setWebsiteList(List<Website> websiteList) {
        this.websiteList = websiteList;
    }

    public Date getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(Date dateRequested) {
        this.dateRequested = dateRequested;
    }

    @Override
    public String toString() {
        return "MonitorRequest{" +
                "websiteList=" + websiteList +
                ", dateRequested=" + dateRequested +
                '}';
    }
}
