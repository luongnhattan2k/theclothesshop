package nhattan.lnt.clothesshop.ADAPTER;

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

import nhattan.lnt.clothesshop.DTO.TinTucDTO;
import nhattan.lnt.clothesshop.R;

public class QuanLyTinTucAdapter extends RecyclerView.Adapter<QuanLyTinTucAdapter.Viewholder>{

    ArrayList<TinTucDTO> listTinTuc;
    Fragment context;
    public static int position;

    public static int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public QuanLyTinTucAdapter( Fragment context,ArrayList<TinTucDTO> listTinTuc) {
        this.listTinTuc = listTinTuc;
        this.context = context;
    }

    @NonNull
    @Override
    public QuanLyTinTucAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_custom_tintuc,parent,false);

        return new QuanLyTinTucAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuanLyTinTucAdapter.Viewholder holder, int position) {
        TinTucDTO tinTucDTO = listTinTuc.get(position);
        holder.txt_Tieude.setText(tinTucDTO.getTIEUDE());
        holder.txt_Noidung.setText(tinTucDTO.getNOIDUNG());
        holder.txt_Ngaydang.setText(tinTucDTO.getNGAYDANG());

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
        if(listTinTuc != null){
            return listTinTuc.size();
        }
        return 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView img_Hinhanh;
        TextView txt_Tieude, txt_Noidung, txt_Ngaydang;
        CardView viewholder_qltintuc;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txt_Tieude = itemView.findViewById(R.id.txtV_Tieude);
            txt_Noidung = itemView.findViewById(R.id.txtV_Noidung);
            txt_Ngaydang = itemView.findViewById(R.id.txtV_Ngaydang);
            viewholder_qltintuc = itemView.findViewById(R.id.viewholder_qltintuc);

            itemView.setOnCreateContextMenuListener(this);

            viewholder_qltintuc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewholder_qltintuc.showContextMenu();
                }
            });
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(menu.NONE, R.id.iSua,
                    menu.NONE, "Xem tin tức");
            menu.add(menu.NONE, R.id.iXoa,
                    menu.NONE, "Xóa tin tức");
        }
    }
}
