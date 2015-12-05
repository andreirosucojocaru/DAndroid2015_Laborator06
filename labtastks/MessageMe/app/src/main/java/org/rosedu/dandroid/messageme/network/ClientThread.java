package org.rosedu.dandroid.messageme.network;

import android.widget.TextView;

public class ClientThread extends Thread {

    private String ipAddress;
    private int port;
    private String username;
    private TextView resultTextView;

    public ClientThread(String ipAddress, int port, String username, TextView resultTextView) {
        this.ipAddress = ipAddress;
        this.port = port;
        this.username = username;
        this.resultTextView = resultTextView;
    }

    @Override
    public void run() {
        // TODO: exercise 04d
    }
}
