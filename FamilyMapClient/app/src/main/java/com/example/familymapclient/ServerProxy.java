package com.example.familymapclient;

import android.util.Log;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import Request.UserLoginRequest;
import Request.UserRegisterRequest;
import Response.UserLoginResponse;
import Response.UserRegisterResponse;

public class ServerProxy {
    //has the code for contacting my server
    //uses httpURLConnection class stuff
    //as well as deal with requests and responses
    private static final String LOG_TAG = "ServerProxy";


    public String[] loginRequest(URL url, UserLoginRequest ulReq)
    {
        String[] result = new String[3];
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            osw.write(new Gson().toJson(ulReq));
            osw.flush();
            osw.close();
            os.close();  //don't forget to close the OutputStream
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream responseBody = connection.getInputStream(); // Get response body input stream

                // Read response body bytes
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = responseBody.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
                result[0] = outputStream.toString(); // Convert response body bytes to a string
                connection.disconnect();

                UserLoginResponse lRes = new Gson().fromJson(result[0], UserLoginResponse.class);
                if (!result[0].contains("Error"))
                {
                    String authToken = lRes.getAuthtoken();
                    URL dataUrl = new URL (url.getProtocol(), url.getHost(), url.getPort(), "/person");
                    result[1] = getData(authToken, dataUrl);
                    dataUrl = new URL (url.getProtocol(), url.getHost(), url.getPort(), "/event");
                    result[2] = getData(authToken, dataUrl);
                }

            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        return result;
    }

    public String[] registerRequest(URL url, UserRegisterRequest urReq)
    {
        String[] result = new String[3];
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            OutputStream os = connection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
            osw.write(new Gson().toJson(urReq));
            osw.flush();
            osw.close();
            os.close();  //don't forget to close the OutputStream
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream responseBody = connection.getInputStream(); // Get response body input stream

                // Read response body bytes
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = responseBody.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
                result[0] = outputStream.toString(); // Convert response body bytes to a string
                connection.disconnect();

                UserRegisterResponse rRes = new Gson().fromJson(result[0], UserRegisterResponse.class);
                if (!result[0].contains("Error"))
                {
                    String authToken = rRes.getauthtoken();
                    URL dataUrl = new URL (url.getProtocol(), url.getHost(), url.getPort(), "/person");
                    result[1] = getData(authToken, dataUrl);
                    dataUrl = new URL (url.getProtocol(), url.getHost(), url.getPort(), "/event");
                    result[2] = getData(authToken, dataUrl);
                }

            }
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        return result;
    }

    //gets the specified person's family data as well as all of their corresponding event data
    public String getData(String authToken, URL dataUrl) throws IOException {
        String result = null;
        try {
            HttpURLConnection dataConnection = (HttpURLConnection) dataUrl.openConnection();
            dataConnection.setRequestMethod("GET");
            dataConnection.setRequestProperty("Authorization", authToken);
            dataConnection.connect();

            //get Response
            InputStream responseBody = dataConnection.getInputStream(); // Get response body input stream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = responseBody.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }

            //grab result and close connection
            result = outputStream.toString();
            dataConnection.disconnect();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
        }
        return result;
    }
}
