package com.example.notesapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditNoteActivity extends AppCompatActivity {

    private EditText editTitle, editContent;
    private Button btnSave;
    private DatabaseHelper databaseHelper;
    private int noteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        btnSave = findViewById(R.id.btnSave);

        databaseHelper = new DatabaseHelper(this);

        if (getIntent() != null && getIntent().hasExtra("id")) {
            noteId = getIntent().getIntExtra("id", -1);
            editTitle.setText(getIntent().getStringExtra("title"));
            editContent.setText(getIntent().getStringExtra("content"));
        }

        btnSave.setOnClickListener(v -> {
            String title = editTitle.getText().toString().trim();
            String content = editContent.getText().toString().trim();

            if (TextUtils.isEmpty(title)) {
                editTitle.setError("Title required");
                return;
            }

            if (noteId == -1) {
                databaseHelper.addNote(title, content);
                Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show();
            } else {
                databaseHelper.updateNote(noteId, title, content);
                Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }
}