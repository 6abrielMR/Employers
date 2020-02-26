package vortex.solutions.employers.Presenter;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import vortex.solutions.employers.View.Activity.Login;

public class ManageTasks {

    //constants
    private static final String TAG = "ManageTasks";
    private static boolean state = false;

    public static class createUser extends AsyncTask<Object, Void, ArrayList<Object>> {

        @Override
        protected ArrayList<Object> doInBackground(Object... objects) {

            ArrayList<Object> response = new ArrayList<Object>();

            ((FirebaseAuth) objects[1]).createUserWithEmailAndPassword(
                    (String) objects[2],
                    (String) objects[3])
                    .addOnCompleteListener((Login) objects[0], new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail: Success");
                            state = true;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "createUserWithEmail: Failure");
                            state = false;
                        }
                    });

            response.add(state);
            response.add(objects[0]);

            return response;
        }

        @Override
        protected void onPostExecute(ArrayList<Object> response) {
            if ((boolean) response.get(0))
                ((Login) response.get(1)).SuccesMessagge();
            else
                ((Login) response.get(1)).FailureMessagge();
        }
    }

}
