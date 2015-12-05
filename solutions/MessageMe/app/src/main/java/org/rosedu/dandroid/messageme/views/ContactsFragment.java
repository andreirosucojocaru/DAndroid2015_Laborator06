package org.rosedu.dandroid.messageme.views;

import android.content.Context;
import android.content.Intent;
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
import org.rosedu.dandroid.messageme.network.CustomRequest;
import org.rosedu.dandroid.messageme.network.VolleyController;

import java.util.HashMap;
import java.util.Map;


public class ContactsFragment extends Fragment {

    TextView contactsTextView;
    LinearLayout contactsList;
    RelativeLayout contact;

    public ContactsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        contactsTextView = (TextView)getActivity().findViewById(R.id.contacts_text_view);
        contactsList = (LinearLayout)getActivity().findViewById(R.id.contacts_list);
        final LayoutInflater inflater = (LayoutInflater)getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final String senderId = getActivity().getIntent().getStringExtra(Constants.USER_ID_ATTRIBUTE);
        final String senderUsername = getActivity().getIntent().getStringExtra(Constants.USERNAME_ATTRIBUTE);
        Map<String, String> parameters = new HashMap<>();
        parameters.put(Constants.USERNAME_ATTRIBUTE, senderUsername);
        CustomRequest<JSONArray> customRequest = new CustomRequest(
                Request.Method.POST,
                Constants.USER_LIST_WEB_SERVICE_ADDRESS,
                parameters,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int position = 0; position < response.length(); position++) {
                                JSONObject user = response.getJSONObject(position);
                                final String recipientId = user.get(Constants.USER_ID_ATTRIBUTE).toString();
                                final String recipientUsername = user.get(Constants.USERNAME_ATTRIBUTE).toString();
                                contact = (RelativeLayout)inflater.inflate(R.layout.contact, null);
                                TextView usernameTextView = (TextView)contact.findViewById(R.id.username_text_view);
                                usernameTextView.setText(recipientUsername);
                                Button writeMessageButton = (Button)contact.findViewById(R.id.write_message_button);
                                writeMessageButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent writeMessageIntent = new Intent(Constants.WRITE_MESSAGE_ACTIVITY_INTENT_ACTION);
                                        writeMessageIntent.putExtra(Constants.SENDER_ID_ATTRIBUTE, senderId);
                                        writeMessageIntent.putExtra(Constants.SENDER_USERNAME_ATTRIBUTE, senderUsername);
                                        writeMessageIntent.putExtra(Constants.RECIPIENT_ID_ATTRIBUTE, recipientId);
                                        writeMessageIntent.putExtra(Constants.RECIPIENT_USERNAME_ATTRIBUTE, recipientUsername);
                                        getActivity().startActivityForResult(writeMessageIntent, Constants.WRITE_MESSAGE_ACTIVITY_REQUEST_CODE);
                                    }
                                });
                                contactsList.addView(contact);
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
                        Snackbar.make(contactsTextView, getResources().getString(R.string.an_error_has_occurred), Snackbar.LENGTH_LONG)
                                .show();
                    }

                },
                JSONArray.class
        );
        VolleyController.getInstance(getActivity().getApplicationContext()).addToRequestQueue(customRequest);
    }

}
