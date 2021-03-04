package ru.sibsutis.metallwidgetgspp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class MetalWidget extends AppWidgetProvider {

    //public static String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";
    public static String VALUE_RECEIVER_1 = "ru.sibsutis.metallwidgetgspp.value1";
    public static String VALUE_RECEIVER_2 = "ru.sibsutis.metallwidgetgspp.value2";
    public static String VALUE_RECEIVER_3 = "ru.sibsutis.metallwidgetgspp.value3";
    public static String VALUE_RECEIVER_4 = "ru.sibsutis.metallwidgetgspp.value4";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);

        /*//этот Intent поменяет металл
        Intent active_1 = new Intent(context, MetalWidget.class);
        active_1.setAction(VALUE_RECEIVER_1);
        Intent active_2 = new Intent(context, MetalWidget.class);
        active_2.setAction(VALUE_RECEIVER_2);
        Intent active_3 = new Intent(context, MetalWidget.class);
        active_3.setAction(VALUE_RECEIVER_3);
        Intent active_4 = new Intent(context, MetalWidget.class);
        active_4.setAction(VALUE_RECEIVER_4);

        //создаем наше событие
        PendingIntent intent_1 = PendingIntent.getBroadcast(context, 1, active_1, 0);
        PendingIntent intent_2 = PendingIntent.getBroadcast(context, 2, active_2, 0);
        PendingIntent intent_3 = PendingIntent.getBroadcast(context, 3, active_3, 0);
        PendingIntent intent_4 = PendingIntent.getBroadcast(context, 4, active_4, 0);
        //регистрируем наше событие
        remoteViews.setOnClickPendingIntent(R.id.radio_1, intent_1);
        remoteViews.setOnClickPendingIntent(R.id.radio_2, intent_2);
        remoteViews.setOnClickPendingIntent(R.id.radio_3, intent_3);
        remoteViews.setOnClickPendingIntent(R.id.radio_4, intent_4);*/

        //обновляем виджет
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    /*@Override
    public void onReceive(Context context, Intent intent) {

        //Ловим наш Broadcast, проверяем и выводим сообщение
        final String action = intent.getAction();
        if (action.equals(VALUE_RECEIVER_1)) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
            remoteViews.setTextViewText(R.id.MetalTextView, context.getString(R.string.goldannotation));
            remoteViews.setImageViewResource(R.id.MetalImage, R.drawable.gold);
        }
        else if(action.equals(VALUE_RECEIVER_2)) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
            remoteViews.setTextViewText(R.id.MetalTextView, context.getString(R.string.silvannotation));
            remoteViews.setImageViewResource(R.id.MetalImage,R.drawable.silver);
            ComponentName componentName = new ComponentName(context, MetalWidget.class);
            AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
        }
        else if(action.equals(VALUE_RECEIVER_3)) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
            remoteViews.setTextViewText(R.id.MetalTextView, context.getString(R.string.platannotation));
            remoteViews.setImageViewResource(R.id.MetalImage,R.drawable.plat);
            ComponentName componentName = new ComponentName(context, MetalWidget.class);
            AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
        }
        else if(action.equals(VALUE_RECEIVER_4)) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
            remoteViews.setTextViewText(R.id.MetalTextView, context.getString(R.string.pallannotation));
            remoteViews.setImageViewResource(R.id.MetalImage,R.drawable.pall);
            ComponentName componentName = new ComponentName(context, MetalWidget.class);
            AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
        }
        super.onReceive(context, intent);
    }*/

}

