package nhattan.lnt.clothesshop.DAO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
        TextView txtTongTien,txtNgaydat,txtMahoadon,txtTinhtrang;
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
            holder.txtNgaydat = (TextView) view.findViewById(R.id.txtNgaydathoadon);
            holder.txtMahoadon = (TextView) view.findViewById(R.id.txtMahoadon);
            holder.txtTinhtrang = (TextView) view.findViewById(R.id.txtTinhtrangnhoadon);
            holder.listView = (ListView) view.findViewById(R.id.lv_lichsugiaodich);
            view.setTag(holder);
        } else {
            holder = (HoaDonDAO.ViewHolder) view.getTag();
        }

        HoaDonDTO hoaDon = ListHoaDon.get(i);

        if (hoaDon.getTINHTRANG() == 1) {
            holder.txtTinhtrang.setText("T??nh tr???ng: Ch??? duy???t ????n");
        } else if (hoaDon.getTINHTRANG() == 2) {
            holder.txtTinhtrang.setText("T??nh tr???ng: ??ang ????ng h??ng");
        } else if (hoaDon.getTINHTRANG() == 3) {
            holder.txtTinhtrang.setText("T??nh tr???ng: Xu???t ????n h??ng");
        } else if (hoaDon.getTINHTRANG() == 4) {
            holder.txtTinhtrang.setText("T??nh tr???ng: ??ang giao");
        } else if (hoaDon.getTINHTRANG() == 5) {
            holder.txtTinhtrang.setText("T??nh tr???ng: Giao th??nh c??ng");
        } else if (hoaDon.getTINHTRANG() == 6) {
            holder.txtTinhtrang.setText("T??nh tr???ng: ???? nh???n h??ng");
        } else if (hoaDon.getTINHTRANG() == 7) {
            holder.txtTinhtrang.setText("T??nh tr???ng: ???? g???i ????nh gi??");
        } else {
            holder.txtTinhtrang.setText("T??nh tr???ng: ???? h???y");
        }

        holder.txtMahoadon.setText("M?? h??a ????n: " + "FASH" + hoaDon.getIDHOADON());
        holder.txtNgaydat.setText("Ng??y ?????t: " + hoaDon.getNGAYDAT());
        holder.txtTongTien.setText("T???ng ti???n: " + (NumberFormat.getNumberInstance(Locale.US).format(hoaDon.getTONGTIEN())) + " VN??");
        id = hoaDon.getIDHOADON();
        idcthd = hoaDon.getIDCTHOADON();

        return view;
    }
}
