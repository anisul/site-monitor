package util;

public class AppUtil {
    private static final String HTTP_SECURE_PROTOCOL = "https://";
    private static final String HTTP_PROTOCOL = "http://";
    public static final int MAX_HISTORICAL_DATA_INTERVAL = 60 * 60;
    public static final int ALERT_CHECK_INTERVAL_IN_MINUTE = 2 * 60;
    public static final int SECOND_MONITOR_INTERVAL = 10 * 1000;
    public static final int SECOND_MONITOR_DELAY = 10 * 1000;
    public static final int MINUTE_MONITOR_INTERVAL = 1 * 60 * 1000;
    public static final int MINUTE_MONITOR_DELAY = 1 * 60 * 1000;
    public static final int ALERT_CHECK_INTERVAL = 2 * 60 * 1000;
    public static final int ALERT_CHECK_DELAY = 2 * 60 * 1000;

    public static String buildWebsiteAddress(String input) {
        String output = "";
        if (input.toLowerCase().contains(HTTP_SECURE_PROTOCOL)) {
            output = input.replaceAll(HTTP_SECURE_PROTOCOL, HTTP_PROTOCOL);
            return output;
        } else if (!input.toLowerCase().contains(HTTP_PROTOCOL)) {
            StringBuilder sb = new StringBuilder(input);
            sb.insert(0, HTTP_PROTOCOL);
            return sb.toString();
        } else {
            return input;
        }
    }
}
