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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nhattan.lnt.clothesshop.DTO.GopYDTO;
import nhattan.lnt.clothesshop.DTO.TaiKhoanDTO;
import nhattan.lnt.clothesshop.R;

public class QuanLyGopYAdapter extends RecyclerView.Adapter<QuanLyGopYAdapter.Viewholder>{
    ArrayList<GopYDTO> listGopy;
    Fragment context;
    public static int position;

    public static int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public QuanLyGopYAdapter( Fragment context,ArrayList<GopYDTO> listGopy) {
        this.listGopy = listGopy;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_manage_gopy,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        GopYDTO gopYDTO = listGopy.get(position);

//        Picasso.with(context.getActivity()).load()
//        .placeholder(R.drawable.logo)
//        .error(R.drawable.logo).into(holder.imgV_Hinh_qlgopy);
        holder.imgV_Hinh_qlgopy.setImageResource(R.drawable.logo);
        holder.txtV_Tentaikhoan_qlgopy.setText(gopYDTO.getTENTK());
        holder.txtV_Noidung_qlgopy.setText(gopYDTO.getNOIDUNG());

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
        if(listGopy != null){
            return listGopy.size();
        }
        return 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView txtV_Tentaikhoan_qlgopy, txtV_Noidung_qlgopy;
        ImageView imgV_Hinh_qlgopy;
        CardView viewholder_qlgopy;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txtV_Tentaikhoan_qlgopy = itemView.findViewById(R.id.txtV_Tentaikhoan_qlgopy);
            txtV_Noidung_qlgopy = itemView.findViewById(R.id.txtV_Noidung_qlgopy);
            imgV_Hinh_qlgopy = itemView.findViewById(R.id.imgV_Hinh_qlgopy);
            viewholder_qlgopy = itemView.findViewById(R.id.viewholder_qlgopy);

            itemView.setOnCreateContextMenuListener(this);

            viewholder_qlgopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewholder_qlgopy.showContextMenu();
                }
            });
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(menu.NONE, R.id.iSua,
                    menu.NONE, "Xem thông tin bài góp ý");
            menu.add(menu.NONE, R.id.iXoa,
                    menu.NONE, "Xóa bài góp ý");
        }
    }
}
