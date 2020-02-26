package vortex.solutions.employers.View.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import vortex.solutions.employers.R;

public class FailureMessagge extends DialogFragment {

    //vars
    private Activity currentActivity;
    private String currentText;

    public FailureMessagge(Activity currentActivity, String currentText) {
        this.currentActivity = currentActivity;
        this.currentText = currentText;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
        View v = currentActivity.getLayoutInflater().inflate(R.layout.failure_messagge, null);

        final TextView newText = v.findViewById(R.id.successmessagge_txt);

        newText.setText(currentText);

        builder.setView(v);

        return builder.create();
    }
}
