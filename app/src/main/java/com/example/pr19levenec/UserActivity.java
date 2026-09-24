package com.example.pr19levenec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserActivity extends AppCompatActivity {

    EditText nameBox;
    EditText yearBox;
    EditText EmailBox;
    EditText CityBox;
    EditText PhoneNumberBox;
    Button delButton;
    Button saveButton;

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        nameBox = findViewById(R.id.name);
        yearBox = findViewById(R.id.year);
        EmailBox = findViewById(R.id.email);
        CityBox = findViewById(R.id.city);
        PhoneNumberBox = findViewById(R.id.phone_number);
        delButton = findViewById(R.id.deleteButton);
        saveButton = findViewById(R.id.saveButton);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
        if (userId > 0) {
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " + DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});

            if (userCursor.moveToFirst()) {
                // Динамически получаем индексы столбцов по их именам из DatabaseHelper
                int nameIdx = userCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME); // Укажите ваши константы
                int yearIdx = userCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_YEAR);
                int emailIdx = userCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL);
                int phoneIdx = userCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE_NUMBER);
                int cityIdx = userCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CITY);


                // Безопасно заполняем поля
                nameBox.setText(userCursor.getString(nameIdx));
                yearBox.setText(String.valueOf(userCursor.getInt(yearIdx)));
                EmailBox.setText(userCursor.getString(emailIdx));
                PhoneNumberBox.setText(userCursor.getString(phoneIdx));
                CityBox.setText(userCursor.getString(cityIdx));
            }
        } else {
            delButton.setVisibility(View.GONE);
        }
    }

    public void save(View view){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, nameBox.getText().toString());
        cv.put(DatabaseHelper.COLUMN_YEAR, Integer.parseInt(yearBox.getText().toString()));

        // Добавление новых полей
        cv.put(DatabaseHelper.COLUMN_EMAIL, EmailBox.getText().toString());
        cv.put(DatabaseHelper.COLUMN_PHONE_NUMBER, PhoneNumberBox.getText().toString());
        cv.put(DatabaseHelper.COLUMN_CITY, CityBox.getText().toString());

        if (userId > 0) {
            db.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + userId, null);
        } else {
            db.insert(DatabaseHelper.TABLE, null, cv);
        }
        goHome();
    }
    public void delete(View view){
        db.delete(DatabaseHelper.TABLE, "_id = ?", new String[]{String.valueOf(userId)});
        goHome();
    }
    private void goHome(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
