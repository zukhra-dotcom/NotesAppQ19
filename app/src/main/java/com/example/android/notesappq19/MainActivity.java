package com.example.android.notesappq19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addBtn;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBtn = findViewById(R.id.adding_new_note);
        recyclerView = findViewById(R.id.main_view);

        addBtn.setOnClickListener((view -> startActivity(new Intent(MainActivity.this, NoteData.class))));
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        Query query = Utility.getCollectionReference().orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(options, this);
        recyclerView.setAdapter(noteAdapter);
    }

    @Override
    public void onStart(){
        super.onStart();
        noteAdapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        noteAdapter.stopListening();
    }

    @Override
    public void onResume(){
        super.onResume();
        noteAdapter.notifyDataSetChanged();
    }

}