package vortex.solutions.employers.Presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import vortex.solutions.employers.Model.User;

public class ManageDb {

    //constants
    private static final String TAG = "ManageDb";
    private static final String EMPLOYERS = "employers";
    private static final String NOTHING = "nothing";

    //vars
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void createUser(String names, String lastnames,
                           String typeId, String id, String num1,
                           String num2, String num3, String email,
                           String salary) {

        if (num1.equals("")) num1 = NOTHING;
        if (num2.equals("")) num2 = NOTHING;
        if (num3.equals("")) num3 = NOTHING;
        if (email.equals("")) email = NOTHING;
        
        db.collection(EMPLOYERS)
                .document(String.valueOf(id))
                .set(new User(names, lastnames, typeId, id, num1, num2, num3, email, salary,
                        getTimestamp(), getTimestamp()))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: Registrado");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onSuccess: No Registrado");
                        e.printStackTrace();
                    }
                });

    }

    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("America/Bogota"));
        return sdf.format(new Date());
    }

}
