package com.example.android.notesappq19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.annotations.NonNull;

public class NoteData extends AppCompatActivity {

    TextView pageTitleTxt;
    EditText noteTitleEdit, noteContentEdit, searchTagEdit;
    ImageButton saveNoteBtn, searchTagBtn;
    String title, content, date, docID;
    boolean isEditMode = false;
    Button deleteNoteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_data);

        pageTitleTxt = findViewById(R.id.page_title);
        noteTitleEdit = findViewById(R.id.note_title);
        noteContentEdit = findViewById(R.id.note_content);
        searchTagEdit = findViewById(R.id.search_hashtag);
        saveNoteBtn = findViewById(R.id.save_note_btn);
        deleteNoteBtn = findViewById(R.id.delete_note_btn);
        searchTagBtn = findViewById(R.id.search_btn);

        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        date = getIntent().getStringExtra("date");
        docID = getIntent().getStringExtra("docID");

        if(docID!=null && !docID.isEmpty()){
            isEditMode = true;
        }

        noteTitleEdit.setText(title);
        noteContentEdit.setText(content);

        if(isEditMode){
            pageTitleTxt.setText("Edit your note");
            deleteNoteBtn.setVisibility(View.VISIBLE);
        }

        saveNoteBtn.setOnClickListener((view -> saveNote()));
        deleteNoteBtn.setOnClickListener((view -> deleteNote()));
    }

    void saveNote(){
        String noteTitle = noteTitleEdit.getText().toString();
        String noteContent = noteContentEdit.getText().toString();

        if(noteTitle == null || noteTitle.isEmpty()){
            noteTitleEdit.setError("Please enter the title of note");
            return;
        }
        Note note = new Note();
        note.setTitle(noteTitle);
        note.setContent(noteContent);
        note.setTimestamp(Timestamp.now());

        saveToStorage(note);
    }

    void saveToStorage(Note note){
        DocumentReference documentReference;

        if(isEditMode){
            documentReference = Utility.getCollectionReference().document(docID);
        }else{
            documentReference = Utility.getCollectionReference().document();
        }

        documentReference.set(note).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()){
                    Utility.showToast(NoteData.this, "Note added to the cloud storage");
                    finish();
                }else{
                    Utility.showToast(NoteData.this, "Note is not added to the cloud storage");
                }
            }
        });
    }

    void deleteNote(){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReference().document(docID);
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()){
                    Utility.showToast(NoteData.this, "Note deleted from the cloud storage");
                    finish();
                }else{
                    Utility.showToast(NoteData.this, "Note is not deleted from the cloud storage");
                }
            }
        });
    }
}