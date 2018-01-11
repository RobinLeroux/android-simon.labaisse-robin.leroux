package be.henallux.iesn.namurbynight.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiDAO {
    public String get(String apiLink) throws IOException {
        /*
        * The API isn't there yet, so the URL change and the authentication token isn't needed
        * Need two files because the fake api can't be more than 10ko
        * */
        //URL url = new URL("http://namurbynight.azurewebsites.net/api/" + apiLink);
        URL url;
        if (apiLink.contains("event")) {
            url = new URL("https://my-json-server.typicode.com/RobinLeroux/fakeJsonTestEvent/" + apiLink);
        } else {
            url = new URL("https://my-json-server.typicode.com/RobinLeroux/fakeJsonTest/" + apiLink);
        }
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("Content-Type", "application/json");
        //urlConnection.setRequestProperty("Authorization", "Bearer " + token);
        urlConnection.setDoInput(true);
        urlConnection.connect();

        BufferedReader br;
        if (urlConnection.getResponseCode() >= 200 && urlConnection.getResponseCode() <= 299) {
            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String stringJSON;
        String line;
        while((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        stringJSON = sb.toString();
        urlConnection.disconnect();

        return stringJSON;
    }
}
