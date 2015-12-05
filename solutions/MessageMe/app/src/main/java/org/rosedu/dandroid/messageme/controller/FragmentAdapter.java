package org.rosedu.dandroid.messageme.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.rosedu.dandroid.messageme.general.Constants;
import org.rosedu.dandroid.messageme.views.ContactsFragment;
import org.rosedu.dandroid.messageme.views.MessagesFragment;
import org.rosedu.dandroid.messageme.views.ProfileFragment;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    private ProfileFragment profileFragment;
    private MessagesFragment messagesFragment;
    private ContactsFragment contactsFragment;

    public FragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case Constants.FRAGMENT_PROFILE_INDEX:
                profileFragment = new ProfileFragment();
                return profileFragment;
            case Constants.FRAGMENT_MESSAGES_INDEX:
                messagesFragment = new MessagesFragment();
                return messagesFragment;
            case Constants.FRAGMENT_CONTACTS_INDEX:
                contactsFragment = new ContactsFragment();
                return contactsFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return Constants.NUMBER_OF_FRAGMENTS;
    }
}