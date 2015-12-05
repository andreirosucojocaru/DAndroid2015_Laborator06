package org.rosedu.dandroid.messageme.general;

public interface Constants {
    public final static int REGISTER_ACTIVITY_REQUEST_CODE = 1;
    public final static String REGISTER_ACTIVITY_INTENT_ACTION = "org.rosedu.android.intent.action.RegisterActivity";
    public final static int WRITE_MESSAGE_ACTIVITY_REQUEST_CODE = 2;
    public final static String WRITE_MESSAGE_ACTIVITY_INTENT_ACTION = "org.rosedu.android.intent.action.WriteMessageActivity";
    public final static int READ_MESSAGE_ACTIVITY_REQUEST_CODE = 3;
    public final static String READ_MESSAGE_ACTIVITY_INTENT_ACTION = "org.rosedu.android.intent.action.ReadMessageActivity";
    public final static int COMMUNICATE_ACTIVITY_REQUEST_CODE = 4;
    public final static String COMMUNICATE_ACTIVITY_INTENT_ACTION = "org.rosedu.android.intent.action.CommunicateActivity";

    public final static int FRAGMENT_PROFILE_INDEX = 0;
    public final static String FRAGMENT_PROFILE = "Profile";
    public final static int FRAGMENT_MESSAGES_INDEX = 1;
    public final static String FRAGMENT_MESSAGES = "Messages";
    public final static int FRAGMENT_CONTACTS_INDEX = 2;
    public final static String FRAGMENT_CONTACTS = "Contacts";
    public final static int NUMBER_OF_FRAGMENTS = 3;

    public final static String TAG = "[MESSAGE_ME] ";
    public final static boolean DEBUG = false;

    public final static String GET_REQUEST_METHOD = "GET";
    public final static String POST_REQUEST_METHOD = "POST";

    public final static String ANDROID_ROSEDU_ORG_PAGE = "http://android.rosedu.org";
    public final static String LOGO_ATTRIBUTE = "title";
    public final static String LOGO_VALUE = "d_androidbetter.png";
    public final static String SRC = "src";
    public final static int LOGO_WIDTH = 480;
    public final static int LOGO_HEIGHT = 200;

    public final static String REGISTER_WEB_SERVICE_ADDRESS = "http://dandroid.andreirosucojocaru.ro/user_registration.php";
    public final static String USERNAME_ATTRIBUTE = "username";
    public final static String EMAIL_ATTRIBUTE = "email";
    public final static String PASSWORD_ATTRIBUTE = "password";

    public final static String RESULT_ATTRIBUTE = "result";
    public final static String SUCCESS_RESULT = "success";
    public final static String FAILURE_RESULT = "failure";

    public final static String LOGIN_WEB_SERVICE_ADDRESS = "http://dandroid.andreirosucojocaru.ro/user_login.php";
    public final static String USER_ID_ATTRIBUTE = "user_id";

    public final static String USER_LIST_WEB_SERVICE_ADDRESS = "http://dandroid.andreirosucojocaru.ro/user_list.php";
    public final static String SENDER_ID_ATTRIBUTE = "sender_id";
    public final static String SENDER_USERNAME_ATTRIBUTE = "sender_username";
    public final static String RECIPIENT_ID_ATTRIBUTE = "recipient_id";
    public final static String RECIPIENT_USERNAME_ATTRIBUTE = "recipient_username";

    public final static String MESSAGE_LIST_WEB_SERVICE_ADDRESS = "http://dandroid.andreirosucojocaru.ro/message_list.php";
    public final static String SENDER_ATTRIBUTE = "sender";
    public final static String RECIPIENT_ATTRIBUTE = "recipient";
    public final static String MESSAGE_ID_ATTRIBUTE = "message_id";
    public final static String STATUS_ATTRIBUTE = "status";
    public final static String READ = "Y";
    public final static String UNREAD = "N";

    public final static String WRITE_MESSAGE_WEB_SERVICE_ADDRESS = "http://dandroid.andreirosucojocaru.ro/write_message.php";
    public final static String SUBJECT_ATTRIBUTE = "subject";
    public final static String CONTENT_ATTRIBUTE = "content";

    public final static String READ_MESSAGE_WEB_SERVICE_ADDRESS = "http://dandroid.andreirosucojocaru.ro/read_message.php";
    public final static String TIMESTAMP_ATTRIBUTE = "timestamp";

    public final static String PROFILE_UPDATE_WEB_SERVICE_ADDRESS = "http://dandroid.andreirosucojocaru.ro/profile_update.php";
}
