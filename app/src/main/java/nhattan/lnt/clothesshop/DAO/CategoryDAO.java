package nhattan.lnt.clothesshop.DAO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import nhattan.lnt.clothesshop.DTO.CategoryDTO;
import nhattan.lnt.clothesshop.R;

public class CategoryDAO extends ArrayAdapter<CategoryDTO> {
    public CategoryDAO(@NonNull Context context, int resource, @NonNull List<CategoryDTO> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected, parent, false);
        TextView txt_Selected = convertView.findViewById(R.id.txtSelected);

        CategoryDTO categoryDTO = this.getItem(position);
        if (categoryDTO != null){
            txt_Selected.setText(categoryDTO.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        TextView txt_Category = convertView.findViewById(R.id.txtCategory);

        CategoryDTO categoryDTO = this.getItem(position);
        if (categoryDTO != null){
            txt_Category.setText(categoryDTO.getName());
        }
        return convertView;
    }
}
