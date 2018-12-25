package core;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MonitorHelper {
    public double getResponseTime(String address) {
        double startTime = 0;
        double finishTime = 0;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();
            startTime = System.currentTimeMillis();

            String jsonResponse = inputStreamReader(connection.getInputStream());
            finishTime = System.currentTimeMillis();
        } catch (Exception e) {
            return 0;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return (finishTime - startTime);
    }

    public boolean isSiteAvailable(String address) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(address).openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (200 <= responseCode && responseCode <= 399);
        } catch (IOException exception) {
            return false;
        }
    }

    public int getResponseCode(String address) {
        int responseCode = 400;
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            responseCode = connection.getResponseCode();
            connection.disconnect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return responseCode;
    }

    private String inputStreamReader(InputStream in) {
        StringBuilder sb = null;
        try {
            InputStreamReader reader = new InputStreamReader(in);
            sb = new StringBuilder();
            int c = reader.read();
            while (c != -1) {
                sb.append((char) c);
                c = reader.read();
            }
            reader.close();
            return sb.toString();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return sb.toString();
    }
}
