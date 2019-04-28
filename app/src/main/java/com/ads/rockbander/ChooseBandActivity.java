package com.ads.rockbander;

import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ads.rockbander.models.Band;
import com.ads.rockbander.models.Piece;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ChooseBandActivity extends AppCompatActivity {

    EditText etBandName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_band);

        etBandName = findViewById(R.id.et_band_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void create_band(final View view) {

        String bandName = etBandName.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference bandDocRef = db.collection("bands").document();

        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Band b = new Band(bandName, new ArrayList<Piece>(), userUid);
        bandDocRef.set(b).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(view.getContext(), "Succssful data send.", Toast.LENGTH_SHORT);
                } else {
                    Toast.makeText(view.getContext(), "Unsuccessful data send.", Toast.LENGTH_SHORT);
                }
            }
        });


    }
}
