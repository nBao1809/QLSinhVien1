package com.example.qlsinhvien.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    //region
    public static final String DATABASE_NAME = "ql_sinhvien.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TB_USERS = "USERS";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    public static final String PHOTO = "PHOTO";
    public static final String EMAIL = "EMAIL";
    public static final String ROLE = "ROLE";


    public static final String TB_SINHVIEN = "SINHVIEN";
    public static final String MSSV = "MSSV";
    public static final String HOTEN = "HOTEN";
    public static final String NGAYSINH = "NGAYSINH";
    public static final String CCCD = "CCCD";

    public static final String TB_GIANGVIEN = "GIANGVIEN";
    public static final String MA_GIANGVIEN = "MA_GIANGVIEN";
    public static final String KHOA = "KHOA";


    public static final String TB_HOCKY = "HOCKY";
    public static final String MA_HOCKY = "MA_HOCKY";
    public static final String TENHOCKY = "TENHOCKY";
    public static final String NAMHOC = "NAMHOC";

    public static final String TB_LOPSINHVIEN = "LOP_SINHVIEN";
    public static final String ID = "ID";
    public static final String TB_LOPHOCPHAN = "LOPHOCPHAN";
    public static final String MA_LOP = "MA_LOP";
    public static final String TENLOP = "TENLOP";
    public static final String NGAYBATDAU = "NGAYBATDAU";
    public static final String NGAYKETTHUC = "NGAYKETTHUC";

    public static final String TB_DIEM = "DIEM";
    public static final String MA_DIEM = "MADIEM";

    public static final String DIEMSO = "DIEMSO";
    public static final String TB_LOAIDIEM = "LOAIDIEM";
    public static final String MA_LOAIDIEM = "MA_LOAIDIEM";
    public static final String TEN_LOAIDIEM="TEN_LOAIDIEM";
    public static final String TRONGSO = "TRONGSO";


    public static final String TB_MONHOC = "MONHOC";
    public static final String MA_MONHOC = "MA_MONHOC";
    public static final String TENMONHOC = "TENMONHOC";
    public static final String TINCHI = "TINCHI";
    public static final String NGANH = "NGANH";

    public static final String TB_LOPHANHCHINH = "LOPHANHCHINH";
    public static final String MA_LOPHANHCHINH = "MA_LOPHANHCHINH";
    public static final String TENLOPHANHCHINH = "TENLOPHANHCHINH";

    public static final String TB_NGANH = "NGANH";
    public static final String MA_NGANH = "MA_NGANH";
    //endregion
    public static final String CREATE_MONHOC =
            "CREATE TABLE " + TB_MONHOC + " (" + MA_MONHOC +
                    " TEXT PRIMARY KEY , " + TENMONHOC + " TEXT NOT NULL, " + TINCHI +
                    " DOUBLE , " + NGANH + " TEXT REFERENCES " + TB_NGANH + " (" + MA_NGANH + "))";
    public static final String CREATE_NGANH =
            "CREATE TABLE " + TB_NGANH + " (" + MA_NGANH +
                    " TEXT PRIMARY KEY, " + NGANH + " TEXT )";
    public static final String CREATE_LOPHANHCHINH =
            "CREATE TABLE " + TB_LOPHANHCHINH + " (" + MA_LOPHANHCHINH +
                    " TEXT PRIMARY KEY, " + TENLOPHANHCHINH + " TEXT NOT NULL)";
    public static final String CREATE_USERS = "CREATE TABLE " + TB_USERS + " (" + ID + " INTEGER" +
            " PRIMARY KEY AUTOINCREMENT, " + USERNAME + " TEXT , " + PASSWORD + " TEXT, " + PHOTO + " TEXT, " + EMAIL + " TEXT, " + ROLE + " TEXT)";
    public static final String CREATE_GIANGVIEN =
            "CREATE TABLE " + TB_GIANGVIEN + " (" + MA_GIANGVIEN + " TEXT PRIMARY KEY, " + HOTEN +
                    " TEXT NOT NULL, " + CCCD + " TEXT, " + NGAYSINH + "TEXT, " + KHOA +
                    " TEXT, " + ID + " REFERENCES " + TB_USERS + "(" + ID + "))";
    public static final String CREATE_SINHVIEN = "CREATE TABLE " + TB_SINHVIEN + " (" + MSSV +
            " TEXT PRIMARY KEY, " + HOTEN + "TEXT NOT NULL, " + CCCD + " TEXT, " + NGAYSINH + " " +
            " TEXT, " + ID + " REFERENCES " + TB_USERS + "(" + ID + ")," +
            MA_LOPHANHCHINH + " REFERENCES " + TB_LOPHANHCHINH + "(" + MA_LOPHANHCHINH + "), " +
            MA_NGANH + " REFERENCES " + TB_NGANH + "(" + MA_NGANH + "))";
    public static final String CREATE_HOCKY =
            "CREATE TABLE " + TB_HOCKY + " (" + MA_HOCKY + " TEXT PRIMARY KEY, " + TENHOCKY +
                    " TEXT NOT NULL, " + NAMHOC + " TEXT )";
    public static final String CREATE_LOPSINHVIEN =
            "CREATE TABLE " + TB_LOPSINHVIEN + " (" + ID + " TEXT PRIMARY KEY , " +
                    MA_LOP + " REFERENCES " + TB_LOPHOCPHAN + "(" + MA_LOP +
                    "), " + MSSV + " REFERENCES " + TB_SINHVIEN + "(" + MSSV + "), " +
                    MA_HOCKY + " REFERENCES " + TB_HOCKY + "(" + MA_HOCKY + "))";
    public static final String CREATE_LOAIDIEM = "CREATE TABLE " + TB_LOAIDIEM + " (" + MA_LOAIDIEM +
            " TEXT PRIMARY KEY , " + TEN_LOAIDIEM + " TEXT, " + TRONGSO  +
            " REAL )";
    public static final String CREATE_DIEM = "CREATE TABLE " + TB_DIEM + " (" + MA_DIEM  +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + DIEMSO + " REAL, " + MA_LOAIDIEM  +
            " REFERENCES " +TB_LOAIDIEM+"("+MA_LOAIDIEM+"), "
             + ID + " REFERENCES " + TB_LOPSINHVIEN + "(" + ID + "))";
    public static final String CREATE_LOPHOCPHAN =
            "CREATE TABLE " + TB_LOPHOCPHAN + " (" + MA_LOP + " TEXT PRIMARY KEY, " + TENLOP +
                    " TEXT, " + NGAYBATDAU + " TEXT, " + NGAYKETTHUC + " TEXT, " +
                    MA_LOP + " REFERENCES " + TB_LOPHOCPHAN + "(" + MA_LOP +
                    "), " + MSSV + " REFERENCES " + TB_SINHVIEN + "(" + MSSV + "), " +
                    MA_HOCKY + " REFERENCES " + TB_HOCKY + "(" + MA_HOCKY + "))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS);
        db.execSQL(CREATE_LOAIDIEM);
        db.execSQL(CREATE_LOPHANHCHINH);
        db.execSQL(CREATE_NGANH);
        db.execSQL(CREATE_SINHVIEN);
        db.execSQL(CREATE_GIANGVIEN);
        db.execSQL(CREATE_MONHOC);
        db.execSQL(CREATE_LOPHOCPHAN);
        db.execSQL(CREATE_HOCKY);
        db.execSQL(CREATE_LOPSINHVIEN);
        db.execSQL(CREATE_DIEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TB_DIEM);
        db.execSQL("DROP TABLE IF EXISTS " + TB_LOPSINHVIEN);
        db.execSQL("DROP TABLE IF EXISTS " + TB_HOCKY);
        db.execSQL("DROP TABLE IF EXISTS " + TB_LOPHOCPHAN);
        db.execSQL("DROP TABLE IF EXISTS " + TB_MONHOC);
        db.execSQL("DROP TABLE IF EXISTS " + TB_GIANGVIEN);
        db.execSQL("DROP TABLE IF EXISTS " + TB_SINHVIEN);
        db.execSQL("DROP TABLE IF EXISTS " + TB_NGANH);
        db.execSQL("DROP TABLE IF EXISTS " + TB_LOPHANHCHINH);
        db.execSQL("DROP TABLE IF EXISTS " + TB_LOAIDIEM);
        db.execSQL("DROP TABLE IF EXISTS " + TB_USERS);
        onCreate(db);
    }


}