package nhattan.lnt.clothesshop.Manager.FragmentManager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import nhattan.lnt.clothesshop.ADAPTER.QuanLyHoaDonAdapter;
import nhattan.lnt.clothesshop.DTO.HoaDonDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.Manager.Chitiethoadon_manager;
import nhattan.lnt.clothesshop.Manager.ThongKe_Thang;
import nhattan.lnt.clothesshop.R;


public class Quanly_HoaDon extends Fragment {

    private View view;
    RecyclerView recV_DanhSachSanPham_qlhd;
    Database database;
    HoaDonDTO hoaDonDTO;
    ArrayList<HoaDonDTO> listHoaDon;
    QuanLyHoaDonAdapter adapter;
    Button btn_Thongkedoanhthu, btn_Thoatthongke, btn_Chonngay_qlhd, btn_Xacnhanthang_qlhd, btn_Xacnhannam_qlhd;
    String strNgay = null;
    String strThang = null;
    String strNam = null;
    TextView txt_Tongtienthongke, txt_Ngaythongke;
    EditText edt_Thangthongke, edt_Namthongke, edt_timkiemhoadon;
    int Doanhthu;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quanly__hoadon, container, false);

        database = new Database(getActivity());
        AnhXa();
        registerForContextMenu(recV_DanhSachSanPham_qlhd);

        listHoaDon = new ArrayList<>();
        adapter = new QuanLyHoaDonAdapter(this, listHoaDon);
        Load();

        recV_DanhSachSanPham_qlhd.setLayoutManager( new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        recV_DanhSachSanPham_qlhd.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        Load();
        super.onStart();
    }

    private void Load() {
        listHoaDon.clear();
        listHoaDon.addAll(database.QuanLyHoaDon());
        adapter.notifyDataSetChanged();
    }

    private void Load_TimKiem() {
        listHoaDon.clear();
        listHoaDon.addAll(database.QuanLyHoaDon_TimKiem(edt_timkiemhoadon.getText().toString()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = -1;
        try {
            position = QuanLyHoaDonAdapter.getPosition();
        } catch (Exception e) {
            Log.d("TAG", e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.iXemhd:
                Intent iXemhoadon = new Intent(getActivity(), Chitiethoadon_manager.class);
                iXemhoadon.putExtra("IDQLCTHD", listHoaDon.get(position).getIDCTHOADON());
                Toast.makeText(getActivity(), "Mã hóa đơn là FASH" + listHoaDon.get(position).getIDCTHOADON(), Toast.LENGTH_SHORT).show();
                startActivity(iXemhoadon);
                return true;
            case R.id.iXoahd:
                hoaDonDTO = listHoaDon.get(position);
                ShowDialog();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void ShowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông Báo");
        builder.setMessage("Bạn có chắc muốn xóa nó hay không ?");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "Xóa thành công ! " + hoaDonDTO.getIDHOADON(), Toast.LENGTH_SHORT).show();
                database.XoaHD(hoaDonDTO.getIDHOADON());
                Load();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void Tongdoanhthu() {
        Cursor cursor = database.Getdata("SELECT SUM ( TONGTIEN ) FROM HOADON WHERE TINHTRANG >= 5 AND  NGAYDAT = '"
                + strNgay + "'");
        cursor.moveToNext();
        Doanhthu = cursor.getInt(0);

        txt_Tongtienthongke.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(Doanhthu) + " VNĐ"));
    }

    private void Tongdoanhthu_nam() {
        Cursor cursor = database.Getdata("SELECT SUM ( TONGTIEN ) FROM HOADON WHERE NAMDAT = '"
                + strNam + "'");
        cursor.moveToNext();
        Doanhthu = cursor.getInt(0);

        txt_Tongtienthongke.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(Doanhthu) + " VNĐ"));
    }

    private void AnhXa() {
        recV_DanhSachSanPham_qlhd = view.findViewById(R.id.recV_DanhSachSanPham_qlhd);
        btn_Thongkedoanhthu = view.findViewById(R.id.btnThongkedoanhthu);
        edt_timkiemhoadon = view.findViewById(R.id.edt_timkiemhoadon);

        edt_timkiemhoadon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Load();
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Load_TimKiem();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_Thongkedoanhthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(
                        R.layout.layout_bottom_sheet_qlhd, getActivity().findViewById(R.id.bottomSheetContainer_qlhd)
                );

                Button btn_Thongketheongay_qlhd = bottomSheetView.findViewById(R.id.btn_Thongketheongay_qlhd);
                Button btn_Thongketheothang_qlhd = bottomSheetView.findViewById(R.id.btn_Thongketheothang_qlhd);

                btn_Thongketheothang_qlhd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), ThongKe_Thang.class));
                    }
                });

                btn_Thongketheongay_qlhd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogThongkeNgay();
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            }
        });

    }

    private void DialogThongkeNgay() {
        Dialog dialog_ngay = new Dialog(getActivity());
        dialog_ngay.setContentView(R.layout.dialog_custom_qlhd);

        txt_Ngaythongke = dialog_ngay.findViewById(R.id.txt_Ngaythongke);
        txt_Tongtienthongke = dialog_ngay.findViewById(R.id.txt_Tongtienthongke);
        btn_Thoatthongke = dialog_ngay.findViewById(R.id.btn_Thoatthongke);
        btn_Chonngay_qlhd = dialog_ngay.findViewById(R.id.btn_Chonngay_qlhd);

        Calendar calendar = Calendar.getInstance();
        int year_now = calendar.get(Calendar.YEAR);
        int month_now = calendar.get(Calendar.MONTH);
        int day_now = calendar.get(Calendar.DAY_OF_MONTH);

        btn_Chonngay_qlhd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar chonNgay = Calendar.getInstance();
                        chonNgay.set(year, month, dayOfMonth);
                        strNgay = simpleDateFormat.format(chonNgay.getTime());
                        txt_Ngaythongke.setText(strNgay);
                        Log.d("Ngày: ", String.valueOf(dayOfMonth));
                        Log.d("Tháng: ", String.valueOf(month + 1));
                        Log.d("Năm: ", String.valueOf(year));
                        Tongdoanhthu();

                    }
                }, year_now, month_now, day_now );
                datePickerDialog.show();
            }
        });

        btn_Thoatthongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_ngay.cancel();
                dialog_ngay.dismiss();
            }
        });

        dialog_ngay.show();
    }


}