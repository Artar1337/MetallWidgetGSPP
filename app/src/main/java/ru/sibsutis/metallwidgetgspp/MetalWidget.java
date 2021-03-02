package ru.sibsutis.metallwidgetgspp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MetalWidget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager
            appWidgetManager, int[] appWidgetIds) {
        //Создаем новый RemoteViews
        RemoteViews remoteViews = new
                RemoteViews(context.getPackageName(), R.layout.widget);
        //Подготавливаем Intent для Broadcast
        Intent active = new Intent(context, MetalWidget.class);
        active.setAction("Ivan"); // устанавливаем действие
        active.putExtra("msg", "Hello"); // добавляем параметр
        //создаем наше событие
        PendingIntent actionPendingIntent =
                PendingIntent.getBroadcast(context, 0, active, 0);
        //регистрируем наше событие
        remoteViews.setOnClickPendingIntent(R.id.Header,
                actionPendingIntent);
        //обновляем виджет
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String m;
        final String action = intent.getAction();

        if (action=="Ivan") { // если поймали наше событие
            m = intent.getStringExtra("msg");
// Выводим сообщение его в виде всплавающей подсказки
            Toast.makeText(context, m, Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent); // вызываем родительский метод
    }
}

