package com.ui.two;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GenerateView {
    AttributeSet attributeSet;
    final Context context;
    final Activity activity;

    GenerateView(Context context, Activity activity){
        this.context= context;
        this.activity = activity;
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

    public void generateTextView(LinearLayout.LayoutParams layoutParams, int id, String label, Drawable icon,Drawable background, LinearLayout parentLayout){
        TextView textView = new TextView(context);
        textView.setCompoundDrawables(icon, null,null,null);
        textView.setId(id);
        textView.setText(label);
        textView.setLayoutParams(layoutParams);
        textView.setBackground(background);
        textView.invalidate();


        parentLayout.addView(textView);
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
