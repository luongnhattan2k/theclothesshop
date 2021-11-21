package nhattan.lnt.clothesshop.DAO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import nhattan.lnt.clothesshop.DTO.HoaDonDTO;
import nhattan.lnt.clothesshop.R;

public class HoaDonDAO extends BaseAdapter {

    private Context context;
    private int layout;
    public static List<HoaDonDTO> ListHoaDon;
    int id,idcthd;



    public HoaDonDAO(Context context, int layout, List<HoaDonDTO> ListHoaDon) {
        this.context = context;
        this.layout = layout;
        this.ListHoaDon = ListHoaDon;
    }


    @Override
    public int getCount() {
        return ListHoaDon.size();
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
        TextView txtTongTien,txtdiachi,txtghichu;
        ListView listView;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        HoaDonDAO.ViewHolder holder;

        if (view == null){
            holder = new HoaDonDAO.ViewHolder();
            LayoutInflater inflater;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txtTongTien = (TextView) view.findViewById(R.id.txtTongtienhoadon);
            holder.txtdiachi = (TextView) view.findViewById(R.id.txtDiachi);
            holder.txtghichu = (TextView) view.findViewById(R.id.txtGhichu);
            holder.listView = (ListView) view.findViewById(R.id.lv_lichsugiaodich);
            view.setTag(holder);
        } else {
            holder = (HoaDonDAO.ViewHolder) view.getTag();
        }

        HoaDonDTO hoaDon = ListHoaDon.get(i);


        holder.txtTongTien.setText(String.valueOf(NumberFormat.getNumberInstance(Locale.US).format(hoaDon.getTONGTIEN())) + " VNĐ");
        holder.txtdiachi.setText(hoaDon.getDIACHI());
        holder.txtghichu.setText(hoaDon.getGHICHU());
        id = hoaDon.getIDHOADON();
        idcthd = hoaDon.getIDCTHOADON();

        return view;
    }
}
