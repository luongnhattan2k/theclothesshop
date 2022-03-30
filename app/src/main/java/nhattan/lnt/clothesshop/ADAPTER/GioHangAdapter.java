package nhattan.lnt.clothesshop.ADAPTER;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nhattan.lnt.clothesshop.DTO.GioHangDTO;
import nhattan.lnt.clothesshop.R;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {

    Context context;
    List<GioHangDTO> gioHangList;

    public GioHangAdapter(Context context, List<GioHangDTO> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView img_Hinhanh;
        TextView txt_Tensp, txt_Soluongsp, txt_Giasp;
        ImageButton img_Themslsp, img_Truslsp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_Hinhanh = itemView.findViewById(R.id.imageHinhCustom);
            txt_Tensp = itemView.findViewById(R.id.imageHinhCustom);
            txt_Soluongsp = itemView.findViewById(R.id.imageHinhCustom);
            txt_Giasp = itemView.findViewById(R.id.imageHinhCustom);
            img_Hinhanh = itemView.findViewById(R.id.imageHinhCustom);
            img_Hinhanh = itemView.findViewById(R.id.imageHinhCustom);

        }
    }
}
