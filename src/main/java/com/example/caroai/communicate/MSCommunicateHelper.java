package com.example.caroai.communicate;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MSCommunicateHelper<T> {
    private static final String GET_METHOD = "GET";
    private static final Gson gson = new Gson();

    public static <T> T httpGetMethodExecutive(String urlInput, Class<T> clazz){
        URL url = null;
        try {
            url = new URL(urlInput);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Thiết lập phương thức request
        try {
            connection.setRequestMethod(GET_METHOD);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        }

        // Đọc dữ liệu phản hồi
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StringBuilder response = new StringBuilder();
        String line;
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            response.append(line);
        }
        System.out.println(response);
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return gson.fromJson(response.toString(), clazz);
    }
}
