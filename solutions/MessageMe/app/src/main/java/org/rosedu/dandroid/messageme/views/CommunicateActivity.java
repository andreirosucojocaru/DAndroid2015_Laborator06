package org.rosedu.dandroid.messageme.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.rosedu.dandroid.messageme.R;
import org.rosedu.dandroid.messageme.general.Constants;
import org.rosedu.dandroid.messageme.network.ClientThread;
import org.rosedu.dandroid.messageme.network.ServerThread;

public class CommunicateActivity extends AppCompatActivity {

    private EditText serverIpAddressEditText;
    private EditText serverPortEditText;
    private Button communicateButton;
    private TextView historyTextView;
    private EditText clientIpAddressEditText;
    private EditText clientPortEditText;
    private Button queryButton;
    private TextView resultTextView;

    private ServerThread serverThread;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate);

        Intent intent = getIntent();
        username = intent.getStringExtra(Constants.USERNAME_ATTRIBUTE);

        serverIpAddressEditText = (EditText)findViewById(R.id.server_ip_address_edit_text);
        serverPortEditText = (EditText)findViewById(R.id.server_port_edit_text);
        communicateButton = (Button)findViewById(R.id.communicate_button);
        communicateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipAddress = serverIpAddressEditText.getText().toString();
                int port = Integer.parseInt(serverPortEditText.getText().toString());
                if (communicateButton.getText().toString().equals(getResources().getString(R.string.bind))) {
                    serverThread = new ServerThread(ipAddress, port, username, historyTextView);
                    if (serverThread != null) {
                        serverThread.startServer();
                    }
                    communicateButton.setText(getResources().getString(R.string.unbind));
                    //communicateButton.setBackground(getDrawable(R.color.green));
                    return;
                }
                if (communicateButton.getText().toString().equals(getResources().getString(R.string.unbind))) {
                    if (serverThread != null) {
                        serverThread.stopServer();
                    }
                    communicateButton.setText(getResources().getString(R.string.bind));
                    //communicateButton.setBackground(getDrawable(R.color.red));
                    return;
                }
            }
        });
        historyTextView = (TextView)findViewById(R.id.history_text_view);
        clientIpAddressEditText = (EditText)findViewById(R.id.client_ip_address_edit_text);
        clientPortEditText = (EditText)findViewById(R.id.client_port_edit_text);
        queryButton = (Button)findViewById(R.id.query_button);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipAddress = clientIpAddressEditText.getText().toString();
                int port = Integer.parseInt(clientPortEditText.getText().toString());
                new ClientThread(ipAddress, port, username, resultTextView).start();
            }
        });
        resultTextView = (TextView)findViewById(R.id.result_text_view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (serverThread != null) {
            serverThread.stopServer();
        }
        communicateButton.setText(getResources().getString(R.string.bind));
    }

}
