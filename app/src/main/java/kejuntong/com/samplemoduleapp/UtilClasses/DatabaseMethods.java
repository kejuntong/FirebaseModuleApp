package kejuntong.com.samplemoduleapp.UtilClasses;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kejuntong.com.samplemoduleapp.Interfaces.DatabaseCallback;
import kejuntong.com.samplemoduleapp.ModelClasses.User;

/**
 * Created by kejuntong on 2018-08-06.
 */

public class DatabaseMethods {

    public static void getUserFromFirebase(FirebaseDatabase database, String userId, final DatabaseCallback callback){
        DatabaseReference myRef = database.getReference("user").child(userId);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null){
                    callback.onDataCallback(dataSnapshot.getValue(User.class));
                } else {
                    callback.onDataCallback(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onDataCallback(null);
            }
        });
    }
}
