import core.AlertCron;
import core.MinuteCron;
import core.SecondCron;
import types.MonitorRequest;
import util.AppUtil;

import java.util.Scanner;
import java.util.Timer;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        System.out.println("Enter Website and check-interval pair/s: <address> <interval>, ..., ..., ...");
        String input = reader.nextLine();

        MonitorRequest monitorRequest = MonitorRequest.getInstance();
        monitorRequest.load(input);

        System.out.println("\nMonitoring website/s: (Every after 10sec and 1min. Alert after 2min if Website is unavailable)\n");

        Timer timer = new Timer();

        SecondCron sc = new SecondCron();
        MinuteCron mc = new MinuteCron();
        AlertCron ac = new AlertCron();

        timer.scheduleAtFixedRate(sc, AppUtil.SECOND_MONITOR_DELAY, AppUtil.SECOND_MONITOR_INTERVAL);
        timer.scheduleAtFixedRate(mc, AppUtil.MINUTE_MONITOR_DELAY, AppUtil.MINUTE_MONITOR_INTERVAL);
        timer.scheduleAtFixedRate(ac, AppUtil.ALERT_CHECK_DELAY, AppUtil.ALERT_CHECK_INTERVAL);

        reader.close();
    }
}
