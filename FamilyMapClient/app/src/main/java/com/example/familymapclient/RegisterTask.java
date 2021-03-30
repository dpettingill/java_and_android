package com.example.familymapclient;

import android.os.Bundle;
import android.os.Message;
import android.os.Handler;

import java.net.URL;

import Request.UserRegisterRequest;


//makes a call to serverProxy to login
// then gets the data with another call to serverProxy once it has logged in and caches that data somewhere
public class RegisterTask implements Runnable {
    private final Handler messageHandler;
    private final URL url;
    private final UserRegisterRequest urReq;
    private static final String USER_KEY = "userKey";
    private static final String PERSONS_KEY = "personsKey";
    private static final String EVENTS_KEY = "eventsKey";

    public RegisterTask(Handler messageHandler, URL url, UserRegisterRequest urReq) {
        this.messageHandler = messageHandler;
        this.url = url;
        this.urReq = urReq;
    }

    @Override
    public void run() {
        ServerProxy serverProxy = new ServerProxy();

        //pass in event[] and person[] s to loginRequest for the next step
        String[] result = serverProxy.registerRequest(url, urReq); //success is temp

        sendMessage(result); //will need to edit sendMessage to put event[] and person[]
    }

    private void sendMessage(String[] result) {
        Message message = Message.obtain();

        Bundle messageBundle = new Bundle();
        messageBundle.putString(USER_KEY, result[0]);
        messageBundle.putString(PERSONS_KEY, result[1]);
        messageBundle.putString(EVENTS_KEY, result[2]);
        message.setData(messageBundle);

        messageHandler.sendMessage(message);
    }
}
