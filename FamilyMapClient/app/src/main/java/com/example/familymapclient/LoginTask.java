package com.example.familymapclient;

import android.os.Bundle;
import android.os.Message;
import android.os.Handler;

import java.net.URL;

import Request.UserLoginRequest;


//makes a call to serverProxy to login
    // then gets the data with another call to serverProxy once it has logged in and caches that data somewhere
public class LoginTask implements Runnable {
        private final Handler messageHandler;
        private final URL url;
        private final UserLoginRequest ulReq;
        private static final String PERSON_KEY = "personKey";
        private static final String EVENT_KEY = "eventKey";

        public LoginTask(Handler messageHandler, URL url, UserLoginRequest ulReq) {
            this.messageHandler = messageHandler;
            this.url = url;
            this.ulReq = ulReq;
        }

        @Override
        public void run() {
            ServerProxy serverProxy = new ServerProxy();

            //pass in event[] and person[] s to loginRequest for the next step
            String[] result = serverProxy.loginRequest(url, ulReq); //success is temp

            sendMessage(result); //will need to edit sendMessage to put event[] and person[]
        }

        private void sendMessage(String[] result) {
            Message message = Message.obtain();

            Bundle messageBundle = new Bundle();
            messageBundle.putString(PERSON_KEY, result[1]);
            messageBundle.putString(EVENT_KEY, result[2]);
            message.setData(messageBundle);

            messageHandler.sendMessage(message);
        }
}
