package org.rosedu.dandroid.messageme.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.rosedu.dandroid.messageme.general.Constants;

import org.rosedu.dandroid.messageme.R;
import org.rosedu.dandroid.messageme.network.VolleyController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button notRegisteredYetButton;
    private ImageView logoImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = (EditText)findViewById(R.id.username_edit_text);
        passwordEditText = (EditText)findViewById(R.id.password_edit_text);

        logoImageView = (ImageView)findViewById(R.id.logo_image_view);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(Constants.ANDROID_ROSEDU_ORG_PAGE).get();
                    Element root = doc.child(0);
                    Elements logoWrappers =  root.getElementsByAttributeValue(Constants.LOGO_ATTRIBUTE, Constants.LOGO_VALUE);
                    Element logoWrapper = logoWrappers.first();
                    String logoURL = Constants.ANDROID_ROSEDU_ORG_PAGE + logoWrapper.child(0).attr(Constants.SRC);
                    ImageRequest request = new ImageRequest(
                            logoURL,
                            new Response.Listener<Bitmap>() {
                                    @Override
                                    public void onResponse(Bitmap bitmap) {
                                        logoImageView.setImageBitmap(bitmap);
                                    }
                                },
                            Constants.LOGO_WIDTH,
                            Constants.LOGO_HEIGHT,
                            null,
                            new Response.ErrorListener() {
                                    public void onErrorResponse(VolleyError error) {
                                        logoImageView.setImageResource(R.color.black);
                                    }
                                });
                    VolleyController.getInstance(getApplicationContext()).addToRequestQueue(request);
                } catch (IOException ioException) {
                    Log.e(Constants.TAG, ioException.getMessage());
                    if (Constants.DEBUG) {
                        ioException.printStackTrace();
                    }
                }
            }
        }).start();

        loginButton = (Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final String username = usernameEditText.getText().toString();
                final String password = passwordEditText.getText().toString();
                if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                    Snackbar.make(view, getResources().getString(R.string.all_fields_are_mandatory), Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                /*Map<String, String> parameters = new HashMap<>();
                parameters.put(Constants.USERNAME_ATTRIBUTE, username);
                parameters.put(Constants.PASSWORD_ATTRIBUTE, password);

                CustomRequest<JSONObject> customRequest = new CustomRequest(
                        Request.Method.POST,
                        Constants.LOGIN_WEB_SERVICE_ADDRESS,
                        parameters,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String userId = null;
                                String email = null;
                                String result = null;
                                try {
                                    userId = response.get(Constants.USER_ID_ATTRIBUTE).toString();
                                    email = response.get(Constants.EMAIL_ATTRIBUTE).toString();
                                    result = response.get(Constants.RESULT_ATTRIBUTE).toString();
                                } catch (JSONException jsonException) {
                                    Log.e(Constants.TAG, jsonException.getMessage());
                                    if (Constants.DEBUG) {
                                        jsonException.printStackTrace();
                                    }
                                }
                                if ((userId == null) || userId.isEmpty()
                                        || (email == null) || email.isEmpty()
                                        || (result == null) || result.isEmpty()) {
                                    Snackbar.make(view, getResources().getString(R.string.an_error_has_occurred), Snackbar.LENGTH_LONG)
                                            .show();
                                } else {
                                    if (Integer.parseInt(userId) == -1 || result.equals(Constants.FAILURE_RESULT)) {
                                        Snackbar.make(view, getResources().getString(R.string.login_failed), Snackbar.LENGTH_LONG)
                                                .show();
                                    } else {
                                        Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                                        mainActivityIntent.putExtra(Constants.USER_ID_ATTRIBUTE, userId);
                                        mainActivityIntent.putExtra(Constants.USERNAME_ATTRIBUTE, username);
                                        mainActivityIntent.putExtra(Constants.EMAIL_ATTRIBUTE, email);
                                        mainActivityIntent.putExtra(Constants.PASSWORD_ATTRIBUTE, password);
                                        startActivity(mainActivityIntent);
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
                VolleyController.getInstance(getApplicationContext()).addToRequestQueue(customRequest);*/

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        URL url = null;
                        HttpURLConnection httpURLConnection = null;
                        try {
                            url = new URL(Constants.LOGIN_WEB_SERVICE_ADDRESS);
                            httpURLConnection = (HttpURLConnection)url.openConnection();
                            httpURLConnection.setRequestMethod(Constants.POST_REQUEST_METHOD);
                            httpURLConnection.setDoInput(true);
                            httpURLConnection.setDoOutput(true);
                            OutputStream outputStream = httpURLConnection.getOutputStream();
                            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                            StringBuilder parameters = new StringBuilder();
                            parameters.append(URLEncoder.encode(Constants.USERNAME_ATTRIBUTE, "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8"));
                            parameters.append("&");
                            parameters.append(URLEncoder.encode(Constants.PASSWORD_ATTRIBUTE, "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8"));
                            bufferedWriter.write(parameters.toString());
                            bufferedWriter.flush();
                            bufferedWriter.close();
                            outputStream.close();
                            int responseCode = httpURLConnection.getResponseCode();
                            StringBuilder response = new StringBuilder();
                            if (responseCode == HttpURLConnection.HTTP_OK) {
                                InputStream inputStream = httpURLConnection.getInputStream();
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                                String line;
                                while ((line = bufferedReader.readLine()) != null) {
                                    response.append(line);
                                }
                            }
                            String userId = null;
                            String email = null;
                            String result = null;
                            try {
                                JSONObject jsonObject = new JSONObject(response.toString());
                                userId = jsonObject.get(Constants.USER_ID_ATTRIBUTE).toString();
                                email = jsonObject.get(Constants.EMAIL_ATTRIBUTE).toString();
                                result = jsonObject.get(Constants.RESULT_ATTRIBUTE).toString();
                            } catch (JSONException jsonException) {
                                Log.e(Constants.TAG, jsonException.getMessage());
                                if (Constants.DEBUG) {
                                    jsonException.printStackTrace();
                                }
                            }
                            if ((userId == null) || userId.isEmpty()
                                    || (email == null) || email.isEmpty()
                                    || (result == null) || result.isEmpty()) {
                                Snackbar.make(view, getResources().getString(R.string.an_error_has_occurred), Snackbar.LENGTH_LONG)
                                        .show();
                            } else {
                                if (Integer.parseInt(userId) == -1 || result.equals(Constants.FAILURE_RESULT)) {
                                    Snackbar.make(view, getResources().getString(R.string.login_failed), Snackbar.LENGTH_LONG)
                                            .show();
                                } else {
                                    Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                                    mainActivityIntent.putExtra(Constants.USER_ID_ATTRIBUTE, userId);
                                    mainActivityIntent.putExtra(Constants.USERNAME_ATTRIBUTE, username);
                                    mainActivityIntent.putExtra(Constants.EMAIL_ATTRIBUTE, email);
                                    mainActivityIntent.putExtra(Constants.PASSWORD_ATTRIBUTE, password);
                                    startActivity(mainActivityIntent);
                                }
                            }
                        } catch (MalformedURLException malformedURLException) {
                            Log.e(Constants.TAG, malformedURLException.getMessage());
                            if (Constants.DEBUG) {
                                malformedURLException.printStackTrace();
                            }
                        } catch (IOException ioException) {
                            Log.e(Constants.TAG, ioException.getMessage());
                            if (Constants.DEBUG) {
                                ioException.printStackTrace();
                            }
                        } finally {
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                        }
                    }
                }).start();
            }
        });

        notRegisteredYetButton = (Button)findViewById(R.id.not_registered_yet_button);
        notRegisteredYetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Constants.REGISTER_ACTIVITY_INTENT_ACTION);
                startActivityForResult(registerIntent, Constants.REGISTER_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case Constants.REGISTER_ACTIVITY_REQUEST_CODE:
                Snackbar.make(notRegisteredYetButton, getResources().getString(R.string.activity_returned_with_result) + " " + resultCode, Snackbar.LENGTH_LONG)
                        .show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
