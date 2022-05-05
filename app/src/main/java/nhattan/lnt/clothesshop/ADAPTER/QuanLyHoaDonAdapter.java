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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import nhattan.lnt.clothesshop.DTO.HoaDonDTO;
import nhattan.lnt.clothesshop.R;

public class QuanLyHoaDonAdapter extends RecyclerView.Adapter<QuanLyHoaDonAdapter.Viewholder>{

    ArrayList<HoaDonDTO> listHoaDon;
    Fragment context;
    public static int position;

    public static int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public QuanLyHoaDonAdapter( Fragment context,ArrayList<HoaDonDTO> listHoaDon) {
        this.listHoaDon = listHoaDon;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_quanly_hoadon,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        HoaDonDTO hoaDonDTO = listHoaDon.get(position);

        holder.img_Hinhanh.setImageResource(R.drawable.logo);
        holder.txt_Mahoadon.setText(("Mã hóa đơn: " + "FASH" + hoaDonDTO.getIDHOADON()));
        holder.txt_Ngaydat.setText(("Ngày đặt: " + hoaDonDTO.getNGAYDAT()));
        holder.txt_Tongtien.setText(("Tổng tiền: " + (NumberFormat.getNumberInstance(Locale.US).format(hoaDonDTO.getTONGTIEN()) + " VNĐ")));

        if (hoaDonDTO.getTINHTRANG() == 1) {
            holder.txt_Tinhtranghoadon.setText("Tình trạng: Chờ duyệt đơn");
        } else if (hoaDonDTO.getTINHTRANG() == 2) {
            holder.txt_Tinhtranghoadon.setText("Tình trạng: Đang đóng hàng");
        } else if (hoaDonDTO.getTINHTRANG() == 3) {
            holder.txt_Tinhtranghoadon.setText("Tình trạng: Xuất đơn hàng");
        } else if (hoaDonDTO.getTINHTRANG() == 4) {
            holder.txt_Tinhtranghoadon.setText("Tình trạng: Đang giao");
        } else if (hoaDonDTO.getTINHTRANG() == 5) {
            holder.txt_Tinhtranghoadon.setText("Tình trạng: Giao thành công");
        } else if (hoaDonDTO.getTINHTRANG() == 6) {
            holder.txt_Tinhtranghoadon.setText("Tình trạng: Đã nhận hàng");
        } else if (hoaDonDTO.getTINHTRANG() == 7) {
            holder.txt_Tinhtranghoadon.setText("Tình trạng: Đã gửi đánh giá");
        } else {
            holder.txt_Tinhtranghoadon.setText("Tình trạng: Đã hủy");
        }

        holder.itemView .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPosition(holder.getPosition());
                holder.itemView .performLongClick();
                //Toast.makeText(context, "hi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listHoaDon != null){
            return listHoaDon.size();
        }
        return 0;
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        ImageView img_Hinhanh;
        TextView txt_Mahoadon, txt_Ngaydat, txt_Tongtien, txt_Tinhtranghoadon;
        CardView product_hoadon;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txt_Mahoadon = itemView.findViewById(R.id.txtMahoadon_qlhd);
            txt_Ngaydat = itemView.findViewById(R.id.txtNgaydathang_qlhd);
            txt_Tongtien = itemView.findViewById(R.id.txtTongtienhoadon_qlhd);
            txt_Tinhtranghoadon = itemView.findViewById(R.id.txtTinhtrangnhoadon_qlhd);
            img_Hinhanh = itemView.findViewById(R.id.imageHinhCustom_qlhd);
            product_hoadon = itemView.findViewById(R.id.product_hoadon);

            itemView.setOnCreateContextMenuListener(this);

            product_hoadon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    product_hoadon.showContextMenu();
                }
            });
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(menu.NONE, R.id.iXemhd,
                    menu.NONE, "Xem hóa đơn");
            menu.add(menu.NONE, R.id.iXoahd,
                    menu.NONE, "Xóa hóa đơn");
        }
    }
}
