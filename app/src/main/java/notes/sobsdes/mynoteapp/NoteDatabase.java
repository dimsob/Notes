package notes.sobsdes.mynoteapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dimsob on 24.02.16.
 */
public class NoteDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notedb";
    final String LOG_TAG = "NoteDatabase";
    private static final int DATABASE_VERSION = 4;

    public NoteDatabase(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sQuery = "CREATE TABLE NOTES (id integer primary key," + "note text, date text, lat text, longt text);";
        db.execSQL(sQuery);

    }



    public void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("note", note.getNote_text());
        cv.put("date", note.getNote_date());
        cv.put("lat", note.getLat());
        cv.put("longt", note.getLongt());
        db.insert("NOTES", null, cv);
        Log.d("Add note", note.toString());

        db.close();
    }


    public void updateNoteByDate(String date, String new_note_text) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("note", new_note_text);
        //"UPDATE notes set note_text = Новый_ТЕКСТ WHERE date = ДАТА_ЗАМЕТКИ"
        String where = "date=?";
        String[] whereArgs = new String[]{date};
        //whereArgs = "date= МОЯ_ДАТА_ПЕРЕДАВАЕМАЯ_В_ФУНКЦИЮ"
        db.update("notesTable", cv, where, whereArgs);
        Log.d(LOG_TAG, "Note: " + new_note_text.toString());
        db.close();
    }


    public void updateNoteById(int id, String new_note_text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("note", new_note_text);
        String where = "id=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        db.update("notesTable", cv, where, whereArgs);
        Log.d(LOG_TAG, "Note: " + new_note_text.toString());
        db.close();
    }

    public void deleteNoteById(String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "date=?";
        String[] whereArgs = new String[]{date};
        //whereArgs = "date= МОЯ_ДАТА_ПЕРЕДАВАЕМАЯ_В_ФУНКЦИЮ"
        db.delete("NOTES", where, whereArgs);//delete from название_таблицы where date=моя_дата;
        db.close();
    }

    public void deleteAllNotes() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXIST NOTES");
        onCreate(db);
    }

    public List<Note> getAllNotesForTheMap() {
        List<Note> noteList = new ArrayList<Note>();

        String selectQuery = "SELECT * FROM NOTES ORDER BY id DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                Note note = new Note();
                note.setNote_id(Integer.valueOf(cursor.getString(0)));
                note.setNote_text(cursor.getString(1));
                note.setNote_date(cursor.getString(2));
                note.setLat(Double.valueOf(cursor.getString(3)));
                note.setLongt(Double.valueOf(cursor.getString(4)));
                Log.d(LOG_TAG, note.toString());
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        db.close();
        return noteList;
    }

    public List<Note> getAllNotes() {
        List<Note> noteList = new ArrayList<Note>();

        String selectQuery = "SELECT * FROM NOTES ORDER BY id DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                Note note = new Note();
                note.setNote_id(Integer.valueOf(cursor.getString(0)));
                note.setNote_text(cursor.getString(1));
                note.setNote_date(cursor.getString(2));

                Log.d(LOG_TAG, note.toString());
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        db.close();
        return noteList;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST NOTES");
        onCreate(db);
    }
}
