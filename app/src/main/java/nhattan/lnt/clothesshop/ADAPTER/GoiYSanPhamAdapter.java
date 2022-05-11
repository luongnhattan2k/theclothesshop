package nhattan.lnt.clothesshop.ADAPTER;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import nhattan.lnt.clothesshop.DTO.SanPhamDTO;
import nhattan.lnt.clothesshop.R;

public class GoiYSanPhamAdapter extends RecyclerView.Adapter<GoiYSanPhamAdapter.viewholder>{
    ArrayList<SanPhamDTO> listSanPham;
    private Context context;

    public GoiYSanPhamAdapter(ArrayList<SanPhamDTO> listSanPham) {
        this.listSanPham = listSanPham;
    }

    @NonNull
    @Override
    public GoiYSanPhamAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_goiysanpham,parent,false);

        return new GoiYSanPhamAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoiYSanPhamAdapter.viewholder holder, int position) {
        SanPhamDTO sanPhamDTO = listSanPham.get(holder.getAdapterPosition());

        Bitmap bitmap = BitmapFactory.decodeByteArray(sanPhamDTO.getImageSP(),0,sanPhamDTO.getImageSP().length);
        holder.product_image_goiy.setImageBitmap(bitmap);
        holder.product_name_goiy.setText(sanPhamDTO.getTenSP());
        holder.product_price_goiy.setText((NumberFormat.getNumberInstance(Locale.US).format(sanPhamDTO.getGiaSP()) + " VNĐ"));

//        holder.product_layout_goiy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context.getApplicationContext(), ProductionActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("idsanphamgoiy", (Serializable) sanPhamDTO);
//                intent.putExtras(bundle);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (listSanPham!=null){
            return listSanPham.size();
        }
        return 0;
    }

    public class viewholder extends RecyclerView.ViewHolder{
        ImageView product_image_goiy;
        TextView product_name_goiy,product_price_goiy;
        CardView product_layout_goiy;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            product_image_goiy = itemView.findViewById(R.id.product_image_goiy);
            product_name_goiy = itemView.findViewById(R.id.product_name_goiy);
            product_price_goiy = itemView.findViewById(R.id.product_price_goiy);
            product_layout_goiy = itemView.findViewById(R.id.product_layout_goiy);
        }
    }
}
