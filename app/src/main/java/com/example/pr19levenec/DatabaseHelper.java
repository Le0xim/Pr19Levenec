package com.example.pr19levenec;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userstore.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "users"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_CITY = "city";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE users ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_YEAR + " INTEGER, " // Добавлена запятая
                + COLUMN_EMAIL + " TEXT, "  // Добавлена запятая
                + COLUMN_PHONE_NUMBER + " TEXT, " // Добавлен тип данных и запятая
                + COLUMN_CITY + " TEXT)");

        // добавление начальных данных
        // добавление начальных данных
        db.execSQL("INSERT INTO " + TABLE + " ("
                + COLUMN_NAME + ", "
                + COLUMN_YEAR + ", "
                + COLUMN_EMAIL + ", "
                + COLUMN_PHONE_NUMBER + ", "
                + COLUMN_CITY + ") "
                + "VALUES ('Том Смит', 1981, 'tom@email.com', '+79991112233', 'Москва');");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
}

