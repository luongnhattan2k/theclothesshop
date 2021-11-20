package nhattan.lnt.clothesshop.DAO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

import nhattan.lnt.clothesshop.DTO.CTHoaDonDTO;
import nhattan.lnt.clothesshop.R;

public class CTHoaDonDAO extends BaseAdapter {
    private Fragment context;
    private int layout;
    public static List<CTHoaDonDTO> ListCTHoaDon;
    int id;



    public CTHoaDonDAO(Fragment context, int layout, List<CTHoaDonDTO> ListCTHoaDon) {
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
        TextView txt_TenSP, txt_GiaSP, txt_SLSP,txt_count;
        ImageButton btncong,btntru;

    }


    @Override
    public View getView(int i, View view, ViewGroup parent) {

        GioHangDAO.ViewHolder holder;

        if (view == null) {
            holder = new GioHangDAO.ViewHolder();
            LayoutInflater inflater;
            inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txt_TenSP = (TextView) view.findViewById(R.id.txtTensp);
            holder.txt_GiaSP = (TextView) view.findViewById(R.id.txtThanhtien);
            holder.txt_SLSP = (TextView) view.findViewById(R.id.txtSoluong);
            view.setTag(holder);
        } else {
            holder = (GioHangDAO.ViewHolder) view.getTag();
        }

        CTHoaDonDTO ctHoaDonDTO = ListCTHoaDon.get(i);
        return view;
    }
}
