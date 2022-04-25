package nhattan.lnt.clothesshop.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

import nhattan.lnt.clothesshop.DTO.TinTucDTO;
import nhattan.lnt.clothesshop.Database.CreateDatabase;
import nhattan.lnt.clothesshop.R;

public class TinTucDAO extends BaseAdapter {

    SQLiteDatabase database;

    private Fragment context;
    private int layout;
    public static List<TinTucDTO> ListTinTuc;
    int id,idcthd;

    public TinTucDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public TinTucDAO(Fragment context, int layout, List<TinTucDTO> ListTinTuc) {
        this.context = context;
        this.layout = layout;
        this.ListTinTuc = ListTinTuc;
    }

    @Override
    public int getCount() {
        return ListTinTuc.size();
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
        TextView txt_Tieude, txt_noidung, txt_Ngaydang;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater;
            inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.txt_Tieude = (TextView) view.findViewById(R.id.txtV_Tieude);
            holder.txt_noidung = (TextView) view.findViewById(R.id.txtV_Noidung);
            holder.txt_Ngaydang = (TextView) view.findViewById(R.id.txtV_Ngaydang);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        TinTucDTO tinTucDTO = ListTinTuc.get(i);
        holder.txt_Tieude.setText(tinTucDTO.getTIEUDE());
        holder.txt_noidung.setText(tinTucDTO.getNOIDUNG());
        holder.txt_Ngaydang.setText(tinTucDTO.getNGAYDANG());
        id = tinTucDTO.getIDTINTUC();

        return view;
    }
}
