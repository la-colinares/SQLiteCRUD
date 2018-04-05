package com.app.phonebook.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.phonebook.Model.ContactModel;
import com.app.phonebook.R;
import com.app.phonebook.Utils.ContactViewHolder;
import com.app.phonebook.Utils.OnTapListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> {

    List<ContactModel> contactModels = Collections.emptyList();
    private OnTapListener onTapListener;

    public ContactAdapter(List<ContactModel> reminderModels) {
        this.contactModels = reminderModels;
    }

    public void refreshList(ArrayList<ContactModel> contactModelArrayList){
        this.contactModels.clear();
        this.contactModels.addAll(contactModelArrayList);
        notifyDataSetChanged();
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_home, parent, false);

        return new ContactViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, final int position) {

        String gender = contactModels.get(position).getGender();

        if(gender.equals("Male")){
            holder.genderImage.setImageResource(R.drawable.male);
        }else {
            holder.genderImage.setImageResource(R.drawable.female);
        }

        holder.txtContactName.setText(contactModels.get(position).getName());
        holder.txtContactNumber.setText(contactModels.get(position).getNumber());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onTapListener != null){
                    onTapListener.onTapView(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactModels.size();
    }

    public void setOnTapListener(OnTapListener onTapListener){
        this.onTapListener = onTapListener;
    }

}
