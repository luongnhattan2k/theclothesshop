package nhattan.lnt.clothesshop.ADAPTER;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import nhattan.lnt.clothesshop.DTO.BinhLuanDTO;
import nhattan.lnt.clothesshop.R;

public class BinhLuanAdapter extends RecyclerView.Adapter<BinhLuanAdapter.viewholder>{
    ArrayList<BinhLuanDTO> listBL;

    public BinhLuanAdapter(ArrayList<BinhLuanDTO> listBL) {
        this.listBL = listBL;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_custom_binhluan,parent,false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        BinhLuanDTO binhLuanDTO = listBL.get(holder.getAdapterPosition());

        Bitmap bitmap = BitmapFactory.decodeByteArray(binhLuanDTO.getHinhdaidien(),0,binhLuanDTO.getHinhdaidien().length);
        holder.img_Hinhdaidien_binhluan.setImageBitmap(bitmap);
        holder.txt_NoiDung_binhluan.setText(binhLuanDTO.getNoidungBL());
        holder.txtV_ThoiGian_binhluan.setText(String.valueOf(binhLuanDTO.getThoiGianBL()));
        holder.txtV_Tennguoidung_binhluan.setText(String.valueOf(binhLuanDTO.getTENTAIKHOAN()));
        holder.txtV_Phanloai_binhluan.setText("Phân loại: " + binhLuanDTO.getSize());
        holder.rb_saodanhgia_binhluan.setRating(binhLuanDTO.getDanhgia());
    }

    @Override
    public int getItemCount() {
        if (listBL!=null){
            return listBL.size();
        }
        return 0;
    }

    public class viewholder extends RecyclerView.ViewHolder{
        CircleImageView img_Hinhdaidien_binhluan;
        TextView txt_NoiDung_binhluan,txtV_ThoiGian_binhluan, txtV_Tennguoidung_binhluan, txtV_Phanloai_binhluan;
        RatingBar rb_saodanhgia_binhluan;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            img_Hinhdaidien_binhluan = itemView.findViewById(R.id.img_Hinhdaidien_binhluan);
            txt_NoiDung_binhluan = itemView.findViewById(R.id.txt_NoiDung_binhluan);
            txtV_ThoiGian_binhluan = itemView.findViewById(R.id.txtV_ThoiGian_binhluan);
            rb_saodanhgia_binhluan = itemView.findViewById(R.id.rb_saodanhgia_binhluan);
            txtV_Tennguoidung_binhluan = itemView.findViewById(R.id.txtV_Tennguoidung_binhluan);
            txtV_Phanloai_binhluan = itemView.findViewById(R.id.txtV_Phanloai_binhluan);
        }
    }
}
