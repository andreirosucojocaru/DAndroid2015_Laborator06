package org.rosedu.dandroid.messageme.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;
import org.rosedu.dandroid.messageme.R;
import org.rosedu.dandroid.messageme.general.Constants;
import org.rosedu.dandroid.messageme.network.CustomRequest;
import org.rosedu.dandroid.messageme.network.VolleyController;

import java.util.HashMap;
import java.util.Map;

public class WriteMessageActivity extends AppCompatActivity {

    private EditText fromEditText;
    private EditText toEditText;
    private EditText subjectEditText;
    private EditText contentEditText;
    private Button sendButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final String senderId = intent.getStringExtra(Constants.SENDER_ID_ATTRIBUTE);
        final String senderUsername = intent.getStringExtra(Constants.SENDER_USERNAME_ATTRIBUTE);
        final String recipientId = intent.getStringExtra(Constants.RECIPIENT_ID_ATTRIBUTE);
        final String recipientUsername = intent.getStringExtra(Constants.RECIPIENT_USERNAME_ATTRIBUTE);

        fromEditText = (EditText)findViewById(R.id.from_edit_text);
        fromEditText.setText(senderUsername);
        toEditText = (EditText)findViewById(R.id.to_edit_text);
        toEditText.setText(recipientUsername);
        subjectEditText = (EditText)findViewById(R.id.subject_edit_text);
        contentEditText = (EditText)findViewById(R.id.content_edit_text);
        sendButton = (Button)findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String subject = subjectEditText.getText().toString();
                String content = contentEditText.getText().toString();
                if (senderId == null || senderId.isEmpty()
                        || recipientId == null || recipientId.isEmpty()
                        || subject == null || subject.isEmpty()
                        || content == null || content.isEmpty()) {
                    Snackbar.make(view, getResources().getString(R.string.all_fields_are_mandatory), Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                Map<String, String> parameters = new HashMap<>();
                parameters.put(Constants.SENDER_ID_ATTRIBUTE, senderId);
                parameters.put(Constants.RECIPIENT_ID_ATTRIBUTE, recipientId);
                parameters.put(Constants.SUBJECT_ATTRIBUTE, subject);
                parameters.put(Constants.CONTENT_ATTRIBUTE, content);
                CustomRequest<JSONObject> customRequest = new CustomRequest(
                        Request.Method.POST,
                        Constants.WRITE_MESSAGE_WEB_SERVICE_ADDRESS,
                        parameters,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String result = null;
                                try {
                                    result = response.get(Constants.RESULT_ATTRIBUTE).toString();
                                } catch (JSONException jsonException) {
                                    Log.e(Constants.TAG, jsonException.getMessage());
                                    if (Constants.DEBUG) {
                                        jsonException.printStackTrace();
                                    }
                                }
                                if ((result == null) || result.isEmpty()) {
                                    Snackbar.make(view, getResources().getString(R.string.an_error_has_occurred), Snackbar.LENGTH_LONG)
                                            .show();
                                } else {
                                    switch(result) {
                                        case Constants.SUCCESS_RESULT:
                                            setResult(RESULT_OK);
                                            finish();
                                            break;
                                        case Constants.FAILURE_RESULT:
                                            Snackbar.make(view, getResources().getString(R.string.login_failed), Snackbar.LENGTH_LONG)
                                                    .show();
                                            break;
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Snackbar.make(view, getResources().getString(R.string.an_error_has_occurred), Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        },
                        JSONObject.class
                );
                VolleyController.getInstance(getApplicationContext()).addToRequestQueue(customRequest);

            }
        });
        cancelButton = (Button)findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

}
