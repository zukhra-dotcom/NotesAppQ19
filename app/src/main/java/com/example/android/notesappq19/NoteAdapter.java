package com.example.android.notesappq19;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapter extends FirestoreRecyclerAdapter<Note, NoteAdapter.NoteViewHolder> {

    Context context;

    public NoteAdapter(FirestoreRecyclerOptions<Note> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(NoteViewHolder holder, int position, Note note) {
        holder.noteTitleView.setText(note.title);
        holder.noteContentView.setText(note.content);
        holder.noteDateView.setText(Utility.dateToString(note.timestamp));

        holder.itemView.setOnClickListener((view -> {
            Intent intent = new Intent(context, NoteData.class);
            intent.putExtra("title", note.title);
            intent.putExtra("content", note.content);
            intent.putExtra("date", note.timestamp);

            String docID = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docID", docID);
            context.startActivity(intent);
        }));
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_note_element, parent, false);
        return new NoteViewHolder(view);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{

        TextView noteTitleView, noteContentView, noteDateView;

        public NoteViewHolder(View itemView) {
            super(itemView);
            noteTitleView = itemView.findViewById(R.id.note_title_view);
            noteContentView = itemView.findViewById(R.id.note_content_view);
            noteDateView = itemView.findViewById(R.id.note_date_view);
        }
    }
}
