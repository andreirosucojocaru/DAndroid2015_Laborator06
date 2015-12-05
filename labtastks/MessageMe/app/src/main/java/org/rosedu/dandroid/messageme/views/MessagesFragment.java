package org.rosedu.dandroid.messageme.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rosedu.dandroid.messageme.R;
import org.rosedu.dandroid.messageme.general.Constants;
import org.rosedu.dandroid.messageme.network.CustomJSONRequest;
import org.rosedu.dandroid.messageme.network.VolleyController;

import java.util.HashMap;
import java.util.Map;


public class MessagesFragment extends Fragment {

    TextView messagesTextView;
    LinearLayout messagesList;
    RelativeLayout message;

    public MessagesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        messagesTextView = (TextView)getActivity().findViewById(R.id.messages_text_view);
        messagesList = (LinearLayout)getActivity().findViewById(R.id.messages_list);
        final LayoutInflater inflater = (LayoutInflater)getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final String userId = getActivity().getIntent().getStringExtra(Constants.USER_ID_ATTRIBUTE);
        final String username = getActivity().getIntent().getStringExtra(Constants.USERNAME_ATTRIBUTE);
        Map<String, String> parameters = new HashMap<>();
        parameters.put(Constants.USER_ID_ATTRIBUTE, userId);
        CustomJSONRequest<JSONArray> customRequest = new CustomJSONRequest(
                Request.Method.POST,
                Constants.MESSAGE_LIST_WEB_SERVICE_ADDRESS,
                parameters,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int position = 0; position < response.length(); position++) {
                                JSONObject dispatch = response.getJSONObject(position);
                                final String messageId = dispatch.get(Constants.MESSAGE_ID_ATTRIBUTE).toString();
                                final String sender = dispatch.get(Constants.SENDER_ATTRIBUTE).toString();
                                final String receiver = dispatch.get(Constants.RECIPIENT_ATTRIBUTE).toString();
                                final String subject = dispatch.get(Constants.SUBJECT_ATTRIBUTE).toString();
                                final String content = dispatch.get(Constants.CONTENT_ATTRIBUTE).toString();
                                final String status = dispatch.get(Constants.STATUS_ATTRIBUTE).toString();
                                message = (RelativeLayout)inflater.inflate(R.layout.message, null);
                                TextView senderUsernameTextView = (TextView)message.findViewById(R.id.sender_username_text_view);
                                senderUsernameTextView.setText(sender);
                                if (status.equals(Constants.UNREAD)) {
                                    senderUsernameTextView.setTypeface(null, Typeface.BOLD);
                                }
                                TextView subjectTextView = (TextView)message.findViewById(R.id.subject_text_view);
                                subjectTextView.setText(subject);
                                if (status.equals(Constants.UNREAD)) {
                                    subjectTextView.setTypeface(null, Typeface.BOLD);
                                }
                                Button readMessageButton = (Button) message.findViewById(R.id.read_message_button);
                                readMessageButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent writeMessageIntent = new Intent(Constants.READ_MESSAGE_ACTIVITY_INTENT_ACTION);
                                        writeMessageIntent.putExtra(Constants.MESSAGE_ID_ATTRIBUTE, messageId);
                                        getActivity().startActivityForResult(writeMessageIntent, Constants.READ_MESSAGE_ACTIVITY_REQUEST_CODE);
                                    }
                                });
                                messagesList.addView(message);
                            }
                        } catch (JSONException jsonException) {
                            Log.e(Constants.TAG, jsonException.getMessage());
                            if (Constants.DEBUG) {
                                jsonException.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Snackbar.make(messagesTextView, getResources().getString(R.string.an_error_has_occurred), Snackbar.LENGTH_LONG)
                                .show();
                    }

                },
                JSONArray.class
        );
        VolleyController.getInstance(getActivity().getApplicationContext()).addToRequestQueue(customRequest);
    }

}
