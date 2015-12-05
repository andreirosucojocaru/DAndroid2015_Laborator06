package org.rosedu.dandroid.messageme.network;

import android.util.Log;
import android.widget.TextView;

import org.rosedu.dandroid.messageme.general.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {

    private String ipAddress;
    private int port;
    private String username;
    private TextView historyTextView;

    private ServerSocket serverSocket;

    private boolean isRunning;

    public ServerThread(String ipAddress, int port, String username, TextView historyTextView) {
        this.ipAddress = ipAddress;
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
                        if (serverSocket != null) {
                            serverSocket.close();
                        }
                        isRunning = false;
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
        try {
            serverSocket = new ServerSocket(port);
            while(isRunning) {
                Socket communicationSocket = serverSocket.accept();
                new CommunicationThread(communicationSocket, username, historyTextView).start();
            }
        } catch (IOException ioException) {
            Log.e(Constants.TAG, ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
    }

}
