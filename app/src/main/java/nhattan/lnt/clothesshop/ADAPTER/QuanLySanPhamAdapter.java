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

import nhattan.lnt.clothesshop.DTO.SanPhamDTO;
import nhattan.lnt.clothesshop.R;

public class QuanLySanPhamAdapter extends RecyclerView.Adapter<QuanLySanPhamAdapter.Viewholder>{
    ArrayList<SanPhamDTO> listSanPham;
    Fragment context;
    public static int position;

    public static int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public QuanLySanPhamAdapter( Fragment context,ArrayList<SanPhamDTO> listSanPham) {
        this.listSanPham = listSanPham;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        SanPhamDTO sanPhamDTO = listSanPham.get(position);
        // chuyen byte[] -> ve bitmap
        byte[] hinhAnh = sanPhamDTO.getImageSP();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhAnh,0, hinhAnh.length);
        holder.img_Hinhanh.setImageBitmap(bitmap);
        holder.txt_Tensp.setText(sanPhamDTO.getTenSP());
        holder.txt_Giasp.setText(String.valueOf(sanPhamDTO.getGiaSP()) + " VND");

        holder.itemView .setOnClickListener(new View.OnClickListener() {
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
        if(listSanPham != null){
            return listSanPham.size();
        }
        return 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        ImageView img_Hinhanh;
        TextView txt_Tensp, txt_Giasp;
        CardView product_layout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txt_Tensp = itemView.findViewById(R.id.product_name);
            txt_Giasp = itemView.findViewById(R.id.product_price);
            img_Hinhanh = itemView.findViewById(R.id.product_image);
            product_layout = itemView.findViewById(R.id.product_layout);

            itemView.setOnCreateContextMenuListener(this);

            product_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    product_layout.showContextMenu();
                }
            });
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(menu.NONE, R.id.iSua,
                    menu.NONE, "Sửa Sản Phẩm");
            menu.add(menu.NONE, R.id.iXoa,
                    menu.NONE, "Xóa Sản Phẩm");
        }

    }
}
