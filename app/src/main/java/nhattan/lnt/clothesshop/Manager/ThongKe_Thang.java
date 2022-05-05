package nhattan.lnt.clothesshop.Manager;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import nhattan.lnt.clothesshop.DTO.ThongKe_ThangDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.R;

public class ThongKe_Thang extends AppCompatActivity{

    private LineChart chart;
    ArrayList<Entry> barEntryArrayList;
    ArrayList<String> labelNames;
    ArrayList<ThongKe_ThangDTO> thongKe_thangs = new ArrayList<>();
    Database database;
    TextView txt_Namchon;
    Button btn_Namchon;
    ImageButton ibtnExitdathang;
    int thang1,thang2,thang3,thang4,thang5,thang6,thang7,thang8,thang9,thang10,thang11,thang12;
    String strNam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_thong_ke_thang);

        database = new Database(ThongKe_Thang.this);
        chart = findViewById(R.id.chart1);
        txt_Namchon = findViewById(R.id.txt_Namchon);
        btn_Namchon = findViewById(R.id.btn_Namchon);
        ibtnExitdathang = findViewById(R.id.ibtnExitdathang);

        Sukien();

    }

    private void Sukien() {
        btn_Namchon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar today = Calendar.getInstance();
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(ThongKe_Thang.this,
                    new MonthPickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(int selectedMonth, int selectedYear) { // on date set
                            txt_Namchon.setText(String.valueOf(selectedYear));
                            strNam = txt_Namchon.getText().toString();
                            Toast.makeText(ThongKe_Thang.this, strNam, Toast.LENGTH_SHORT).show();
                            Tongdoanhthu_thang1(strNam);
                            Tongdoanhthu_thang2(strNam);
                            Tongdoanhthu_thang3(strNam);
                            Tongdoanhthu_thang4(strNam);
                            Tongdoanhthu_thang5(strNam);
                            Tongdoanhthu_thang6(strNam);
                            Tongdoanhthu_thang7(strNam);
                            Tongdoanhthu_thang8(strNam);
                            Tongdoanhthu_thang9(strNam);
                            Tongdoanhthu_thang10(strNam);
                            Tongdoanhthu_thang11(strNam);
                            Tongdoanhthu_thang12(strNam);

                            getMonthSales();
                            barEntryArrayList = new ArrayList<>();
                            labelNames = new ArrayList<>();
                            barEntryArrayList.clear();
                            labelNames.clear();
                            for (int i = 0; i < thongKe_thangs.size(); i++) {
                                String month = thongKe_thangs.get(i).getMonth();
                                int sales = thongKe_thangs.get(i).getSales();
                                barEntryArrayList.add(new BarEntry(i, sales));
                                labelNames.add(month);
                            }

                            LineDataSet lineDataSet = new LineDataSet(barEntryArrayList, "Thống kê theo tháng");
                            lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                            Description description = new Description();
                            description.setText("Tháng");
                            chart.setDescription(description);
                            LineData lineData = new LineData(lineDataSet);
                            chart.setData(lineData);

                            XAxis xAxis = chart.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(labelNames));

                            xAxis.setPosition(XAxis.XAxisPosition.TOP);
                            xAxis.setDrawGridLines(false);
                            xAxis.setDrawAxisLine(false);
                            xAxis.setGranularity(1f);
                            xAxis.setLabelCount(labelNames.size());
                            xAxis.setLabelRotationAngle(270);
                            chart.animateY(2000);
                            chart.invalidate();
                        }

                    }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

            builder.setActivatedMonth(Calendar.JULY)
                    .setMinYear(1990)
                    .setActivatedYear(today.get(Calendar.YEAR))
                    .setMaxYear(2030)
                    .setTitle("Select year")
                    .showYearOnly()
                    .build().show();
            }
        });

        ibtnExitdathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void Tongdoanhthu_thang1(String Nam) {
        Cursor cursor = database.Getdata("SELECT SUM ( TONGTIEN ) FROM HOADON WHERE TINHTRANG >= 5  AND THANGDAT = '01/"+"" + Nam + "'");
        cursor.moveToNext();
        thang1 = cursor.getInt(0);
    }

    private void Tongdoanhthu_thang2(String Nam) {
        Cursor cursor = database.Getdata("SELECT SUM ( TONGTIEN ) FROM HOADON WHERE TINHTRANG >= 5  AND THANGDAT = '02/"+"" + Nam + "'");
        cursor.moveToNext();
        thang2 = cursor.getInt(0);
    }

    private void Tongdoanhthu_thang3(String Nam) {
        Cursor cursor = database.Getdata("SELECT SUM ( TONGTIEN ) FROM HOADON WHERE TINHTRANG >= 5  AND THANGDAT = '03/"+"" + Nam + "'");
        cursor.moveToNext();
        thang3 = cursor.getInt(0);
    }

    private void Tongdoanhthu_thang4(String Nam) {
        Cursor cursor = database.Getdata("SELECT SUM ( TONGTIEN ) FROM HOADON WHERE TINHTRANG >= 5  AND THANGDAT = '04/"+"" + Nam + "'");
        cursor.moveToNext();
        thang4 = cursor.getInt(0);
    }

    private void Tongdoanhthu_thang5(String Nam) {
        Cursor cursor = database.Getdata("SELECT SUM ( TONGTIEN ) FROM HOADON WHERE TINHTRANG >= 5  AND THANGDAT = '05/"+"" + Nam + "'");
        cursor.moveToNext();
        thang5 = cursor.getInt(0);
    }

    private void Tongdoanhthu_thang6(String Nam) {
        Cursor cursor = database.Getdata("SELECT SUM ( TONGTIEN ) FROM HOADON WHERE TINHTRANG >= 5  AND THANGDAT = '06/"+"" + Nam + "'");
        cursor.moveToNext();
        thang6 = cursor.getInt(0);
    }

    private void Tongdoanhthu_thang7(String Nam) {
        Cursor cursor = database.Getdata("SELECT SUM ( TONGTIEN ) FROM HOADON WHERE TINHTRANG >= 5  AND THANGDAT = '07/"+"" + Nam + "'");
        cursor.moveToNext();
        thang7 = cursor.getInt(0);
    }

    private void Tongdoanhthu_thang8(String Nam) {
        Cursor cursor = database.Getdata("SELECT SUM ( TONGTIEN ) FROM HOADON WHERE TINHTRANG >= 5  AND THANGDAT = '08/"+"" + Nam + "'");
        cursor.moveToNext();
        thang8 = cursor.getInt(0);
    }

    private void Tongdoanhthu_thang9(String Nam) {
        Cursor cursor = database.Getdata("SELECT SUM ( TONGTIEN ) FROM HOADON WHERE TINHTRANG >= 5  AND THANGDAT = '09/"+"" + Nam + "'");
        cursor.moveToNext();
        thang9 = cursor.getInt(0);
    }

    private void Tongdoanhthu_thang10(String Nam) {
        Cursor cursor = database.Getdata("SELECT SUM ( TONGTIEN ) FROM HOADON WHERE TINHTRANG >= 5  AND THANGDAT = '10/"+"" + Nam + "'");
        cursor.moveToNext();
        thang10 = cursor.getInt(0);
    }

    private void Tongdoanhthu_thang11(String Nam) {
        Cursor cursor = database.Getdata("SELECT SUM ( TONGTIEN ) FROM HOADON WHERE TINHTRANG >= 5  AND THANGDAT = '11/"+"" + Nam + "'");
        cursor.moveToNext();
        thang11 = cursor.getInt(0);
    }

    private void Tongdoanhthu_thang12(String Nam) {
        Cursor cursor = database.Getdata("SELECT SUM ( TONGTIEN ) FROM HOADON WHERE TINHTRANG >= 5  AND THANGDAT = '12/"+"" + Nam + "'");
        cursor.moveToNext();
        thang12 = cursor.getInt(0);
    }


    private void getMonthSales(){
        thongKe_thangs.clear();
        thongKe_thangs.add(new ThongKe_ThangDTO("1", thang1));
        thongKe_thangs.add(new ThongKe_ThangDTO("2", thang2));
        thongKe_thangs.add(new ThongKe_ThangDTO("3", thang3));
        thongKe_thangs.add(new ThongKe_ThangDTO("4", thang4));
        thongKe_thangs.add(new ThongKe_ThangDTO("5", thang5));
        thongKe_thangs.add(new ThongKe_ThangDTO("6", thang6));
        thongKe_thangs.add(new ThongKe_ThangDTO("7", thang7));
        thongKe_thangs.add(new ThongKe_ThangDTO("8", thang8));
        thongKe_thangs.add(new ThongKe_ThangDTO("9", thang9));
        thongKe_thangs.add(new ThongKe_ThangDTO("10", thang10));
        thongKe_thangs.add(new ThongKe_ThangDTO("11", thang11));
        thongKe_thangs.add(new ThongKe_ThangDTO("12", thang12));
    }
}