package com.tiooooo.mynoteapps.ui.insert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.tiooooo.mynoteapps.R;
import com.tiooooo.mynoteapps.database.Note;
import com.tiooooo.mynoteapps.ui.ViewModelFactory;

public class NoteAddUpdateActivity extends AppCompatActivity {
    private EditText edtTitle;
    private EditText edtDescription;

    public static final String EXTRA_NOTE = "extra_note";
    public static final String EXTRA_POSITION = "extra_position";

    private boolean isEdit = false;
    public static final int REQUEST_ADD = 100;
    public static final int RESULT_ADD = 101;
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;

    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;

    private Note note;
    private int position;

    private NoteAddUpdateViewModel noteAddUpdateViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_add_update);

        edtTitle = findViewById(R.id.edt_title);
        edtDescription = findViewById(R.id.edt_description);;
        Button btnSubmit = findViewById(R.id.btn_submit);

        noteAddUpdateViewModel = obtainViewModel(NoteAddUpdateActivity.this);
    }

    @NonNull
    private NoteAddUpdateViewModel obtainViewModel(NoteAddUpdateActivity noteAddUpdateActivity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(noteAddUpdateActivity.getApplication());

        return ViewModelProviders.of(noteAddUpdateActivity,factory).get(NoteAddUpdateViewModel.class);
    }


}