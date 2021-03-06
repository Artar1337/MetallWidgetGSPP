package ru.sibsutis.metallwidgetgspp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    // дата и 4 цены
    String values[] = new String[5];
    Animation animationRotateCenter;
    ImageView bUpdate;

    private class ParseTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // дата и 4 цены
            String result[] = {" ", " ", " ", " ", " "};
            String url = "https://www.cbr.ru/hd_base/metall/metall_base_new/";
            Document doc = null;
            try {
                doc = Jsoup.connect(url).get();
                //нужная информация находится в body->div.table->table.data->строка №2
                Elements title = doc.body().
                        select("table.data").first()
                        .select("tr").get(1).select("td");

                for (int i = 0; i < 5; i++) {
                    result[i] = title.get(i).text();
                    values[i] = result[i];
                }

            } catch (IOException e) {
                e.printStackTrace();
                for (int i = 0; i < 5; i++)
                    result[i] = "?";
            }
            return result;
        }

        @Override
        protected void onPostExecute(String price[]) {
            TableLayout table = findViewById(R.id.MainTable);
            TextView header = findViewById(R.id.Header);
            table.removeAllViewsInLayout();
            table.setShrinkAllColumns(true);
            table.setStretchAllColumns(true);

            String value = getString(R.string.value);
            String head = getString(R.string.header) + " " + price[0];

            header.setText(head);
            addLine(table, getString(R.string.goldannotation), price[1] + " " + value);
            addLine(table, getString(R.string.silvannotation), price[2] + " " + value);
            addLine(table, getString(R.string.platannotation), price[3] + " " + value);
            addLine(table, getString(R.string.pallannotation), price[4] + " " + value);
            bUpdate.animate().cancel();
        }
    }

    private void addLine(TableLayout tableLayout, String prefix, String postfix) {
        //создаем строку
        // добавляем название металла
        TableRow row = new TableRow(this);
        TextView textview = new TextView(this);
        textview.setTextColor(getResources().getColor(R.color.black));
        textview.setBackgroundColor(getResources().getColor(R.color.tablebackground));
        textview.setText(prefix);

        row.addView(textview);

        //добавляем цену металла
        TextView textview1 = new TextView(this);
        textview1.setTextColor(getResources().getColor(R.color.black));
        textview1.setBackgroundColor(getResources().getColor(R.color.tablebackground));
        textview1.setText(postfix);
        row.addView(textview1);

        tableLayout.addView(row);
    }

    public void tableUpdate() {
        //выполняем запрос в фоне
        new MainActivity.ParseTask().execute();
        bUpdate.startAnimation(animationRotateCenter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animationRotateCenter = AnimationUtils.loadAnimation(
                this, R.anim.rotate);
        bUpdate = (ImageView) findViewById(R.id.UpdateButton);
        View.OnClickListener operationListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableUpdate();
            }
        };
        bUpdate.setOnClickListener(operationListener);
        tableUpdate();
    }


}