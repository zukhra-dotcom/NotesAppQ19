package com.example.android.notesappq19;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class Utility {

    static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    static CollectionReference getCollectionReference(){
        return FirebaseFirestore.getInstance().collection("notes");
    }

    static String dateToString(Timestamp timestamp){
       return new SimpleDateFormat("dd/MM/yyyy").format(timestamp.toDate());
    }
}
