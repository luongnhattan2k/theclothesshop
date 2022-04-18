package nhattan.lnt.clothesshop.ADAPTER;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nhattan.lnt.clothesshop.DTO.TaiKhoanDTO;
import nhattan.lnt.clothesshop.Login;
import nhattan.lnt.clothesshop.R;

public class QuanLyTaiKhoanAdapter extends RecyclerView.Adapter<QuanLyTaiKhoanAdapter.Viewholder>{
    ArrayList<TaiKhoanDTO> listTaiKhoan;
    Fragment context;
    public static int position;

    public static int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public QuanLyTaiKhoanAdapter( Fragment context,ArrayList<TaiKhoanDTO> listTaiKhoan) {
        this.listTaiKhoan = listTaiKhoan;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_manage_user,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        TaiKhoanDTO taiKhoan = listTaiKhoan.get(position);

        if (Login.taiKhoanDTO.getHINHANH() == null){
            holder.imgV_Hinh_qlTaikhoan.setImageResource(R.drawable.logo);
        } else {
            // chuyen byte[] -> ve bitmap
            byte[] hinhAnh = taiKhoan.getHINHANH();
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0, hinhAnh.length);
            holder.imgV_Hinh_qlTaikhoan.setImageBitmap(bitmap);
        }
        holder.txtV_Tentaikhoan_qlTaikhoan.setText(taiKhoan.getTENTK());
        if (taiKhoan.getLOAITK() == 2) {
            holder.txtV_loaitk_qlTaikhoan.setText("VIP");
        } else {
            holder.txtV_loaitk_qlTaikhoan.setText("Thường");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPosition(holder.getPosition());
                holder.itemView .performLongClick();
                //Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show();
            }
        });

//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                setPosition(holder.getPosition());
//                return false;
//            }
//        });

    }



    @Override
    public int getItemCount() {
        if(listTaiKhoan != null){
            return listTaiKhoan.size();
        }
        return 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        ImageView imgV_Hinh_qlTaikhoan;
        TextView txtV_Tentaikhoan_qlTaikhoan, txtV_loaitk_qlTaikhoan;
        CardView viewholder_qltaikhoan;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtV_Tentaikhoan_qlTaikhoan = itemView.findViewById(R.id.txtV_Noidung_qltaikhoan);
            txtV_loaitk_qlTaikhoan = itemView.findViewById(R.id.txtV_loaitk_qlTaikhoan);
            imgV_Hinh_qlTaikhoan = itemView.findViewById(R.id.imgV_Hinh_qltaikhoan);
            viewholder_qltaikhoan = itemView.findViewById(R.id.viewholder_qltaikhoan);

            itemView.setOnCreateContextMenuListener(this);

            viewholder_qltaikhoan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewholder_qltaikhoan.showContextMenu();
                }
            });
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(menu.NONE, R.id.iSua,
                    menu.NONE, "Sửa Thông Tin Tài Khoản");
            menu.add(menu.NONE, R.id.iXoa,
                    menu.NONE, "Xóa Tài Khoản");
        }
    }
}
