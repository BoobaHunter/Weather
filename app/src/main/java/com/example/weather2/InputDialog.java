package com.example.weather2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.widget.EditText;

/**
 * Диалог ввода
 */
public class InputDialog {

    //введеный текст
    private  String mText;
    //родитель
    private Context mParent;
    //заголовок
    private String mTitle;
    //представитель
    private DialogDelegate mDelegate = null;

    /**
     * Конструктор
     * @param parent -родитель
     * @param title -зоголовок
     */
    InputDialog(Context parent, String title)
    {
        mParent= parent;
        mTitle= title;
    }

    /**
     * Получить текст
     * @return текст
     */
    String getText()
    { return mText;}


    /**
     * установить делегата
     * @param delegate делегат
     */
    void setDelegate(DialogDelegate delegate)
    {mDelegate=delegate; }

    void show()
    {
        //вызовем строителя диалога
        AlertDialog.Builder builder = new AlertDialog.Builder(mParent);
        //установим надпись
        builder.setTitle(mTitle);
        //создадим текствовое поле
        final EditText input = new EditText(mParent);
        //установим режим ввода
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        //установим текстовое поле
        builder.setView(input);
        //создадим обработчик на "ок"
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //записываем текст
                mText= input.getText().toString();
                //скрываем диалог
                dialog.dismiss();
                //сообщим делегату
                if(mDelegate !=null)
                    mDelegate.onConfirm();
            }
        });
        //создаем обработчик на отмену
        builder.setNegativeButton("Calcel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //скрываем диалог
                dialog.cancel();
            }
        });
        //покажем диалог
        builder.show();
    }

}


