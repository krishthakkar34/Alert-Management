package com.example.alertmanagement;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class LazyAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;


    public LazyAdapter(Activity a, ArrayList d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);
        HashMap<String, String> itemobj = (HashMap<String, String>) data.get(position);
        TextView name=(TextView)vi.findViewById(R.id.name);
        TextView email=(TextView)vi.findViewById(R.id.email);
        ImageView image=(ImageView)vi.findViewById(R.id.showimage);
        email.setText(itemobj.get("email"));
        name.setText(itemobj.get("name"));
        String imagepath = "/data/user/0/com.example.crudapp/app_imageDir/"+itemobj.get("image");
        //Log.d("imagepath",imagepath);

        File imgFile = new  File(imagepath);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            image.setImageBitmap(myBitmap);
        };
        return vi;
    }
}
