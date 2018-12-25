package core;

import types.MonitorRequest;
import types.ResponseStat;
import types.Website;
import util.AppUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AlertCron extends AbstractCron {
    List<Website> websiteList = new ArrayList<>();
    List<Website> downWebsiteList = new ArrayList<>();

    public void run() {
        websiteList = MonitorRequest.getInstance().getWebsiteList();

        for (Website website: websiteList) {
            int availabilityPercentage = 0;
            int checkedResponseCount = 0;
            int unavailableCount = 0;
            int stateElementCount = (AppUtil.ALERT_CHECK_INTERVAL_IN_MINUTE / website.getCheckIntervalInSecond());

            if (website.getResponseStatList().size() <= stateElementCount) {
                for (ResponseStat rs: website.getResponseStatList()) {
                    checkedResponseCount++;
                    if (!rs.isAvailable()) {
                        unavailableCount++;
                    }
                }
            } else {
                int listStartPosition = website.getResponseStatList().size() - stateElementCount;
                int listEndPosition = website.getResponseStatList().size() - 1;

                List<ResponseStat> subList = website.getResponseStatList().subList(listStartPosition, listEndPosition);

                for (ResponseStat rs: subList) {
                    checkedResponseCount++;
                    if (!rs.isAvailable()) {
                        unavailableCount++;
                    }
                }
            }

            availabilityPercentage = (100 - ((unavailableCount/checkedResponseCount) * 100));

            if (availabilityPercentage < 80) {
                if (!isPresentInDownList(website)) {
                    downWebsiteList.add(website);
                }
                System.out.println("ALERT: Website " + website.getAddress()
                        + " is down. Availability: " + availabilityPercentage
                        + "%, Time: " + new Date(System.currentTimeMillis()));
            } else {
                Iterator<Website> iterator = downWebsiteList.iterator();
                while (iterator.hasNext()) {
                    if (website.getAddress().equals(iterator.next().getAddress())) {
                        System.out.println("Unavailability alert of Website: "
                                + website.getAddress() + " recovered. Time:" + new Date(System.currentTimeMillis()));
                        iterator.remove();
                        break;
                    }
                }
            }
        }
    }

    boolean isPresentInDownList(Website website) {
        for (Website w: downWebsiteList) {
            if (w.getAddress().equals(website.getAddress())) {
                return true;
            }
        }
        return false;
    }
}
