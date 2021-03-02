package ru.sibsutis.metallwidgetgspp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MetalWidget extends AppWidgetProvider {

    public static String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //Создаем новый RemoteViews
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);

        //этот Intent поменяет металл
        Intent active = new Intent(context, MetalWidget.class);
        active.setAction(ACTION_WIDGET_RECEIVER);
        active.putExtra("msg", "Metal changed");

        //этот Intent откроет MainActivity
        Intent MainActive = new Intent(context, MainActivity.class);
        MainActive.setAction(ACTION_WIDGET_RECEIVER);

        //создаем наше событие
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
        PendingIntent MainIntent = PendingIntent.getActivity(context, 1, MainActive, 0);
        //регистрируем наше событие
        remoteViews.setOnClickPendingIntent(R.id.MetalChangeButton, actionPendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.MetalImage,MainIntent);

        //обновляем виджет
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        //Ловим наш Broadcast, проверяем и выводим сообщение
        final String action = intent.getAction();
        if (ACTION_WIDGET_RECEIVER.equals(action)) {
            String msg = "null";
            try {
                msg = intent.getStringExtra("msg");
            } catch (NullPointerException e) {
                Log.e("Error", "msg = null");
            }
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }

}

