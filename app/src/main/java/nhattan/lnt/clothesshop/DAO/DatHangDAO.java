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

import java.util.List;

import nhattan.lnt.clothesshop.DTO.DatHangDTO;
import nhattan.lnt.clothesshop.R;

public class DatHangDAO extends BaseAdapter {
    SQLiteDatabase database;

    private Context context;
    private int layout;
    public static List<DatHangDTO> datHangDTOS;
    int id;

    public DatHangDAO(Context context, int layout, List<DatHangDTO> datHangDTOS) {
        this.context = context;
        this.layout = layout;
        this.datHangDTOS = datHangDTOS;
    }


    @Override
    public int getCount() {
        return datHangDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder{
        TextView txt_TenSP, txt_GiaSP, txt_SLSP;
        ImageView img_HinhAnh;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DatHangDAO.ViewHolder holder;

        if (convertView == null){
            holder = new DatHangDAO.ViewHolder();
            LayoutInflater inflater;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txt_TenSP = (TextView) convertView.findViewById(R.id.txtTensp);
            holder.txt_GiaSP = (TextView) convertView.findViewById(R.id.txtThanhtien);
            holder.txt_SLSP = (TextView) convertView.findViewById(R.id.txtSoluong) ;
            holder.img_HinhAnh = (ImageView) convertView.findViewById(R.id.imageHinhCustom);
            convertView.setTag(holder);
        } else {
            holder = (DatHangDAO.ViewHolder) convertView.getTag();
        }

        DatHangDTO datHangDTO = datHangDTOS.get(position);
        holder.txt_TenSP.setText(datHangDTO.getTENSANPHAM());
        holder.txt_GiaSP.setText("Giá: " + String.valueOf(datHangDTO.getTHANHTIEN()) + " VNĐ" );
        holder.txt_SLSP.setText("Sl: " + String.valueOf(datHangDTO.getSOLUONG()) );
        id = datHangDTO.getIDGIOHANG();

        // chuyen byte[] -> ve bitmap
        byte[] hinhAnh = datHangDTO.getImageSP();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0, hinhAnh.length);
        holder.img_HinhAnh.setImageBitmap(bitmap);

        return convertView;
    }
}
