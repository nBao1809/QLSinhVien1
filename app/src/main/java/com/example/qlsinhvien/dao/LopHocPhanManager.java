package com.example.qlsinhvien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qlsinhvien.Models.LopHocPhan;

import java.util.ArrayList;
import java.util.List;

public class LopHocPhanManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private List<LopHocPhan> lopHocPhanList;
    public LopHocPhanManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addLopHocPhan(LopHocPhan lopHocPhan) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MA_LOP, lopHocPhan.getMaLop());
        values.put(DatabaseHelper.TENLOP, lopHocPhan.getTenLop());
        values.put(DatabaseHelper.NGAYBATDAU, lopHocPhan.getNgayBatDau());
        values.put(DatabaseHelper.NGAYKETTHUC, lopHocPhan.getNgayKetThuc());
        values.put(DatabaseHelper.MA_MONHOC, lopHocPhan.getMaMonHoc());
        values.put(DatabaseHelper.MA_GIANGVIEN, lopHocPhan.getMaGiangVienPhuTrach());

        long rowInserted = db.insert(DatabaseHelper.TB_LOPHOCPHAN, null, values);
        db.close();

        return rowInserted; // >0 nếu thêm thành công, -1 nếu thất bại
    }

    public List<LopHocPhan> getAllLopHocPhan() {
        lopHocPhanList = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHelper.TB_LOPHOCPHAN;
        db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            c.moveToFirst();
            lopHocPhanList.add(new LopHocPhan(c.getString(0), c.getString(1),c.getDouble(2),
                    c.getDouble(3),c.getString(4),c.getString(5)));
            c.close();
            return lopHocPhanList;
        }
        return null;
    }
    public LopHocPhan getLopHocPhan(String maLopHocPhan) {
        db = dbHelper.getReadableDatabase();
        LopHocPhan lopHocPhan = null;
        String[] selection = new String[]{maLopHocPhan};
        Cursor c = db.query(DatabaseHelper.TB_LOPHOCPHAN, null,
                DatabaseHelper.MA_LOP +
                        "= ?",
                selection,
                null,
                null
                , null);
        if (c != null) {
            c.moveToFirst();
            lopHocPhan = new LopHocPhan(c.getString(0), c.getString(1),c.getDouble(2),
                    c.getDouble(3),c.getString(4),c.getString(5));
            c.close();
            return lopHocPhan;
        }
        return null;
    }
    public int updateLopHocPhan(LopHocPhan lopHocPhan) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHelper.MA_LOP, lopHocPhan.getMaLop());
        values.put(DatabaseHelper.TENLOP, lopHocPhan.getTenLop());
        values.put(DatabaseHelper.NGAYBATDAU, lopHocPhan.getNgayBatDau());
        values.put(DatabaseHelper.NGAYKETTHUC, lopHocPhan.getNgayKetThuc());
        values.put(DatabaseHelper.MA_MONHOC, lopHocPhan.getMaMonHoc());
        values.put(DatabaseHelper.MA_GIANGVIEN, lopHocPhan.getMaGiangVienPhuTrach());

        int rowsUpdated = db.update(
                DatabaseHelper.TB_LOPHOCPHAN,
                values,
                DatabaseHelper.MA_LOP + " = ?",
                new String[]{lopHocPhan.getMaLop()}
        );
        db.close();//>0 thành công =0 ko thành công
        return rowsUpdated;
    }

    public int deleteLopHocPhan(String maLopHocPhan) {
        db = dbHelper.getWritableDatabase();
        String selection = DatabaseHelper.MA_LOP + " = ?";
        String[] selectionArgs = {maLopHocPhan};

        int rowsDeleted = db.delete(DatabaseHelper.TB_LOPHOCPHAN, selection, selectionArgs);
        db.close();

        return rowsDeleted; // Trả về số dòng bị xóa, >0 nếu xóa thành công, 0 nếu không có gì bị xóa
    }
}
