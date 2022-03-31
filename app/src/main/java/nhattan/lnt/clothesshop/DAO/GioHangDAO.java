package nhattan.lnt.clothesshop.DAO;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.List;

import nhattan.lnt.clothesshop.DTO.GioHangDTO;
import nhattan.lnt.clothesshop.Database.Database;
import nhattan.lnt.clothesshop.FragmentApp.HomeFragment;
import nhattan.lnt.clothesshop.HomeActivity;
import nhattan.lnt.clothesshop.R;

public class GioHangDAO extends BaseAdapter {

    private Fragment context;
    private int layout;
    public static List<GioHangDTO> sanPhamGioHangList;
    int id;
    Database database;
    GioHangDTO gioHangDTO;
    int SLM = 0;

    public GioHangDAO(Fragment context, int layout, List<GioHangDTO> sanPhamGioHangList) {
        this.context = context;
        this.layout = layout;
        this.sanPhamGioHangList = sanPhamGioHangList;
    }

    @Override
    public int getCount() {
        return sanPhamGioHangList.size();
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
        ImageView img_HinhAnh, img_Xoasp_gh;
        ImageButton img_Themslsp, img_Truslsp;
        CheckBox checkBox;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater;
            inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txt_TenSP = convertView.findViewById(R.id.txtTensp);
            holder.txt_GiaSP = convertView.findViewById(R.id.txtThanhtien);
            holder.txt_SLSP = convertView.findViewById(R.id.txtSoluong) ;
            holder.img_HinhAnh = convertView.findViewById(R.id.imageHinhCustom);
            holder.img_Xoasp_gh = convertView.findViewById(R.id.img_Xoasp_gh);
            holder.img_Themslsp = convertView.findViewById(R.id.img_Themslsp_gh);
            holder.img_Truslsp = convertView.findViewById(R.id.img_Truslsp_gh);
            holder.checkBox = convertView.findViewById(R.id.checkBox);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GioHangDTO gioHang = sanPhamGioHangList.get(position);
        holder.txt_TenSP.setText(gioHang.getTENSANPHAM());
        holder.txt_GiaSP.setText(String.valueOf(gioHang.getTHANHTIEN()) + " VNĐ" );
        holder.txt_SLSP.setText(String.valueOf(gioHang.getSOLUONG()) );

        holder.img_Xoasp_gh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity());
                builder.setTitle("Thông Báo");
                builder.setMessage("Bạn có chắc muốn xóa nó hay không ?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HomeFragment.database.XoaSPGH(gioHang.getIDGIOHANG());
                        Toast.makeText(context.getContext(), "Xóa thành công !", Toast.LENGTH_SHORT).show();
                        Intent iGiohang = new Intent(context.getActivity(), HomeActivity.class);
                        iGiohang.putExtra("DenGioHang", R.id.nav_mycart);
                        context.startActivity(iGiohang);
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
        });

        id = gioHang.getIDGIOHANG();

        // chuyen byte[] -> ve bitmap
        byte[] hinhAnh = gioHang.getImageSP();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0, hinhAnh.length);
        holder.img_HinhAnh.setImageBitmap(bitmap);

        return convertView;
    }

}
