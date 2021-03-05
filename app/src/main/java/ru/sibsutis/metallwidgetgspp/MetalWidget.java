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
import java.lang.reflect.WildcardType;

public class MetalWidget extends AppWidgetProvider {

    //public static String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";
    final public static String VALUE_RECEIVER_1 = "ru.sibsutis.metallwidgetgspp.value1";
    final public static String APP_RECEIVER = "ru.sibsutis.metallwidgetgspp.apprec";
    final public static String VALUE_RECEIVER_2 = "ru.sibsutis.metallwidgetgspp.value2";
    final public static String VALUE_RECEIVER_3 = "ru.sibsutis.metallwidgetgspp.value3";
    final public static String VALUE_RECEIVER_4 = "ru.sibsutis.metallwidgetgspp.value4";

    final public static String VALUE_OF_INDEX = "ru.sibsutis.metallwidgetgspp.indexvalue";

    public static String values[] = {"?", "?", "?", "?", "?"};

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
                    result[i] = "0";
            }
            return result;
        }

        @Override
        protected void onPostExecute(String price[]) {

        }
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        // Удаляем Preferences
        SharedPreferences.Editor editor = context.getSharedPreferences(
                MetalConfig.WIDGET_PREF, Context.MODE_PRIVATE).edit();
        for (int widgetID : appWidgetIds) {
            editor.remove(MetalConfig.WIDGET_INDEX + widgetID);
        }
        editor.commit();
    }

    static void updateWidget(Context context, AppWidgetManager appWidgetManager,
                             SharedPreferences sp, int widgetID) {

        int widgetIndex = sp.getInt(MetalConfig.WIDGET_INDEX + widgetID, 0);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);

        Intent active;
        if (widgetIndex == 1) {
            active = new Intent(context, MetalWidget.class);
            active.setAction(VALUE_RECEIVER_1);
        } else if (widgetIndex == 2) {
            active = new Intent(context, MetalWidget.class);
            active.setAction(VALUE_RECEIVER_2);
        } else if (widgetIndex == 3) {
            active = new Intent(context, MetalWidget.class);
            active.setAction(VALUE_RECEIVER_3);
        } else if (widgetIndex == 4) {
            active = new Intent(context, MetalWidget.class);
            active.setAction(VALUE_RECEIVER_4);
        } else {
            active = new Intent(context, MainActivity.class);
            active.setAction(APP_RECEIVER);
        }

        active.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
        active.putExtra(VALUE_OF_INDEX, widgetIndex);

        //этот Intent отправил в приложение
        Intent main_active = new Intent(context, MainActivity.class);
        main_active.setAction(APP_RECEIVER);

        //создаем событие
        PendingIntent intent = PendingIntent.
                getBroadcast(context, 1, active, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent main_intent = PendingIntent.
                getActivity(context, 0, main_active, PendingIntent.FLAG_UPDATE_CURRENT);
        //регистрируем событие
        remoteViews.setOnClickPendingIntent(R.id.buttonUpdate, intent);
        remoteViews.setOnClickPendingIntent(R.id.MetalImage, main_intent);

        //обновляем виджет
        appWidgetManager.updateAppWidget(widgetID, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        new MetalWidget.ParseTask().execute();

        super.onUpdate(context, appWidgetManager, appWidgetIds);

        SharedPreferences sp = context.getSharedPreferences(
                MetalConfig.WIDGET_PREF, Context.MODE_PRIVATE);
        for (int id : appWidgetIds) {
            updateWidget(context, appWidgetManager, sp, id);
        }

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        final String action = intent.getAction();
        int ID = -1;
        if (intent.getExtras() != null) {
            ID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
        }

        if (action.equals(VALUE_RECEIVER_1)) {
            new MetalWidget.ParseTask().execute();
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
            remoteViews.setTextViewText(R.id.MetalTextView, context.getString(R.string.goldannotation));
            remoteViews.setImageViewResource(R.id.MetalImage, R.drawable.gold);
            remoteViews.setTextViewText(R.id.ValueTextView, values[1] + " " + context.getString(R.string.value));
            AppWidgetManager.getInstance(context).updateAppWidget(ID, remoteViews);
        } else if (action.equals(VALUE_RECEIVER_2)) {
            new MetalWidget.ParseTask().execute();
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
            remoteViews.setTextViewText(R.id.MetalTextView, context.getString(R.string.silvannotation));
            remoteViews.setImageViewResource(R.id.MetalImage, R.drawable.silver);
            remoteViews.setTextViewText(R.id.ValueTextView, values[2] + " " + context.getString(R.string.value));
            AppWidgetManager.getInstance(context).updateAppWidget(ID, remoteViews);
        } else if (action.equals(VALUE_RECEIVER_3)) {
            new MetalWidget.ParseTask().execute();
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
            remoteViews.setTextViewText(R.id.MetalTextView, context.getString(R.string.platannotation));
            remoteViews.setImageViewResource(R.id.MetalImage, R.drawable.plat);
            remoteViews.setTextViewText(R.id.ValueTextView, values[3] + " " + context.getString(R.string.value));
            AppWidgetManager.getInstance(context).updateAppWidget(ID, remoteViews);
        } else if (action.equals(VALUE_RECEIVER_4)) {
            new MetalWidget.ParseTask().execute();
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
            remoteViews.setTextViewText(R.id.MetalTextView, context.getString(R.string.pallannotation));
            remoteViews.setImageViewResource(R.id.MetalImage, R.drawable.pall);
            remoteViews.setTextViewText(R.id.ValueTextView, values[4] + " " + context.getString(R.string.value));
            AppWidgetManager.getInstance(context).updateAppWidget(ID, remoteViews);
        }
        super.onReceive(context, intent);
    }

}

