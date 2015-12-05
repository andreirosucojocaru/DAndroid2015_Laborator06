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

public class CommunicationThread extends Thread {

    final private Socket communicationSocket;
    final private String username;
    final private TextView historyTextView;

    public CommunicationThread(Socket socket, String username, TextView historyTextView) {
        this.communicationSocket = socket;
        this.username = username;
        this.historyTextView = historyTextView;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = Helper.getReader(communicationSocket);
            PrintWriter printWriter = Helper.getWriter(communicationSocket);
            printWriter.println(username);
            final String result = bufferedReader.readLine();
            synchronized (historyTextView) {
                historyTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        historyTextView.append(result + "\n");
                    }
                });
            }
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
