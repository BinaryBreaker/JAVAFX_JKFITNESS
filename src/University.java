import MyLayout.Model.Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class University {

    private static HttpURLConnection connection;
    static String line;
    static StringBuffer responsecontent = new StringBuffer();
    static BufferedReader reader;

    public static void main(String[] args) {
        Client client = new Client();
        client.setEMAIL("muzamil@asdsad.com");
        client.setDOB("2001-8-11");

    }

    public static void GetData() {
        try {
            URL url = new URL("http://127.0.0.1:8000/api/");
            connection = (HttpURLConnection) url.openConnection();
            String encoded = Base64.getEncoder().encodeToString(("ghori" + ":" + "muzamil@1182001").getBytes(StandardCharsets.UTF_8));  //Java 8
            connection.setRequestProperty("Authorization", "Basic " + encoded);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            System.out.println(status);

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responsecontent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responsecontent.append(line);
                }
                reader.close();
            }
            System.out.println(responsecontent.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

    }

    public static void PostData() {
        String charset = "UTF-8";
        String requestURL = "http://127.0.0.1:8000/api/";

        MultipartUtility multipart = null;
        try {
            multipart = new MultipartUtility(requestURL, charset);
            multipart.addFormField("Name", "Ali");
            multipart.addFormField("CNIC", "38200129458093");
            multipart.addFormField("Gender", "Male");
            multipart.addFormField("Father_Name", "Amjad Hussain");
            multipart.addFormField("Profession", "Student");
            multipart.addFormField("Address", "Ghori Town Phase 4B Gali 18 House 814 Islamabad");
            multipart.addFormField("DOB", "2001-08-11");
            multipart.addFormField("EMAIL", "muzamilhussa54r@gmail.com");
            multipart.addFormField("PHONE_NO", "3820129458093");
            multipart.addFormField("Emg_Contact", "03005274991");
            multipart.addFormField("Emg_Name", "Amjad Hussain");
            multipart.addFormField("Status", "Pending");
            multipart.addFormField("Diseases", "Diseases");
            multipart.addFormField("Finger_template", "asdasdbahsdhasbdhasbdasbdhbashdbashbdjasbdhjsabdsabdhjsabdhja");
            multipart.addFilePart("ID_PIC", new File("C:\\Users\\MMH\\Desktop\\JAVAFX\\Dp.jpeg"));
            multipart.addFilePart("DP", new File("C:\\Users\\MMH\\Desktop\\JAVAFX\\Dp.jpeg"));
            String response = multipart.finish(); // response from server
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

