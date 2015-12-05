package org.rosedu.dandroid.messageme.network;

import android.util.Log;
import android.widget.TextView;

import org.rosedu.dandroid.messageme.general.Constants;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerThread extends Thread {

    private int port;
    private String username;
    private TextView historyTextView;

    private ServerSocket serverSocket;

    private boolean isRunning;

    public ServerThread(int port, String username, TextView historyTextView) {
        this.port = port;
        this.username = username;
        this.historyTextView = historyTextView;
    }

    public void startServer() {
        isRunning = true;
        start();
    }

    public void stopServer() {
        if (isRunning) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        isRunning = false;
                        if (serverSocket != null) {
                            serverSocket.close();
                        }
                    } catch (IOException ioException) {
                        Log.e(Constants.TAG, ioException.getMessage());
                        if (Constants.DEBUG) {
                            ioException.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    @Override
    public void run() {
        // TODO: exercise 4b
    }

}
