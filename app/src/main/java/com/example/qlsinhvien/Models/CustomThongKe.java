package com.example.qlsinhvien.Models;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.widget.TextView;

import com.example.qlsinhvien.R;
import com.example.qlsinhvien.dao.LopHocPhanManager;
import com.example.qlsinhvien.dao.MonHocManager;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.List;

public class CustomThongKe extends MarkerView {

    private TextView txtContent, txtResult;
    private List<String> maMonHoc;
    private List<Double> diemTrungBinh;
    LopHocPhanManager lopHocPhanManager;
    MonHocManager monHocManager;

    public CustomThongKe(Context context, int layoutResource, List<String> maMonHoc, List<Double> diemTrungBinh) {
        super(context, layoutResource);
        this.maMonHoc = maMonHoc;
        this.diemTrungBinh = diemTrungBinh;
        this.lopHocPhanManager = new LopHocPhanManager(context);
        this.monHocManager = new MonHocManager(context);
        txtContent = findViewById(R.id.txtContent);

    }


    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        int index = (int) e.getX();
        String maTemp = maMonHoc.get(index);
        String tenMonHoc = monHocManager.getMonHoc(maMonHoc.get(index)).getTenMonHoc();
        double diemTemp = diemTrungBinh.get(index);

        SpannableStringBuilder spannable = new SpannableStringBuilder();

        spannable.append("Môn học: ", new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.append(tenMonHoc + "\n");

        spannable.append("Mã môn học: ", new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.append(maTemp + "\n");

        spannable.append("Điểm: ", new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.append(String.valueOf(diemTemp));

        txtContent.setText(spannable);

        super.refreshContent(e, highlight);

    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}





