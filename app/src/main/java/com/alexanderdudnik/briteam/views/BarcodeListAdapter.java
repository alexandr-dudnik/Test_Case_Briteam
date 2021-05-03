package com.alexanderdudnik.briteam.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alexanderdudnik.briteam.R;
import com.alexanderdudnik.briteam.data.BarcodesList;
import com.alexanderdudnik.briteam.data.ListItem;
import com.alexanderdudnik.briteam.extentions.DateKt;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

//****************************
//Adapter class for barcode list
//
//****************************
public class BarcodeListAdapter extends RecyclerView.Adapter<BarcodeListAdapter.ViewHolder> {
    private final List<ListItem> list = new ArrayList<>();


    //create viewholder
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.barcode_list_item, parent, false);
        return new ViewHolder(view);
    }

    //bind data to viewholder
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    //get item by given position
    @Override
    public long getItemId(int position) {
        return position;
    }

    public ListItem getItem(long id){return list.get((int)id);}


    //set full list
    public void setList(BarcodesList pList){

        DiffUtil.DiffResult diffRes = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return list.size();
            }

            @Override
            public int getNewListSize() {
                return pList.getItems().size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return list.get(oldItemPosition).hashCode() == pList.getItems().get(newItemPosition).hashCode();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return list.get(oldItemPosition).equals(pList.getItems().get(newItemPosition));
            }
        }, true);

        list.clear();
        list.addAll(pList.getItems());

        diffRes.dispatchUpdatesTo(this);
    }



    //get size of list
    @Override
    public int getItemCount() {
        return list.size();
    }

    //Class describes viewholder
    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }

        //bind item to view - fill view by values
        public void bind(ListItem item){

            //find views
            AppCompatTextView tv_barcode = this.itemView.findViewById(R.id.tv_barcode);
            AppCompatTextView tv_type = this.itemView.findViewById(R.id.tv_type);
            AppCompatTextView tv_date = this.itemView.findViewById(R.id.tv_date);

            //apply data to views
            tv_barcode.setText(item.getData().getBarcode());
            tv_barcode.setTextColor(item.getData().getColorValue());
            tv_type.setText(item.getData().getType());
            tv_date.setText(DateKt.format(item.getDateTime(),"dd.MM.yy HH:mm:ss"));
        }


    }
}
