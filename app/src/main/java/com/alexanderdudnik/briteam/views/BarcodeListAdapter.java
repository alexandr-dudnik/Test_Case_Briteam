package com.alexanderdudnik.briteam.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.alexanderdudnik.briteam.R;
import com.alexanderdudnik.briteam.data.BarcodesList;
import com.alexanderdudnik.briteam.data.DataItem;
import com.alexanderdudnik.briteam.data.ListItem;
import com.alexanderdudnik.briteam.extentions.DateKt;
import com.alexanderdudnik.briteam.presenters.BarcodeListPresenter;

import org.jetbrains.annotations.NotNull;

//****************************
//Adapter class for barcode list
//
//****************************
public class BarcodeListAdapter extends RecyclerView.Adapter<BarcodeListAdapter.ViewHolder> {
    private final BarcodesList list = new BarcodesList();
    private final BarcodeListPresenter presenter;

    public BarcodeListAdapter(BarcodeListPresenter presenter) {
        this.presenter = presenter;
    }

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
        holder.bind(list.getItems().get(position));
    }

    //get item by given position
    @Override
    public long getItemId(int position) {
        return position;
    }

    public ListItem getItem(int id){return list.getItems().get(id);}


    //add new item to list
    public void addItem(DataItem item){
        list.addItem(item);
        //force update of list
        notifyDataSetChanged();
    }

    //get size of list
    @Override
    public int getItemCount() {
        return list.getItems().size();
    }

    //Class describes viewholder
    public class ViewHolder extends RecyclerView.ViewHolder{
        private ListItem mItem;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            //set a clicklistener for created view
            itemView.setOnClickListener(v -> presenter.handleItemClick(mItem.getData()));

        }

        //bind item to view - fill view by values
        public void bind(ListItem item){
            //store item
            mItem = item;

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
