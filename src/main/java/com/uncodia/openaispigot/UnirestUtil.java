package com.uncodia.openaispigot;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;
import org.bukkit.ChatColor;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class UnirestUtil {
    public static String openAICompletions(String prompt) {
        String apiUrl = "https://api.openai.com/v1/completions";
        String apiKey = PropertiesFileReader.readOpenAIKey();

        String requestBody = "{\"model\":\"text-davinci-003\"" +
                ",\"prompt\":\""+ prompt +"\"" +
                ",\"max_tokens\":256,\"temperature\":0.9}";
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer "+apiKey);
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(requestBody);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("Response code: " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();


            String responseString= response.toString();

            String textResponseOutput = responseString.substring(responseString.indexOf("text\":\"")+7, responseString.indexOf("\"", responseString.indexOf("text\":\"")+7)).replace("\\n", "");
            return textResponseOutput;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
