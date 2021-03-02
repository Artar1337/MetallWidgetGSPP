package ru.sibsutis.metallwidgetgspp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class MetalWidget extends AppWidgetProvider {

    public static String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";
    public static String MAIN_RECEIVER = "Main";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);

        ComponentName thisWidget = new ComponentName(context, MetalWidget.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        //remoteViews.setTextViewText(R.id.MetalTextView, String.valueOf(MetalIndex));




        //этот Intent поменяет металл
        Intent active = new Intent(context, MetalWidget.class);
        active.setAction(ACTION_WIDGET_RECEIVER);
        active.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

        //этот Intent откроет MainActivity
        Intent MainActive = new Intent(context, MainActivity.class);
        MainActive.setAction(MAIN_RECEIVER);

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
        if (ACTION_WIDGET_RECEIVER.equals(intent.getAction())) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);

            int MetalIndex = Integer.parseInt(intent.getStringExtra("currentMetal"));

            if(MetalIndex==0){
                remoteViews.setTextViewText(R.id.MetalTextView, context.getString(R.string.silvannotation));
                remoteViews.setImageViewResource(R.id.MetalImage,R.drawable.silver);
            }
            else if(MetalIndex==1){
                remoteViews.setTextViewText(R.id.MetalTextView, context.getString(R.string.platannotation));
                remoteViews.setImageViewResource(R.id.MetalImage,R.drawable.plat);
            }
            else if(MetalIndex==2){
                remoteViews.setTextViewText(R.id.MetalTextView, context.getString(R.string.pallannotation));
                remoteViews.setImageViewResource(R.id.MetalImage,R.drawable.pall);
            }
            else if(MetalIndex==3){
                remoteViews.setTextViewText(R.id.MetalTextView, context.getString(R.string.goldannotation));
                remoteViews.setImageViewResource(R.id.MetalImage,R.drawable.gold);
            }
            else MetalIndex=-1;

            MetalIndex++;
            if (MetalIndex>3||MetalIndex<0) MetalIndex=0;

            ComponentName componentName = new ComponentName(context, MetalWidget.class);
            AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
        }
        super.onReceive(context, intent);
    }

}

