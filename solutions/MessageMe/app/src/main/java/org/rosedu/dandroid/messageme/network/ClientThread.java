package org.rosedu.dandroid.messageme.network;

import android.util.Log;
import android.widget.TextView;

import org.rosedu.dandroid.messageme.general.Constants;
import org.rosedu.dandroid.messageme.general.Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

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
        try {
            Socket clientSocket = new Socket(ipAddress, port);
            BufferedReader bufferedReader = Helper.getReader(clientSocket);
            PrintWriter printWriter = Helper.getWriter(clientSocket);
            printWriter.println(username);
            final String result = bufferedReader.readLine();
            resultTextView.post(new Runnable(){
                @Override
                public void run() {
                    resultTextView.append(result + "\n");
                }
            });
        } catch (UnknownHostException unknownHostException) {
            Log.e(Constants.TAG, unknownHostException.getMessage());
            if (Constants.DEBUG) {
                unknownHostException.printStackTrace();
            }
        } catch (IOException ioException) {
            Log.e(Constants.TAG, ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
    }
}
