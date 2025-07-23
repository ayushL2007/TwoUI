package com.ui.two;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GenerateView {
    AttributeSet attributeSet;
    final Context context;
    final Activity activity;

    public float scrn_height, scrn_width;

    GenerateView(Context context, Activity activity){
        this.context= context;
        this.activity = activity;
        setDisplayProp();
    }

    public void setDisplayProp(){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        scrn_height = displayMetrics.heightPixels;
        scrn_width = displayMetrics.widthPixels;
    }

    public LinearLayout generateLinearLayout(LinearLayout.LayoutParams layoutParams, int id, int orientation){
        LinearLayout childLayout = new LinearLayout(context);
        childLayout.setLayoutParams(layoutParams);
        childLayout.setId(id);
        childLayout.setOrientation(orientation);
        return childLayout;
    }

    public void generateImageView(LinearLayout.LayoutParams params, int id, String label, Drawable drawable, LinearLayout parentLayout){
        ImageView imageView = new ImageView(context);
        imageView.setImageDrawable(drawable);
        imageView.setId(id);
        imageView.setLayoutParams(params);
        imageView.invalidate();

        parentLayout.addView(imageView);
    }

    public void generateTextView(LinearLayout.LayoutParams layoutParams, int id, String label, Drawable icon,Drawable background, LinearLayout parentLayout, int Gravity){
        TextView textView = new TextView(context);
        textView.setId(id);
        textView.setText(label);
        textView.setLayoutParams(layoutParams);
        textView.setCompoundDrawablesWithIntrinsicBounds(icon,null, null,null);
        textView.setGravity(Gravity);
        //textView.setBackground(background);
        textView.invalidate();


        parentLayout.addView(textView);
    }

    public View generateVertStroke(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3);
        layoutParams.bottomMargin = 10;
        View view = new View(context);
        view.setLayoutParams(layoutParams);
        view.setBackground(activity.getDrawable(R.drawable.bg_2));
        return view;
    }

    public void generateCheckBox(int Id, LinearLayout parentLayout, LinearLayout.LayoutParams layoutParams){
        CheckBox checkBox = new CheckBox(context);
        checkBox.setId(Id);
        checkBox.setChecked(false);
        checkBox.setLayoutParams(layoutParams);
        checkBox.invalidate();

        parentLayout.addView(checkBox);
    }

    public float dptopixel(float dp){
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        float density = display.density;
        return density*dp + 0.5f;
    }

    public void addViewToParent(View child, LinearLayout Parent){
        Parent.addView(child);
    }
}
