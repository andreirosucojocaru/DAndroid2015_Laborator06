package org.rosedu.dandroid.messageme.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;
import org.rosedu.dandroid.messageme.R;
import org.rosedu.dandroid.messageme.general.Constants;
import org.rosedu.dandroid.messageme.network.CustomJSONRequest;
import org.rosedu.dandroid.messageme.network.VolleyController;

import java.util.HashMap;
import java.util.Map;

public class ReadMessageActivity extends AppCompatActivity {

    private TextView readMessageTextView;
    private TextView fromTextView;
    private TextView toTextView;
    private TextView subjectTextView;
    private TextView dateAndTimeTextView;
    private TextView contentTextView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final String messageId = intent.getStringExtra(Constants.MESSAGE_ID_ATTRIBUTE);

        readMessageTextView = (TextView)findViewById(R.id.read_message_text_view);
        fromTextView = (TextView)findViewById(R.id.from_text_view);
        toTextView = (TextView)findViewById(R.id.to_text_view);
        subjectTextView = (TextView)findViewById(R.id.subject_text_view);
        dateAndTimeTextView = (TextView)findViewById(R.id.date_and_time_text_view);
        contentTextView = (TextView)findViewById(R.id.content_text_view);

        Map<String, String> parameters = new HashMap<>();
        parameters.put(Constants.MESSAGE_ID_ATTRIBUTE, messageId);
        CustomJSONRequest<JSONObject> customRequest = new CustomJSONRequest(
                Request.Method.POST,
                Constants.READ_MESSAGE_WEB_SERVICE_ADDRESS,
                parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String sender = null;
                        String recipient = null;
                        String subject = null;
                        String timestamp = null;
                        String content = null;
                        try {
                            sender = response.get(Constants.SENDER_ATTRIBUTE).toString();
                            recipient = response.get(Constants.RECIPIENT_ATTRIBUTE).toString();
                            subject = response.get(Constants.SUBJECT_ATTRIBUTE).toString();
                            timestamp = response.get(Constants.TIMESTAMP_ATTRIBUTE).toString();
                            content = response.get(Constants.CONTENT_ATTRIBUTE).toString();
                        } catch (JSONException jsonException) {
                            Log.e(Constants.TAG, jsonException.getMessage());
                            if (Constants.DEBUG) {
                                jsonException.printStackTrace();
                            }
                        }
                        if ((sender == null) || sender.isEmpty()
                                || (recipient == null) || recipient.isEmpty()
                                || (subject == null) || subject.isEmpty()
                                || (timestamp == null) || timestamp.isEmpty()
                                || (content == null) || content.isEmpty()) {
                            Snackbar.make(readMessageTextView, getResources().getString(R.string.an_error_has_occurred), Snackbar.LENGTH_LONG)
                                    .show();
                        } else {
                            fromTextView.setText(sender);
                            toTextView.setText(recipient);
                            subjectTextView.setText(subject);
                            dateAndTimeTextView.setText(timestamp);
                            contentTextView.setText(content);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Snackbar.make(readMessageTextView, getResources().getString(R.string.an_error_has_occurred), Snackbar.LENGTH_LONG)
                                .show();
                    }
                },
                JSONObject.class
        );
        VolleyController.getInstance(getApplicationContext()).addToRequestQueue(customRequest);

        backButton = (Button)findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

}
