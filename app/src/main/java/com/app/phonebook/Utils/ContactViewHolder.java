package com.app.phonebook.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.phonebook.R;

/**
 * Created by Colinares on 4/6/2018.
 */
public class ContactViewHolder extends RecyclerView.ViewHolder{

    public TextView txtContactName, txtContactNumber;
    public ImageView genderImage;

    public ContactViewHolder(View itemView){
        super(itemView);

        txtContactName = (TextView) itemView.findViewById(R.id.custom_contact_name);
        txtContactNumber = (TextView) itemView.findViewById(R.id.custom_contact_number);
        genderImage = (ImageView) itemView.findViewById(R.id.gender_image);
    }

}
