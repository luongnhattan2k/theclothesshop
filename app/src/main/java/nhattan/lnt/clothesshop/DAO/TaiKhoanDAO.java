package nhattan.lnt.clothesshop.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import java.util.List;

import nhattan.lnt.clothesshop.DTO.TaiKhoanDTO;
import nhattan.lnt.clothesshop.Database.CreateDatabase;
import nhattan.lnt.clothesshop.R;

public class TaiKhoanDAO extends BaseAdapter {
    SQLiteDatabase database;
    private Context context;
    private int layout;
    public static List<TaiKhoanDTO> taiKhoanDTOList;
    int id;

    public void DoAnAdapter(Context context, int layout, List<TaiKhoanDTO> taiKhoanDTOList) {
        this.context = context;
        this.layout = layout;
        this.taiKhoanDTOList = taiKhoanDTOList;
    }

    public TaiKhoanDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemTaiKhoan(TaiKhoanDTO taiKhoanDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.tbl_TAIKHOAN_TENTAIKHOAN,taiKhoanDTO.getTENTK());
        contentValues.put(CreateDatabase.tbl_TAIKHOAN_MATKHAU,taiKhoanDTO.getMATKHAU());
        contentValues.put(CreateDatabase.tbl_TAIKHOAN_SDT,taiKhoanDTO.getSDT());
        contentValues.put(CreateDatabase.tbl_TAIKHOAN_EMAIL,taiKhoanDTO.getEMAIL());
        contentValues.put(CreateDatabase.tbl_TAIKHOAN_QUYEN,1);
        contentValues.put(CreateDatabase.tbl_TAIKHOAN_LOAITK,1);

        long kiemtra = database.insert(CreateDatabase.tbl_TAIKHOAN, null, contentValues);
        return kiemtra;
    }



    public TaiKhoanDTO KiemTraDangNhap(String tendangnhap, String matkhau){
        String truyvan = "SELECT * FROM " + CreateDatabase.tbl_TAIKHOAN + " WHERE " + CreateDatabase.tbl_TAIKHOAN_TENTAIKHOAN + " = '" + tendangnhap
                + "' AND " + CreateDatabase.tbl_TAIKHOAN_MATKHAU + " = '" + matkhau + "'";

        Cursor cursor = database.rawQuery(truyvan, null);
        while(cursor.moveToNext()){
            return new TaiKhoanDTO(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7),
                    cursor.getInt(8)
            );
        }
        return null;
    }


    @Override
    public int getCount() {
        return taiKhoanDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        EditText edtTentaikhoan, edtMatkhau, edtSdt, edtEmail, edtDiachi;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view ==null )
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.edtTentaikhoan = (EditText) view.findViewById(R.id.edtTaikhoan);
            holder.edtMatkhau = (EditText) view.findViewById(R.id.edtMatkhau_dk);
            holder.edtSdt = (EditText) view.findViewById(R.id.edtSdt);
            holder.edtEmail = (EditText) view.findViewById(R.id.edtEmail);
            holder.edtDiachi = (EditText) view.findViewById(R.id.edtDiachi);
            view.setTag(holder);

        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        TaiKhoanDTO taiKhoanDTO = taiKhoanDTOList.get(i);
        holder.edtTentaikhoan.setText(taiKhoanDTO.getTENTK());
        holder.edtMatkhau.setText(taiKhoanDTO.getMATKHAU());
        holder.edtSdt.setText(taiKhoanDTO.getSDT());
        holder.edtEmail.setText(taiKhoanDTO.getEMAIL());
        holder.edtDiachi.setText(taiKhoanDTO.getDIACHI());
        id = taiKhoanDTO.getMATK();
        return view;
    }
}
