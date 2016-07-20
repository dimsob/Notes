package notes.sobsdes.mynoteapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.textservice.TextInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    NotesListAdapter adapter;
    ListView list ;


    NoteDatabase db;
    final String LOG_TAG = "NotesLog";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();





        adapter = new NotesListAdapter(MainActivity.this, getData(), MainActivity.this.getApplicationContext());
        ListView lvNotes = (ListView) findViewById(R.id.lvNotes);
        lvNotes.setAdapter(adapter);


        getSupportActionBar().setTitle("Заметки (" + String.valueOf(adapter.getCount()) + ")");
        getSupportActionBar().setSubtitle("крутое приложение");

    }

    public ArrayList<Note> getData() {

        NoteDatabase db = new NoteDatabase(MainActivity.this.getApplicationContext());
        final ArrayList<Note> stringItems = new ArrayList<Note>();

        ArrayList<Note> pr = (ArrayList<Note>) db.getAllNotes();

        for (Note p : pr) {
            stringItems.add(p);
        }

        return stringItems;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about) {
            Intent it = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(it);
        }
        if (id == R.id.createNote) {
            Intent it = new Intent(MainActivity.this, CreateNoteActivity.class);
            int flag =0;
            getIntent().putExtra("flag_place", String.valueOf(flag));
            startActivity(it);
        }
        if (id == R.id.showMap) {
            Intent it = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(it);
        }
        return super.onOptionsItemSelected(item);
    }
}
