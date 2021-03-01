package ru.sibsutis.metallwidgetgspp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private class ParseTask extends AsyncTask<Void, Void, String []> {

        @Override
        protected String [] doInBackground(Void... params) {
            String result [] = {" "," "," "," "};
            String url = "https://www.cbr.ru/hd_base/metall/metall_base_new/";
            Document doc = null;
            try {
                doc = Jsoup.connect(url).get();
                String title = doc.title();
                result[0]=title.substring(0,50);
                Log.wtf(" ",result[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
        @Override
        protected void onPostExecute(String price [])
        {
            new ParseTask().execute();;

            TableLayout table = findViewById(R.id.MainTable);
            table.setShrinkAllColumns(true);
            table.setStretchAllColumns(true);

            String value = getString(R.string.value);

            addLine(table,getString(R.string.goldannotation),price[0]+" "+value);
            addLine(table,getString(R.string.silvannotation),price[1]+" "+value);
            addLine(table,getString(R.string.platannotation),price[2]+" "+value);
            addLine(table,getString(R.string.pallannotation),price[3]+" "+value);
        }
    }

        private void addLine(TableLayout tableLayout,String prefix, String postfix)
    {
        TableRow row = new TableRow(this);
        TextView textview = new TextView(this);
        textview.setTextColor(getResources().getColor(R.color.black));
        textview.setBackgroundColor(getResources().getColor(R.color.tablebackground));
        textview.setText(prefix);
        row.addView(textview);

        TextView textview1 = new TextView(this);
        textview1.setTextColor(getResources().getColor(R.color.black));
        textview1.setBackgroundColor(getResources().getColor(R.color.tablebackground));
        textview1.setText(postfix);
        row.addView(textview1);

        tableLayout.addView(row);
    }

    private String [] valuesUpdate(){
        ParseTask task = new ParseTask();
        return task.doInBackground();
    }

    private void tableUpdate()
    {
        new ParseTask().execute();;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableUpdate();
    }
}