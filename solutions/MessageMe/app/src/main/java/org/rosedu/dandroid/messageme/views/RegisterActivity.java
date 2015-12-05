package org.rosedu.dandroid.messageme.views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.rosedu.dandroid.messageme.R;
import org.rosedu.dandroid.messageme.general.Constants;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText userNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordAgainEditText;
    private Button registerButton;
    private Button cancelButton;

    private class RegisterThread extends Thread {

        protected View view;

        public RegisterThread(View view) {
            this.view = view;
        }

        @Override
        public void run() {
            String userName = userNameEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String passwordAgain = passwordAgainEditText.getText().toString();
            if (userName == null || userName.isEmpty() ||
                    email == null || email.isEmpty() ||
                    password == null || password.isEmpty() ||
                    passwordAgain == null || passwordAgain.isEmpty()) {
                view.post(new Thread() {
                    @Override
                    public void run() {
                        Snackbar.make(view, getResources().getString(R.string.all_fields_are_mandatory), Snackbar.LENGTH_LONG)
                                .show();
                    }
                });
                return;
            }
            if (!password.equals(passwordAgain)) {
                view.post(new Thread() {
                    @Override
                    public void run() {
                        Snackbar.make(view, getResources().getString(R.string.passwords_do_not_match), Snackbar.LENGTH_LONG)
                                .show();
                    }
                });
                return;
            }

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Constants.REGISTER_WEB_SERVICE_ADDRESS);
            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair(Constants.USERNAME_ATTRIBUTE, userName));
            parameters.add(new BasicNameValuePair(Constants.EMAIL_ATTRIBUTE, email));
            parameters.add(new BasicNameValuePair(Constants.PASSWORD_ATTRIBUTE, password));
            try {
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
                httpPost.setEntity(urlEncodedFormEntity);
            } catch (UnsupportedEncodingException unsupportedEncodingException) {
                Log.e(Constants.TAG, unsupportedEncodingException.getMessage());
                if (Constants.DEBUG) {
                    unsupportedEncodingException.printStackTrace();
                }
            }
            try {
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                JSONObject message = new JSONObject(httpClient.execute(httpPost, responseHandler));
                String result = message.getString(Constants.RESULT_ATTRIBUTE);
                if (result.equals(Constants.SUCCESS_RESULT)) {
                    view.post(new Thread() {
                        @Override
                        public void run() {
                            Snackbar.make(view, getResources().getString(R.string.thank_you_for_registering), Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    });
                    setResult(RESULT_OK);
                    finish();
                }
                if (result.equals(Constants.FAILURE_RESULT)) {
                    view.post(new Thread() {
                        @Override
                        public void run() {
                            Snackbar.make(view, getResources().getString(R.string.registration_failed), Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } catch (ClientProtocolException clientProtocolException) {
                Log.e(Constants.TAG, clientProtocolException.getMessage());
                if (Constants.DEBUG) {
                    clientProtocolException.printStackTrace();
                }
            } catch (IOException ioException) {
                Log.e(Constants.TAG, ioException.getMessage());
                if (Constants.DEBUG) {
                    ioException.printStackTrace();
                }
            } catch (JSONException jsonException) {
                Log.e(Constants.TAG, jsonException.getMessage());
                if (Constants.DEBUG) {
                    jsonException.getMessage();
                }
            }
        }

    }

    private class RegisterAsyncTask extends AsyncTask<Void, Void, String> {

        private View view;

        private String userName;
        private String email;
        private String password;
        private String passwordAgain;

        public RegisterAsyncTask(View view) {
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            userName = userNameEditText.getText().toString();
            email = emailEditText.getText().toString();
            password = passwordEditText.getText().toString();
            passwordAgain = passwordAgainEditText.getText().toString();
        }

        @Override
        protected String doInBackground(Void... arguments) {
            if (userName == null || userName.isEmpty() ||
                    email == null || email.isEmpty() ||
                    password == null || password.isEmpty() ||
                    passwordAgain == null || passwordAgain.isEmpty()) {
                return getResources().getString(R.string.all_fields_are_mandatory);
            }
            if (!password.equals(passwordAgain)) {
                return getResources().getString(R.string.passwords_do_not_match);
            }

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Constants.REGISTER_WEB_SERVICE_ADDRESS);
            List<NameValuePair> parameters = new ArrayList<>();
            parameters.add(new BasicNameValuePair(Constants.USERNAME_ATTRIBUTE, userName));
            parameters.add(new BasicNameValuePair(Constants.EMAIL_ATTRIBUTE, email));
            parameters.add(new BasicNameValuePair(Constants.PASSWORD_ATTRIBUTE, password));
            try {
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
                httpPost.setEntity(urlEncodedFormEntity);
            } catch (UnsupportedEncodingException unsupportedEncodingException) {
                Log.e(Constants.TAG, unsupportedEncodingException.getMessage());
                if (Constants.DEBUG) {
                    unsupportedEncodingException.printStackTrace();
                }
            }
            try {
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                JSONObject message = new JSONObject(httpClient.execute(httpPost, responseHandler));
                String result = message.getString(Constants.RESULT_ATTRIBUTE);
                return result;
            } catch (ClientProtocolException clientProtocolException) {
                Log.e(Constants.TAG, clientProtocolException.getMessage());
                if (Constants.DEBUG) {
                    clientProtocolException.printStackTrace();
                }
            } catch (IOException ioException) {
                Log.e(Constants.TAG, ioException.getMessage());
                if (Constants.DEBUG) {
                    ioException.printStackTrace();
                }
            } catch (JSONException jsonException) {
                Log.e(Constants.TAG, jsonException.getMessage());
                if (Constants.DEBUG) {
                    jsonException.getMessage();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            switch (result) {
                case Constants.SUCCESS_RESULT:
                    Snackbar.make(view, getResources().getString(R.string.thank_you_for_registering), Snackbar.LENGTH_LONG)
                            .show();
                    setResult(RESULT_OK);
                    finish();
                    break;
                case Constants.FAILURE_RESULT:
                    Snackbar.make(view, getResources().getString(R.string.registration_failed), Snackbar.LENGTH_LONG)
                            .show();
                    break;
                default:
                    Snackbar.make(view, result, Snackbar.LENGTH_LONG)
                            .show();
                    break;
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userNameEditText = (EditText)findViewById(R.id.username_edit_text);
        emailEditText = (EditText)findViewById(R.id.email_edit_text);
        passwordEditText = (EditText)findViewById(R.id.password_edit_text);
        passwordAgainEditText = (EditText)findViewById(R.id.password_again_edit_text);
        registerButton = (Button)findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new RegisterThread(view).start();
                new RegisterAsyncTask(view).execute();
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
