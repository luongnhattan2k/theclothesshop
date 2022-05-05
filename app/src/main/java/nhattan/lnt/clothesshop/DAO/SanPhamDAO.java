package nhattan.lnt.clothesshop.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import nhattan.lnt.clothesshop.DTO.SanPhamDTO;
import nhattan.lnt.clothesshop.Database.CreateDatabase;
import nhattan.lnt.clothesshop.R;

public class SanPhamDAO extends BaseAdapter {

    SQLiteDatabase database;

    private Fragment context;
    private int layout;
    public static List<SanPhamDTO> sanPhamDTOList;
    int id;

    public SanPhamDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public SanPhamDAO(Fragment context, int layout, List<SanPhamDTO> sanPhamDTOList) {
        this.context = context;
        this.layout = layout;
        this.sanPhamDTOList = sanPhamDTOList;
    }


    @Override
    public int getCount() {
        return sanPhamDTOList.size();
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
        TextView txt_TenSP, txt_GiaSP;
        ImageView img_HinhAnh;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        ViewHolder holder;

        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater;
            inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txt_TenSP = (TextView) view.findViewById(R.id.product_name);
            holder.txt_GiaSP = (TextView) view.findViewById(R.id.product_price);
            holder.img_HinhAnh = (ImageView) view.findViewById(R.id.product_image);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        SanPhamDTO sanPhamDTO = sanPhamDTOList.get(i);
        holder.txt_TenSP.setText(sanPhamDTO.getTenSP());
        holder.txt_GiaSP.setText((NumberFormat.getNumberInstance(Locale.US).format(sanPhamDTO.getGiaSP()) + " VNĐ"));
        id = sanPhamDTO.getMaSP();

        // chuyen byte[] -> ve bitmap
        byte[] hinhAnh = sanPhamDTO.getImageSP();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0, hinhAnh.length);
        holder.img_HinhAnh.setImageBitmap(bitmap);

        return view;
    }
}
