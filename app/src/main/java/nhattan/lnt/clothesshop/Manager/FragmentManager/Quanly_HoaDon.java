package nhattan.lnt.clothesshop.Manager.FragmentManager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
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
import nhattan.lnt.clothesshop.R;


public class Quanly_HoaDon extends Fragment {

    private View view;
    RecyclerView recV_DanhSachSanPham_qlhd;
    Database database;
    HoaDonDTO hoaDonDTO;
    ArrayList<HoaDonDTO> listHoaDon;
    QuanLyHoaDonAdapter adapter;
    Button btn_Thongkedoanhthu, btn_Thoatthongke;
    String strNgay = null;
    TextView txt_Tongtienthongke, txt_Ngaythongke;
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
                database.XoaHD(hoaDonDTO.getIDHOADON());
                Toast.makeText(getContext(), "Xóa thành công !", Toast.LENGTH_SHORT).show();
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

    private void AnhXa() {
        recV_DanhSachSanPham_qlhd = view.findViewById(R.id.recV_DanhSachSanPham_qlhd);
        btn_Thongkedoanhthu = view.findViewById(R.id.btnThongkedoanhthu);

        btn_Thongkedoanhthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(
                        R.layout.layout_bottom_sheet_qlhd, getActivity().findViewById(R.id.bottomSheetContainer_qlhd)
                );

                TextView txt_Hienthingay_qlhd = bottomSheetView.findViewById(R.id.txt_Hienthingay_qlhd);
//                TextView txt_Hienthithang_qlhd = bottomSheetView.findViewById(R.id.txt_Hienthithang_qlhd);
//                TextView txt_Hienthinam_qlhd = bottomSheetView.findViewById(R.id.txt_Hienthinam_qlhd);
                Button btn_Chonngay_qlhd = bottomSheetView.findViewById(R.id.btn_Chonngay_qlhd);
                Button btn_Xacnhan_Chonngay_qlhd = bottomSheetView.findViewById(R.id.btn_Xacnhan_Chonngay_qlhd);
//                Button btn_Chonthang_qlhd = bottomSheetView.findViewById(R.id.btn_Chonthang_qlhd);
//                Button btn_Xacnhan_Chonthang_qlhd = bottomSheetView.findViewById(R.id.btn_Xacnhan_Chonthang_qlhd);
//                Button btn_Chonnam_qlhd = bottomSheetView.findViewById(R.id.btn_Chonnam_qlhd);
//                Button btn_Xacnhan_Chonnam_qlhd = bottomSheetView.findViewById(R.id.btn_Xacnhan_Chonnam_qlhd);

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

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
                                txt_Hienthingay_qlhd.setText(strNgay);
                            }
                        }, year, month, day );
                        datePickerDialog.show();
                    }
                });

                btn_Xacnhan_Chonngay_qlhd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (strNgay == null) {
                            Toast.makeText(getActivity(), "Hãy chọn ngày cần thống kê ! ", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(getActivity(), "Ngày: " + strNgay, Toast.LENGTH_SHORT).show();
                            DialogThongke();
                            Tongdoanhthu();
                        }

                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            }
        });

    }

    private void Tongdoanhthu() {
        Cursor cursor = database.Getdata("SELECT SUM ( TONGTIEN ) FROM HOADON WHERE NGAYDAT = '"
                + strNgay + "'");
        cursor.moveToNext();
        Doanhthu = cursor.getInt(0);

        txt_Tongtienthongke.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(Doanhthu) + " VNĐ"));
    }

    private void DialogThongke() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_custom_qlhd);

        txt_Ngaythongke = dialog.findViewById(R.id.txt_Ngaythongke);
        txt_Tongtienthongke = dialog.findViewById(R.id.txt_Tongtienthongke);
        btn_Thoatthongke = dialog.findViewById(R.id.btn_Thoatthongke);

        txt_Ngaythongke.setText(strNgay);

        btn_Thoatthongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}