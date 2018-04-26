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
     * @param user
     */
    public void addUser(User user) {
        databaseReference.child(user.getUsername()).setValue(user);
    }

    /**
     *
     * @param email
     * @param dataSnapshot
     * @return
     */
    public boolean doesEmailExist(String email, DataSnapshot dataSnapshot)
    {
        // Iterate through the database
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
}
