package ru.sibsutis.metallwidgetgspp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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

    private void tableUpdate()
    {
        float  price [] = {1.0f,2.0f,3.0f,4.0f};

        TableLayout table = findViewById(R.id.MainTable);
        table.setShrinkAllColumns(true);
        table.setStretchAllColumns(true);

        String value = getString(R.string.value);

        addLine(table,getString(R.string.goldannotation),price[0]+" "+value);
        addLine(table,getString(R.string.silvannotation),price[1]+" "+value);
        addLine(table,getString(R.string.platannotation),price[2]+" "+value);
        addLine(table,getString(R.string.pallannotation),price[3]+" "+value);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableUpdate();
    }
}