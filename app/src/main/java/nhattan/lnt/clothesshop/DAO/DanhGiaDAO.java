package nhattan.lnt.clothesshop.DAO;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import nhattan.lnt.clothesshop.DTO.CTHoaDonDTO;
import nhattan.lnt.clothesshop.DTO.SanPhamDTO;
import nhattan.lnt.clothesshop.DanhGiaActivity;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;
import nhattan.lnt.clothesshop.Login;
import nhattan.lnt.clothesshop.R;

public class DanhGiaDAO extends BaseAdapter {
    private Context context;
    private int layout;
    public static List<CTHoaDonDTO> ListCTHoaDon;
    int id;
    String dateString_ngay;



    public DanhGiaDAO(Context context, int layout, List<CTHoaDonDTO> ListCTHoaDon) {
        this.context = context;
        this.layout = layout;
        this.ListCTHoaDon = ListCTHoaDon;
    }


    @Override
    public int getCount() {
        return ListCTHoaDon.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder{
        TextView txt_TenSP_dg, txt_Tongtien_dg, txt_SLSP_dg, txt_Size_dg;
        ImageView img_HinhAnh_dg;
        EditText edt_Noidungdanhgia_dg;
        Button btn_Guidanhgiasanpham_dg;
        RatingBar rb_saodanhgia_dg;

    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        DanhGiaDAO.ViewHolder holder;

        if (view == null){
            holder = new DanhGiaDAO.ViewHolder();
            LayoutInflater inflater;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txt_TenSP_dg =  view.findViewById(R.id.txtTenchitiet_dg);
            holder.txt_SLSP_dg =  view.findViewById(R.id.txtSoluongchitiet_dg);
            holder.txt_Tongtien_dg =  view.findViewById(R.id.txtThanhtienchitiet_dg);
            holder.img_HinhAnh_dg =  view.findViewById(R.id.imgHinhchitiet_dg);
            holder.txt_Size_dg =  view.findViewById(R.id.txtSizechitiet_dg);
            holder.edt_Noidungdanhgia_dg =  view.findViewById(R.id.edt_Noidungdanhgia_dg);
            holder.btn_Guidanhgiasanpham_dg =  view.findViewById(R.id.btn_Guidanhgiasanpham_dg);
            holder.rb_saodanhgia_dg =  view.findViewById(R.id.rb_saodanhgia_dg);

            view.setTag(holder);
        } else {
            holder = (DanhGiaDAO.ViewHolder) view.getTag();
        }

        CTHoaDonDTO ctHoaDonDTO = ListCTHoaDon.get(i);
        SanPhamDTO sanPhamDTO = HomeFragment.database.SANPHAM(ctHoaDonDTO.getIDSANPHAM());

        holder.txt_TenSP_dg.setText(ctHoaDonDTO.getTENSANPHAM());
        holder.txt_SLSP_dg.setText("Sl: " + (ctHoaDonDTO.getSOLUONG()));
        holder.txt_Tongtien_dg.setText("Giá: " + (NumberFormat.getNumberInstance(Locale.US).format(ctHoaDonDTO.getTHANHTIEN())) + " VNĐ");
        holder.txt_Size_dg.setText(ctHoaDonDTO.getSIZE());
        id = ctHoaDonDTO.getIDCTHOADON();

        Thread t = new Thread() {
            @Override
            public void run () {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        ((DanhGiaActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf_ngay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                dateString_ngay = sdf_ngay.format(date);
                            }
                        });
                    }
                } catch (InterruptedException e ) { }
            }
        };
        t.start();

        holder.btn_Guidanhgiasanpham_dg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noidung = String.valueOf(holder.edt_Noidungdanhgia_dg.getText());
                if (noidung.isEmpty()) {
                    Toast.makeText(context.getApplicationContext(),  "Hãy nhập nội dung đánh giá !!", Toast.LENGTH_SHORT).show();
                } else {
                    HomeFragment.database.ThemDanhGia(Login.taiKhoanDTO.getMATK(), ctHoaDonDTO.getIDSANPHAM(),
                            holder.rb_saodanhgia_dg.getRating(), noidung, dateString_ngay, ctHoaDonDTO.getSIZE());
                    Toast.makeText(context.getApplicationContext(),  "Thành công !", Toast.LENGTH_SHORT).show();
                    holder.btn_Guidanhgiasanpham_dg.setText("Đã đánh giá");
                    holder.btn_Guidanhgiasanpham_dg.setEnabled(false);
                    holder.btn_Guidanhgiasanpham_dg.setBackgroundTintList(ContextCompat.getColorStateList(
                            context.getApplicationContext(), R.color.xam));
                }
            }
        });


        // chuyen byte[] -> ve bitmap
        byte[] hinhAnh = sanPhamDTO.getImageSP();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0, hinhAnh.length);
        holder.img_HinhAnh_dg.setImageBitmap(bitmap);

        return view;
    }
}
