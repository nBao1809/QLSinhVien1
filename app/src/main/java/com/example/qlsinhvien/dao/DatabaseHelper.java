package com.example.qlsinhvien.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.qlsinhvien.Activities.LoginActivity;


public class DatabaseHelper extends SQLiteOpenHelper {
    //region tên cột
    public static final String DATABASE_NAME = "ql_sinhvien.db";
    public static final int DATABASE_VERSION = 37;

    public static final String TB_USERS = "USERS";
    public static final String ID = "ID";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String PHOTO = "PHOTO";
    public static final String EMAIL = "EMAIL";
    public static final String ROLE = "ROLE";

    public static final String TB_ROLE = "ROLE";
    public static final String MA_ROLE = "MA_ROLE";
    public static final String TEN_ROLE = "TEN_ROLE";


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


    public static final String TB_LOPSINHVIEN = "LOP_SINHVIEN";
    public static final String MA_LOPSINHVIEN = "MA_LOPSINHVIEN";

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
    public static final String TEN_LOAIDIEM = "TEN_LOAIDIEM";
    public static final String TRONGSO = "TRONGSO";


    public static final String TB_MONHOC = "MONHOC";
    public static final String MA_MONHOC = "MA_MONHOC";
    public static final String TENMONHOC = "TENMONHOC";
    public static final String TINCHI = "TINCHI";


    public static final String TB_LOPHANHCHINH = "LOPHANHCHINH";
    public static final String MA_LOPHANHCHINH = "MA_LOPHANHCHINH";
    public static final String TEN_LOPHANHCHINH = "TEN_LOPHANHCHINH";

    public static final String TB_NGANH = "NGANH";
    public static final String MA_NGANH = "MA_NGANH";
    public static final String TEN_NGANH = "TEN_NGANH";
    //endregion

    //region tạo bảng
    public static final String CREATE_MONHOC = "CREATE TABLE " + TB_MONHOC + " (" +
            MA_MONHOC + " TEXT PRIMARY KEY , " +
            TENMONHOC + " TEXT NOT NULL, " +
            TINCHI + " DOUBLE , " +
            MA_NGANH + " TEXT, " +
            "FOREIGN KEY(" + MA_NGANH + ") REFERENCES " + TB_NGANH + " (" + MA_NGANH + "))";
    public static final String CREATE_NGANH = "CREATE TABLE " + TB_NGANH + " (" +
            MA_NGANH + " TEXT PRIMARY KEY, " +
            TEN_NGANH + " TEXT )";
    public static final String CREATE_LOPHANHCHINH = "CREATE TABLE " + TB_LOPHANHCHINH + " (" +
            MA_LOPHANHCHINH +" TEXT PRIMARY KEY, " +
            TEN_LOPHANHCHINH + " TEXT NOT NULL)";
    public static final String CREATE_USERS = "CREATE TABLE " + TB_USERS + " (" +
            ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USERNAME + " TEXT , " +
            PASSWORD + " TEXT, " +
            PHOTO + " BLOB, " +
            EMAIL + " TEXT, " +
            ROLE + " TEXT, " + "FOREIGN KEY(" + ROLE + ") REFERENCES " + TB_ROLE + "(" + MA_ROLE +
            "))";
    public static final String CREATE_GIANGVIEN = "CREATE TABLE " + TB_GIANGVIEN + " (" +
            MA_GIANGVIEN + " TEXT PRIMARY KEY, " +
            HOTEN + " TEXT NOT NULL, " +
            CCCD + " TEXT, " +
            NGAYSINH + " REAL, " +
            KHOA + " TEXT, " +
            ID + " INTEGER, " +
            "FOREIGN KEY(" + ID + ") REFERENCES " + TB_USERS + "(" + ID + "))";
    public static final String CREATE_SINHVIEN = "CREATE TABLE " + TB_SINHVIEN + " (" +
            MSSV + " TEXT PRIMARY KEY, " +
            HOTEN + " TEXT NOT NULL, " +
            CCCD + " TEXT, " +
            NGAYSINH + " REAL, " +
            ID + " INTEGER, " +
            MA_LOPHANHCHINH + " TEXT, " +
            MA_NGANH + " TEXT, " +
            "FOREIGN KEY(" + ID + ") REFERENCES " + TB_USERS + "(" + ID + "), " +
            "FOREIGN KEY(" + MA_LOPHANHCHINH + ") REFERENCES " + TB_LOPHANHCHINH + "(" + MA_LOPHANHCHINH + "), " +
            "FOREIGN KEY(" + MA_NGANH + ") REFERENCES " + TB_NGANH + "(" + MA_NGANH + "))";
    public static final String CREATE_HOCKY = "CREATE TABLE " + TB_HOCKY + " (" +
            MA_HOCKY + " TEXT PRIMARY KEY, " +
            TENHOCKY + " TEXT NOT NULL)";


    public static final String CREATE_ROLE = "CREATE TABLE " + TB_ROLE + " (" +
            MA_ROLE + " TEXT PRIMARY KEY, " +
            TEN_ROLE + " TEXT NOT NULL )";

    public static final String CREATE_LOPSINHVIEN = "CREATE TABLE " + TB_LOPSINHVIEN + " (" +
            MA_LOPSINHVIEN + " TEXT PRIMARY KEY, " +
            MA_LOP + " TEXT, " +
            MSSV + " TEXT, " +
            MA_HOCKY + " TEXT, " +
            "FOREIGN KEY(" + MA_LOP + ") REFERENCES " + TB_LOPHOCPHAN + "(" + MA_LOP + "), " +
            "FOREIGN KEY(" + MSSV + ") REFERENCES " + TB_SINHVIEN + "(" + MSSV + "), " +
            "FOREIGN KEY(" + MA_HOCKY + ") REFERENCES " + TB_HOCKY + "(" + MA_HOCKY + "))";


    public static final String CREATE_LOAIDIEM = "CREATE TABLE " + TB_LOAIDIEM + " (" +
            MA_LOAIDIEM + " TEXT PRIMARY KEY , " +
            TEN_LOAIDIEM + " TEXT, " +
            TRONGSO + " REAL )";
    public static final String CREATE_DIEM = "CREATE TABLE " + TB_DIEM + " (" +
            MA_DIEM + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DIEMSO + " REAL, " +
            MA_LOAIDIEM + " TEXT, " +
            MA_LOPSINHVIEN + " TEXT, " +
            "FOREIGN KEY(" + MA_LOAIDIEM + ") REFERENCES " + TB_LOAIDIEM + "(" + MA_LOAIDIEM + "), " +
            "FOREIGN KEY(" + MA_LOPSINHVIEN + ") REFERENCES " + TB_LOPSINHVIEN + "(" + MA_LOPSINHVIEN + "))";


    public static final String CREATE_LOPHOCPHAN = "CREATE TABLE " + TB_LOPHOCPHAN + " (" +
            MA_LOP + " TEXT PRIMARY KEY, " +
            TENLOP + " TEXT, " +
            NGAYBATDAU + " REAL, " +
            NGAYKETTHUC + " REAL, " +
            MA_MONHOC + " TEXT, " +
            MA_GIANGVIEN + " TEXT, " +
            "FOREIGN KEY(" + MA_MONHOC + ") REFERENCES " + TB_MONHOC + "(" + MA_MONHOC + "), " +
            "FOREIGN KEY(" + MA_GIANGVIEN + ") REFERENCES " + TB_GIANGVIEN + "(" + MA_GIANGVIEN + "))";
    //endregion

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ROLE);
        db.execSQL(CREATE_NGANH);
        db.execSQL(CREATE_LOPHANHCHINH);
        db.execSQL(CREATE_MONHOC);
        db.execSQL(CREATE_USERS);
        db.execSQL(CREATE_GIANGVIEN);
        db.execSQL(CREATE_SINHVIEN);
        db.execSQL(CREATE_HOCKY);
        db.execSQL(CREATE_LOPSINHVIEN);
        db.execSQL(CREATE_LOAIDIEM);
        db.execSQL(CREATE_DIEM);
        db.execSQL(CREATE_LOPHOCPHAN);
        db.execSQL("INSERT INTO " + TB_ROLE + " (" + MA_ROLE + ", " + TEN_ROLE + ") VALUES ('admin', 'Quản trị viên')," +
                "('mod', 'Chuyên viên')," +
                "('gv', 'Giảng viên')," +
                "('sv', 'Sinh viên')");
        db.execSQL("INSERT INTO " + TB_LOPHANHCHINH + " (" + MA_LOPHANHCHINH + ", " + TEN_LOPHANHCHINH + ") VALUES ('LHC001', 'CS01')");
        db.execSQL("INSERT INTO " + TB_LOPHANHCHINH + " (" + MA_LOPHANHCHINH + ", " + TEN_LOPHANHCHINH + ") VALUES ('LHC002', 'CS02')");
        db.execSQL("INSERT INTO " + TB_LOPHANHCHINH + " (" + MA_LOPHANHCHINH + ", " + TEN_LOPHANHCHINH + ") VALUES ('LHC003', 'IT01')");
        db.execSQL("INSERT INTO " + TB_LOPHANHCHINH + " (" + MA_LOPHANHCHINH + ", " + TEN_LOPHANHCHINH + ") VALUES ('LHC004', 'IT02')");
        db.execSQL("INSERT INTO " + TB_LOPHANHCHINH + " (" + MA_LOPHANHCHINH + ", " + TEN_LOPHANHCHINH + ") VALUES ('LHC005', 'IT03')");

        db.execSQL("INSERT INTO " + TB_NGANH + " (" + MA_NGANH + ", " + TEN_NGANH + ") VALUES ('CNTT', 'Công nghệ thông tin')");
        db.execSQL("INSERT INTO " + TB_NGANH + " (" + MA_NGANH + ", " + TEN_NGANH + ") VALUES ('KT', 'Kinh tế')");
        db.execSQL("INSERT INTO " + TB_NGANH + " (" + MA_NGANH + ", " + TEN_NGANH + ") VALUES ('MT', 'Môi trường')");
        db.execSQL("INSERT INTO " + TB_NGANH + " (" + MA_NGANH + ", " + TEN_NGANH + ") VALUES ('QTKD', 'Quản trị kinh doanh')");
        db.execSQL("INSERT INTO " + TB_NGANH + " (" + MA_NGANH + ", " + TEN_NGANH + ") VALUES ('HTTT', 'Hệ thống thông tin')");

        db.execSQL("INSERT INTO " + TB_MONHOC + " (" + MA_MONHOC + ", " + TENMONHOC + ", " + TINCHI + ", " + MA_NGANH + ") " +
                "VALUES ('MH001', 'Lập trình Java', 3.0, 'CNTT')");
        db.execSQL("INSERT INTO " + TB_MONHOC + " (" + MA_MONHOC + ", " + TENMONHOC + ", " + TINCHI + ", " + MA_NGANH + ") " +
                "VALUES ('MH002', 'Cơ sở dữ liệu', 3.0, 'CNTT')");
        db.execSQL("INSERT INTO " + TB_MONHOC + " (" + MA_MONHOC + ", " + TENMONHOC + ", " + TINCHI + ", " + MA_NGANH + ") " +
                "VALUES ('MH003', 'Kinh tế vi mô', 2.5, 'KT')");
        db.execSQL("INSERT INTO " + TB_MONHOC + " (" + MA_MONHOC + ", " + TENMONHOC + ", " + TINCHI + ", " + MA_NGANH + ") " +
                "VALUES ('MH004', 'Kinh tế vĩ mô', 2.5, 'KT')");
        db.execSQL("INSERT INTO " + TB_MONHOC + " (" + MA_MONHOC + ", " + TENMONHOC + ", " + TINCHI + ", " + MA_NGANH + ") " +
                "VALUES ('MH005', 'Quản trị học', 3.0, 'QTKD')");
        db.execSQL("INSERT INTO " + TB_MONHOC + " (" + MA_MONHOC + ", " + TENMONHOC + ", " + TINCHI + ", " + MA_NGANH + ") " +
                "VALUES ('MH006', 'Marketing căn bản', 2.0, 'QTKD')");
        db.execSQL("INSERT INTO " + TB_MONHOC + " (" + MA_MONHOC + ", " + TENMONHOC + ", " + TINCHI + ", " + MA_NGANH + ") " +
                "VALUES ('MH007', 'Môi trường học', 3.0, 'MT')");
        db.execSQL("INSERT INTO " + TB_MONHOC + " (" + MA_MONHOC + ", " + TENMONHOC + ", " + TINCHI + ", " + MA_NGANH + ") " +
                "VALUES ('MH008', 'Hệ thống thông tin', 3.0, 'HTTT')");


        db.execSQL("INSERT INTO " + TB_HOCKY + " (" + MA_HOCKY + ", " + TENHOCKY + ") " +
                "VALUES ('HK1', 'Học kỳ 1')");
        db.execSQL("INSERT INTO " + TB_HOCKY + " (" + MA_HOCKY + ", " + TENHOCKY + ") " +
                "VALUES ('HK2', 'Học kỳ 2')");
        db.execSQL("INSERT INTO " + TB_HOCKY + " (" + MA_HOCKY + ", " + TENHOCKY + ") " +
                "VALUES ('HK3', 'Học kỳ 3')");
        db.execSQL("INSERT INTO " + TB_HOCKY + " (" + MA_HOCKY + ", " + TENHOCKY + ") " +
                "VALUES ('Unknown', 'Học kỳ hè')");

        db.execSQL("INSERT INTO " + TB_LOAIDIEM + " (" + MA_LOAIDIEM + ", " + TEN_LOAIDIEM + ", " + TRONGSO + ") " +
                "VALUES ('L01', 'Cuối kỳ', 0.5)");
        db.execSQL("INSERT INTO " + TB_LOAIDIEM + " (" + MA_LOAIDIEM + ", " + TEN_LOAIDIEM + ", " + TRONGSO + ") " +
                "VALUES ('L02', 'Giữa kỳ', 0.4)");
        db.execSQL("INSERT INTO " + TB_LOAIDIEM + " (" + MA_LOAIDIEM + ", " + TEN_LOAIDIEM + ", " + TRONGSO + ") " +
                "VALUES ('L03', 'Thường xuyên', 0.1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TB_DIEM);
        db.execSQL("DROP TABLE IF EXISTS " + TB_LOPSINHVIEN);
        db.execSQL("DROP TABLE IF EXISTS " + TB_LOAIDIEM);
        db.execSQL("DROP TABLE IF EXISTS " + TB_LOPHOCPHAN);
        db.execSQL("DROP TABLE IF EXISTS " + TB_SINHVIEN);
        db.execSQL("DROP TABLE IF EXISTS " + TB_GIANGVIEN);
        db.execSQL("DROP TABLE IF EXISTS " + TB_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TB_MONHOC);
        db.execSQL("DROP TABLE IF EXISTS " + TB_HOCKY);
        db.execSQL("DROP TABLE IF EXISTS " + TB_NGANH);
        db.execSQL("DROP TABLE IF EXISTS " + TB_LOPHANHCHINH);
        db.execSQL("DROP TABLE IF EXISTS " + TB_ROLE);
        db.execSQL("DROP TABLE IF EXISTS " + TB_LOAIDIEM);
        db.execSQL("DROP TABLE IF EXISTS " + TB_USERS);
        onCreate(db);



    }
}