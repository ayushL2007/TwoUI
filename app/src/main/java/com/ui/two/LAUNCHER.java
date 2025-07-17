package com.ui.two;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;

public class LAUNCHER extends AppCompatActivity {
    private GenerateView generateView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_launcher);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setApps();
    }

    public void setApps(){
        GetApps getApps = new GetApps(this,1);
        Drawable[][] icons = getApps.getPackagesIcon();
        String[][] name = getApps.getName();

        LinearLayout superParentLayout = findViewById(R.id.scroll);
        generateView = new GenerateView(this,this);
        for (int i = 0; i < icons.length; i++) {
            if(name[i][0]!=null) {
                addApp_toList(i, icons[i][0], name[i][0], icons.length, superParentLayout);
            }
        }
    }

    /**
     * Id correlation with "i":
     * App Icon:ImageView:i+arr.length
     * App Name:TextView:i
     * App CheckBox:CheckBox:i+(arr.length*2)
     * LinearLayout:orientation=horizontal:1000+i
     *             :Contains the AppIcon, AppName, CheckBox
     * @param i
     * @param icon
     * @param AppName
     * @param length
     * @param superParentLayout
     */
    public void addApp_toList(int i, Drawable icon, String AppName,int length, LinearLayout superParentLayout){
        View view = generateView.generateVertStroke();
        LinearLayout parentLayout = setLinearLayout(1000 + i);
        setAppIcon(icon,i+length,parentLayout);
        setAppText(AppName, icon, i, parentLayout);
        setCheckBox(i+length*2, parentLayout);

        setOnClickListener(parentLayout, "selectApp", i, length);
        generateView.addViewToParent(parentLayout, superParentLayout);
        generateView.addViewToParent(view, superParentLayout);
    }

    public void setOnClickListener(View view, String methodName, int param1,int param2)  {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Method method = null;
                try {
                    method = com.ui.two.LAUNCHER.class.getMethod(methodName, new Class[]{Integer.class, Integer.class});
                    method.invoke(com.ui.two.LAUNCHER.this,param1, param2);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void selectApp(Integer i, Integer length){
        CheckBox checkBox = findViewById(length*2+i);
        checkBox.setChecked(!checkBox.isChecked());
        checkBox.invalidate();
        showAddButton();

    }

    private void showAddButton(){
        Button button = findViewById(R.id.hide_App);
        button.setVisibility(RelativeLayout.VISIBLE);
        button.invalidate();
    }

    private LinearLayout setLinearLayout(int id){
        int height = (int) generateView.dptopixel(50.0f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        return generateView.generateLinearLayout(layoutParams,id, LinearLayout.HORIZONTAL);
    }

    private void setAppIcon(Drawable drawable, int id, LinearLayout parentView){
        int r = (int) generateView.dptopixel(40);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(r,r);
        layoutParams.topMargin = r/5;
        generateView.generateImageView(layoutParams, id, null,drawable, parentView);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private void setAppText(@Nullable String text, @NonNull Drawable icon, int id, LinearLayout parentView){
        int r = (int) generateView.dptopixel(50);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(r*3, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.bottomMargin = layoutParams.leftMargin = layoutParams.topMargin
                = r/5;

        generateView.generateTextView(layoutParams, id, text, null, getDrawable(R.drawable.bg_1), parentView);
    }

    private void setCheckBox(int id, LinearLayout parentLayout){
        int pix_10 = (int) generateView.dptopixel(10);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                pix_10*3, pix_10*3);
        layoutParams.topMargin = pix_10;
        layoutParams.leftMargin = pix_10*5;
        generateView.generateCheckBox(id, parentLayout, layoutParams);
    }




    public Drawable scaleDrawable(Drawable drawable, int width, int height){
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        return new BitmapDrawable(getResources(), bitmap);
    }

}