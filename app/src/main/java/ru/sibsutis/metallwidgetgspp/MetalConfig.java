package ru.sibsutis.metallwidgetgspp;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class MetalConfig extends Activity {

    int widgetID = AppWidgetManager.INVALID_APPWIDGET_ID;
    Intent resultValue;

    public final static String WIDGET_PREF = "widget_pref";
    public final static String WIDGET_INDEX = "widget_index_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config);


        // извлекаем ID конфигурируемого виджета
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            widgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        // и проверяем его корректность
        if (widgetID == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        // формируем intent ответа
        resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);

        // отрицательный ответ
        setResult(RESULT_CANCELED, resultValue);
    }

    public void onUpdateClick(View v) {
        int selected = ((RadioGroup) findViewById(R.id.rGroup))
                .getCheckedRadioButtonId();
        int index;
        switch (selected) {
            case R.id.radio_1:
                index = 1;
                break;
            case R.id.radio_2:
                index = 2;
                break;
            case R.id.radio_3:
                index = 3;
                break;
            case R.id.radio_4:
                index = 4;
                break;
            default:
                index = 1;
                break;
        }

        // Записываем значения с экрана в Preferences
        SharedPreferences sp = getSharedPreferences(WIDGET_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(WIDGET_INDEX + widgetID, index);
        editor.commit();
        //Фикс неустанавливающегося события кнопки
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        MetalWidget.updateWidget(this, appWidgetManager, sp, widgetID);
        // положительный ответ
        setResult(RESULT_OK, resultValue);
        finish();
    }

}
