package core;

import types.MonitorRequest;
import types.ResponseStat;
import types.Website;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SecondCron extends AbstractCron {
    List<Website> websiteList = new ArrayList<Website>();
    DecimalFormat df = new DecimalFormat("0");

    public void run() {
        websiteList = MonitorRequest.getInstance().getWebsiteList();

        for (Website website: websiteList) {
            int statElementCount = ((10 * 60) / website.getCheckIntervalInSecond());
            int unavailabilityCount = 0;
            int availabilityPercentage = 0;
            double maxResponseTime = 0;
            double totalResponseTime = 0;
            double averageResponseTime = 0;

            int count = 0;
            if (website.getResponseStatList().size() <= statElementCount) {
                for (ResponseStat rs: website.getResponseStatList()) {
                    if (rs.getResponseTime() > maxResponseTime) {
                        maxResponseTime = rs.getResponseTime();
                    }

                    if (!rs.isAvailable()) {
                        unavailabilityCount++;
                    }
                    totalResponseTime += rs.getResponseTime();
                    count++;
                }
                averageResponseTime = (totalResponseTime/count);
            } else {
                int listStartPosition = website.getResponseStatList().size() - statElementCount;
                int listEndPosition = website.getResponseStatList().size() - 1;

                List<ResponseStat> subList = website.getResponseStatList().subList(listStartPosition, listEndPosition);

                for (ResponseStat rs: subList) {
                    if (rs.getResponseTime() > maxResponseTime) {
                        maxResponseTime = rs.getResponseTime();
                    }

                    if (!rs.isAvailable()) {
                        unavailabilityCount++;
                    }
                    totalResponseTime += rs.getResponseTime();
                    count++;
                }
                averageResponseTime = (totalResponseTime/count);
            }

            availabilityPercentage = (100 - ((unavailabilityCount / count) * 100));

            System.out.println("[10sec Checker] Website:" + website.getAddress()
                    + ", Availability: " + availabilityPercentage
                    + "%, Max Response Time:" + df.format(maxResponseTime) + "ms, "
                    + " Avg. Response Time:" + df.format(averageResponseTime) + "ms");
        }
    }
}
