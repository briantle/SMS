package database;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
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
     * @param userId
     * @param username
     * @param email
     * @param password
     */
    public void addUser(String userId, String username, String email, String password) {
        User user = new User(userId, username, email, password);
        databaseReference.child(user.getUserId()).setValue(user);
    }

    /**
     *
     * @param email
     * @param dataSnapshot
     * @return
     */
    public boolean doesEmailExist(String email, DataSnapshot dataSnapshot)
    {
        // Search through the database
        for (DataSnapshot snapshot: dataSnapshot.getChildren())
        {
            // Get the current user in the database
            User databaseUser = snapshot.getValue(User.class);
            // A user has already registered with that email
            if (databaseUser.getEmail().equals(email)) {
                return true;
            }
        }
        // Nobody has registered with this email
        return false;
    }
    public boolean doesUserNameExist(String username, DataSnapshot dataSnapshot)
    {
        // Search through the database
        for (DataSnapshot snapshot: dataSnapshot.getChildren())
        {
            // Get the current user in the database
            User databaseUser = snapshot.getValue(User.class);
            // A user has already registered with this username
            if (databaseUser.getUsername().equals(username)) {
                return true;
            }
        }
        // Nobody has registered with this username
        return false;
    }
}
