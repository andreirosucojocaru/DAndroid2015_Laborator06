package org.rosedu.dandroid.messageme.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class ProfileFragment extends Fragment {

    private TextView profileTextView;
    private Button communicateButton;
    private EditText userIdEditText;
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordAgainEditText;
    private Button updateButton;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent intent = getActivity().getIntent();
        final String userId = intent.getStringExtra(Constants.USER_ID_ATTRIBUTE);
        final String username = intent.getStringExtra(Constants.USERNAME_ATTRIBUTE);
        String email = intent.getStringExtra(Constants.EMAIL_ATTRIBUTE);
        final String password = intent.getStringExtra(Constants.PASSWORD_ATTRIBUTE);

        profileTextView = (TextView)getActivity().findViewById(R.id.profile_text_view);
        communicateButton = (Button)getActivity().findViewById(R.id.communicate_button);
        communicateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent communicateActivityIntent = new Intent(Constants.COMMUNICATE_ACTIVITY_INTENT_ACTION);
                communicateActivityIntent.putExtra(Constants.USERNAME_ATTRIBUTE, username);
                startActivityForResult(communicateActivityIntent, Constants.COMMUNICATE_ACTIVITY_REQUEST_CODE);
            }
        });
        userIdEditText = (EditText)getActivity().findViewById(R.id.user_id_edit_text);
        userIdEditText.setText(userId);
        usernameEditText = (EditText)getActivity().findViewById(R.id.username_edit_text);
        usernameEditText.setText(username);
        emailEditText = (EditText)getActivity().findViewById(R.id.email_edit_text);
        emailEditText.setText(email);
        passwordEditText = (EditText)getActivity().findViewById(R.id.password_edit_text);
        passwordEditText.setText(password);
        passwordAgainEditText = (EditText)getActivity().findViewById(R.id.password_again_edit_text);
        passwordAgainEditText.setText(password);
        updateButton = (Button)getActivity().findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String newEmail = emailEditText.getText().toString();
                String newPassword = passwordEditText.getText().toString();
                String newPasswordAgain = passwordAgainEditText.getText().toString();

                if (newEmail == null || newEmail.isEmpty()
                        || newPassword == null || newPassword.isEmpty()
                        || newPasswordAgain == null || newPasswordAgain.isEmpty()) {
                    Snackbar.make(view, getResources().getString(R.string.an_error_has_occurred), Snackbar.LENGTH_LONG)
                            .show();
                } else {
                    if (!newPassword.equals(newPasswordAgain)) {
                        Snackbar.make(view, getResources().getString(R.string.passwords_do_not_match), Snackbar.LENGTH_LONG)
                                .show();
                    } else {
                        Map<String, String> parameters = new HashMap<>();
                        parameters.put(Constants.USER_ID_ATTRIBUTE, userId);
                        parameters.put(Constants.EMAIL_ATTRIBUTE, newEmail);
                        parameters.put(Constants.PASSWORD_ATTRIBUTE, newPassword);

                        CustomRequest<JSONObject> customRequest = new CustomRequest(
                                Request.Method.POST,
                                Constants.PROFILE_UPDATE_WEB_SERVICE_ADDRESS,
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
                                        switch(result) {
                                            case Constants.SUCCESS_RESULT:
                                                Snackbar.make(view, getResources().getString(R.string.update_was_successful), Snackbar.LENGTH_LONG)
                                                        .show();
                                                break;
                                            case Constants.FAILURE_RESULT:
                                                Snackbar.make(view, getResources().getString(R.string.update_has_failed), Snackbar.LENGTH_LONG)
                                                        .show();
                                                break;
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
                        VolleyController.getInstance(getActivity().getApplicationContext()).addToRequestQueue(customRequest);
                    }
                }

            }
        });
    }


}
