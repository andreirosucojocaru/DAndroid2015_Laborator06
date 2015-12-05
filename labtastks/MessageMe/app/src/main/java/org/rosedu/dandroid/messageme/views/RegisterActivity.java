package org.rosedu.dandroid.messageme.views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.rosedu.dandroid.messageme.R;

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

            // TODO: exercise 02a

        }

    }

    private class RegisterAsyncTask extends AsyncTask<Void, Void, String> {

        private View view;

        public RegisterAsyncTask(View view) {
            this.view = view;
        }

        @Override
        protected void onPreExecute() {

            // TODO: exercise 02b

        }

        @Override
        protected String doInBackground(Void... arguments) {

            // TODO: exercise 02b

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            // TODO: exercise 02b

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

                // TODO: exercise 02

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
