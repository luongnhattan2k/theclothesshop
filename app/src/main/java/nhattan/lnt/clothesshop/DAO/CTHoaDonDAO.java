package nhattan.lnt.clothesshop.DAO;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import nhattan.lnt.clothesshop.DTO.CTHoaDonDTO;
import nhattan.lnt.clothesshop.DTO.SanPhamDTO;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;
import nhattan.lnt.clothesshop.R;

public class CTHoaDonDAO extends BaseAdapter {
    private Context context;
    private int layout;
    public static List<CTHoaDonDTO> ListCTHoaDon;
    int id;



    public CTHoaDonDAO(Context context, int layout, List<CTHoaDonDTO> ListCTHoaDon) {
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
        TextView txt_TenSP, txt_Tongtien, txt_SLSP, txt_Size;
        ImageView img_HinhAnh;

    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        CTHoaDonDAO.ViewHolder holder;

        if (view == null){
            holder = new CTHoaDonDAO.ViewHolder();
            LayoutInflater inflater;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txt_TenSP =  view.findViewById(R.id.txtTenchitiet);
            holder.txt_SLSP =  view.findViewById(R.id.txtSoluongchitiet);
            holder.txt_Tongtien =  view.findViewById(R.id.txtThanhtienchitiet);
            holder.img_HinhAnh =  view.findViewById(R.id.imgHinhchitiet);
            holder.txt_Size =  view.findViewById(R.id.txtSizechitiet);

            view.setTag(holder);
        } else {
            holder = (CTHoaDonDAO.ViewHolder) view.getTag();
        }

        CTHoaDonDTO ctHoaDonDTO = ListCTHoaDon.get(i);

        holder.txt_TenSP.setText(ctHoaDonDTO.getTENSANPHAM());
        holder.txt_SLSP.setText("Sl: " + (ctHoaDonDTO.getSOLUONG()));
        holder.txt_Tongtien.setText("Giá: " + (NumberFormat.getNumberInstance(Locale.US).format(ctHoaDonDTO.getTHANHTIEN())) + " VNĐ");
        holder.txt_Size.setText(ctHoaDonDTO.getSIZE());
        id = ctHoaDonDTO.getIDCTHOADON();

        SanPhamDTO sanPhamDTO = HomeFragment.database.SANPHAM(ctHoaDonDTO.getIDSANPHAM());
        // chuyen byte[] -> ve bitmap
        byte[] hinhAnh = sanPhamDTO.getImageSP();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0, hinhAnh.length);
        holder.img_HinhAnh.setImageBitmap(bitmap);

        return view;
    }
}
