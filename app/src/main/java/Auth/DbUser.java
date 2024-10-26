package Auth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbUser extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDatabase.db";
    private static final String TABLE_NAME = "users";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "username";
    private static final String COL_3 = "password";
    private static final String COL_4 = "role";

    public DbUser(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2 + " TEXT, "
                + COL_3 + " TEXT, "
                + COL_4 + "TEXT )";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Thêm tài khoản mới
    public boolean insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, user.getUsername());
        contentValues.put(COL_3, user.getPassword());
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public List<User> getAllUsers(){
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c =db.rawQuery("SELECT * FROM "+ TABLE_NAME,null);
        if(c.moveToFirst()){
            do {
            String username = c.getString(1);
            String role = c.getString(3);
            userList.add(new User(username,role));
        }while (c.moveToNext());
        }
        c.close();
        db.close();
        return userList;
    }

    // Kiểm tra tài khoản đã tồn tại chưa
    public boolean checkUserExists(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_2 + "=?", new String[]{user.getUsername()});
        boolean result= c.getCount() > 0;
        c.close();
        db.close();
        return result;
    }
    public boolean checkUserLogin(User user){
        SQLiteDatabase db =this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+ " WHERE "+COL_2+"=?AND "+COL_3+"=?",new String[]{user.getUsername(),user.getPassword()});
        boolean result= c.getCount() > 0;
        c.close();
        db.close();
        return result;
    }


}
