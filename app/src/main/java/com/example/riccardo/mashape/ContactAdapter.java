package com.example.riccardo.mashape;

/**
 * Created by riccardo on 27/07/15.
 */
import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<ContactInfo> contactList;
    public Context context;


    public ContactAdapter(List<ContactInfo> contactList,Context c) {
        this.contactList = contactList;
        this.context=c;
    }




    @Override
    public int getItemCount() {
        return contactList.size();
    }











    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.context) .build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        final ContactInfo ci = contactList.get(i);
        contactViewHolder.vEmail.setText(ci.desc);
        contactViewHolder.vTitle.setText(ci.name + " Of " + ci.owner);


        imageLoader.displayImage(ci.image_api, contactViewHolder.vImage);
        contactViewHolder.vImage.setOnClickListener(new View.OnClickListener(){
           

            public void onClick(View v){
                Intent intent;
                intent = new Intent(context, Browser.class);
                intent.putExtra("name",ci.name);
                intent.putExtra("links",ci.links);

                context.startActivity(intent);
            }
        });

    }



    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected ImageView vImage;
        protected TextView vEmail;
        protected TextView vTitle;

        public ContactViewHolder(View v) {
            super(v);
            vEmail = (TextView)  v.findViewById(R.id.tv_des_nature);
            vTitle = (TextView) v.findViewById(R.id.tv_nature);
            vImage=(ImageView) v.findViewById(R.id.img_thumbnail);
        }
    }
}