package database;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import users.User;

public class FirebaseHelper
{
    private Context context;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://smssoftware-5c2d1.firebaseio.com/users");
    public FirebaseHelper(Context context){
        this.context = context;
    }

    /**
     *
     * @param username
     * @param email
     * @param password
     */
    public void addUser(String username, String email, String password) {
        User user = new User(username, email, password);
        databaseReference.child("users").child(username).setValue(user);
    }
}
