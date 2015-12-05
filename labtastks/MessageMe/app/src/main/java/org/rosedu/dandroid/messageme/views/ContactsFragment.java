package org.rosedu.dandroid.messageme.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.rosedu.dandroid.messageme.R;
import org.rosedu.dandroid.messageme.general.Constants;

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

        // TODO: exercise 03

    }

}
