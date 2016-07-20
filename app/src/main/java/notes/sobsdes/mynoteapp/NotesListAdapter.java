package notes.sobsdes.mynoteapp;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NotesListAdapter extends BaseAdapter implements
        OnClickListener {


    final String LOG_TAG = "NoteLog";
    private Activity activity;
    private String[] data;
    private ArrayList<Note> rData = new ArrayList<Note>();


    private static LayoutInflater inflater = null;
    private Context mContext;


    public NotesListAdapter(Activity a, ArrayList<Note> rD,
                            Context context) {
        this.mContext = context;
        activity = a;
        rData = rD;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    public int getCount() {

        return rData.size();

    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onClick(View v) {

    }

    /*********
     * Create a holder Class to contain inflated xml file elements
     *********/
    public static class ViewHolder {

        public TextView text;
        public TextView text1;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {
            //****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.list_view_row, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.text = (TextView) vi.findViewById(R.id.text);
            holder.text1 = (TextView) vi.findViewById(R.id.text1);


            /************ Set holder with LayoutInflater ************/
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }


        Note item = rData.get(position);
        //Log.d(LOG_TAG, item.toString());
        Context context = parent.getContext();


        holder.text.setText(item.getNote_text());


        /******** Set Item Click Listner for LayoutInflater for each row ***********/
        vi.setOnClickListener(new OnItemClickListener(position, item));
        return vi;
    }

    private class OnItemClickListener implements OnClickListener {
        private int mPosition;
        private Note n;

        OnItemClickListener(int position, Note note) {
            mPosition = position;
            n = note;
        }
        @Override
        public void onClick(View arg0) {
            Intent myIntent = new Intent(mContext, CreateNoteActivity.class);
            int flag =1;
            myIntent.putExtra("flag_place", String.valueOf(flag));
            myIntent.putExtra("note_text",n.getNote_text());
            myIntent.putExtra("note_date",n.getNote_date());
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(myIntent);
        }
    }


}
