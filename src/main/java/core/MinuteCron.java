package core;

import types.MonitorRequest;
import types.ResponseStat;
import types.Website;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MinuteCron extends AbstractCron {
    List<Website> websiteList = new ArrayList<Website>();
    DecimalFormat df = new DecimalFormat("0");

    public void run() {
        websiteList = MonitorRequest.getInstance().getWebsiteList();

        for (Website website: websiteList) {
            double averageResponseTime = 0;
            double maxResponseTime = 0;
            double totalResponseTime = 0;
            int count = 0;
            int unavailabilityCount = 0;
            int availabilityPercentage = 0;

            for (ResponseStat rs: website.getResponseStatList()) {
                if (!rs.isAvailable()) {
                    unavailabilityCount++;
                }

                if (rs.getResponseTime() > maxResponseTime) {
                    maxResponseTime = rs.getResponseTime();
                }

                totalResponseTime += rs.getResponseTime();
                count++;
            }
            averageResponseTime = totalResponseTime/count;
            availabilityPercentage = (100 - ((unavailabilityCount / count) * 100));

            System.out.println("[1min Checker] Website:" + website.getAddress()
                    + ", Availability: " + availabilityPercentage
                    + "%, Max Response Time:" + df.format(maxResponseTime) + "ms, "
                    + " Avg. Response Time:" + df.format(averageResponseTime) + "ms");
        }
    }
}
