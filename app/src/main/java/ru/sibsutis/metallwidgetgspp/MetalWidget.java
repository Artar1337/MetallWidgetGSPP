package ru.sibsutis.metallwidgetgspp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MetalWidget extends AppWidgetProvider {

    //public static String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";
    public static String VALUE_RECEIVER_1 = "ru.sibsutis.metallwidgetgspp.value1";
    public static String APP_RECEIVER = "ru.sibsutis.metallwidgetgspp.apprec";
    public static String VALUE_RECEIVER_2 = "ru.sibsutis.metallwidgetgspp.value2";
    public static String VALUE_RECEIVER_3 = "ru.sibsutis.metallwidgetgspp.value3";
    public static String VALUE_RECEIVER_4 = "ru.sibsutis.metallwidgetgspp.value4";

    public static String values [] = {"?","?","?","?","?"};

    private class ParseTask extends AsyncTask<Void, Void, String []> {

        @Override
        protected String [] doInBackground(Void... params) {
            // дата и 4 цены
            String result [] = {" "," "," "," "," "};
            String url = "https://www.cbr.ru/hd_base/metall/metall_base_new/";
            Document doc = null;
            try {
                doc = Jsoup.connect(url).get();
                //нужная информация находится в body->div.table->table.data->строка №2
                Elements title = doc.body().
                        select("table.data").first()
                        .select("tr").get(1).select("td");

                for(int i=0;i<5;i++)
                {
                    result[i]=title.get(i).text();
                    values[i]=result[i];
                }

            } catch (IOException e) {
                e.printStackTrace();
                for(int i=0;i<5;i++)
                    result[i]="?";
            }
            return result;
        }
        @Override
        protected void onPostExecute(String price [])
        {

        }
    }

    @Override
    public void onEnabled(Context context)
    {
        new MetalWidget.ParseTask().execute();
        Intent active_1 = new Intent(context, MetalWidget.class);
        active_1.setAction(VALUE_RECEIVER_1);
        onReceive(context,active_1);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        new MetalWidget.ParseTask().execute();

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
        //этот Intent поменяет металл
        Intent active_1 = new Intent(context, MetalWidget.class);
        active_1.setAction(VALUE_RECEIVER_1);
        Intent active_2 = new Intent(context, MetalWidget.class);
        active_2.setAction(VALUE_RECEIVER_2);
        Intent active_3 = new Intent(context, MetalWidget.class);
        active_3.setAction(VALUE_RECEIVER_3);
        Intent active_4 = new Intent(context, MetalWidget.class);
        active_4.setAction(VALUE_RECEIVER_4);
        //этот Intent отправил в приложение
        Intent main_active = new Intent(context, MainActivity.class);
        main_active.setAction(APP_RECEIVER);

        //создаем наше событие
        PendingIntent intent_1 = PendingIntent.getBroadcast(context, 1, active_1, 0);
        PendingIntent intent_2 = PendingIntent.getBroadcast(context, 2, active_2, 0);
        PendingIntent intent_3 = PendingIntent.getBroadcast(context, 3, active_3, 0);
        PendingIntent intent_4 = PendingIntent.getBroadcast(context, 4, active_4, 0);
        PendingIntent main_intent = PendingIntent.getActivity(context, 0, main_active, 0);
        //регистрируем наше событие
        remoteViews.setOnClickPendingIntent(R.id.radio_1, intent_1);
        remoteViews.setOnClickPendingIntent(R.id.radio_2, intent_2);
        remoteViews.setOnClickPendingIntent(R.id.radio_3, intent_3);
        remoteViews.setOnClickPendingIntent(R.id.radio_4, intent_4);
        remoteViews.setOnClickPendingIntent(R.id.MetalImage, main_intent);

        //обновляем виджет
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        final String action = intent.getAction();
        if (action.equals(VALUE_RECEIVER_1)) {
            new MetalWidget.ParseTask().execute();
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
            remoteViews.setTextViewText(R.id.MetalTextView, context.getString(R.string.goldannotation));
            remoteViews.setImageViewResource(R.id.MetalImage, R.drawable.gold);
            remoteViews.setTextViewText(R.id.ValueTextView,values[1]+" "+context.getString(R.string.value));
            ComponentName componentName = new ComponentName(context, MetalWidget.class);
            AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
        }
        else if(action.equals(VALUE_RECEIVER_2)) {
            new MetalWidget.ParseTask().execute();
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
            remoteViews.setTextViewText(R.id.MetalTextView, context.getString(R.string.silvannotation));
            remoteViews.setImageViewResource(R.id.MetalImage,R.drawable.silver);
            remoteViews.setTextViewText(R.id.ValueTextView,values[2]+" "+context.getString(R.string.value));
            ComponentName componentName = new ComponentName(context, MetalWidget.class);
            AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
        }
        else if(action.equals(VALUE_RECEIVER_3)) {
            new MetalWidget.ParseTask().execute();
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
            remoteViews.setTextViewText(R.id.MetalTextView, context.getString(R.string.platannotation));
            remoteViews.setImageViewResource(R.id.MetalImage,R.drawable.plat);
            remoteViews.setTextViewText(R.id.ValueTextView,values[3]+" "+context.getString(R.string.value));
            ComponentName componentName = new ComponentName(context, MetalWidget.class);
            AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
        }
        else if(action.equals(VALUE_RECEIVER_4)) {
            new MetalWidget.ParseTask().execute();
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
            remoteViews.setTextViewText(R.id.MetalTextView, context.getString(R.string.pallannotation));
            remoteViews.setImageViewResource(R.id.MetalImage,R.drawable.pall);
            remoteViews.setTextViewText(R.id.ValueTextView,values[4]+" "+context.getString(R.string.value));
            ComponentName componentName = new ComponentName(context, MetalWidget.class);
            AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
        }
        super.onReceive(context, intent);
    }

}

