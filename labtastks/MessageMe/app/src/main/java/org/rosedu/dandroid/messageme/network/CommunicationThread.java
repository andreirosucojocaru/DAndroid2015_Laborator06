package org.rosedu.dandroid.messageme.network;

import android.widget.TextView;

import java.net.Socket;

public class CommunicationThread extends Thread {

    private Socket communicationSocket;
    private String username;
    final private TextView historyTextView;

    public CommunicationThread(Socket socket, String username, TextView historyTextView) {
        this.communicationSocket = socket;
        this.username = username;
        this.historyTextView = historyTextView;
    }

    @Override
    public void run() {
        // TODO: exercise 04c
    }
}
