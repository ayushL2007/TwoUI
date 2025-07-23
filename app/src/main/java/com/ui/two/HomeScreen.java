package com.ui.two;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Method;

public class HomeScreen extends AppCompatActivity {
    private GenerateView generateView;
    FileIO fileIO;
    SideLineTask sideLineTask;
    String[][] appNames, packageNames;
    Drawable[][] icons;

    int start,end;

    int length;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        GetApps getApps = new GetApps(this, 4);
        appNames = getApps.getName();
        packageNames = getApps.getPackagesName();
        icons = getApps.getPackagesIcon();
        length = getApps.getLength();
        
        //showLoadingScreen();
        generateView = new GenerateView(this, this);
        loadApps(2*length/3);
        loadApps(length/3);
        loadApps(0);
        setGesture();
    }

    protected void setGesture(){
        Runnable runnable_left = new Runnable() {
            @Override
            public void run() {

            }
        }
        SlideLayout slideLayout = findViewById(R.id.main);
        slideLayout.setOnGestureListener(new Runnable[]{null, null,null,null});
    }
    //TODO
    //Complete the code for home screen
    protected void loadApps(int count_start){
        LinearLayout parentLayout = findViewById(R.id.main);
        for (int i = count_start; i < icons.length/3; i++) {
            LinearLayout horizontalLayout = setLinearLayout(length*4+i, LinearLayout.HORIZONTAL,-1, -2);
            for (int j = 0; j < 4 && i+j<length; j++) {
                int n = i*4+j;
                LinearLayout verticalLayout = setLinearLayout(length*3+n, LinearLayout.VERTICAL, (int) (generateView.scrn_width/4), -2);
                setAppIcon(icons[i][j], length*2+n, verticalLayout);
                setAppText(appNames[i][j], length+n, verticalLayout);

                int finalI = i;
                int finalJ = j;
                verticalLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        launchApp(finalI, finalJ);
                    }
                });

                generateView.addViewToParent(verticalLayout, horizontalLayout);
            }
            generateView.addViewToParent(horizontalLayout, parentLayout);
            end=i;
        }
    }


    private LinearLayout setLinearLayout(int id, int orientation, int width, int height){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.leftMargin = 10;
        return generateView.generateLinearLayout(layoutParams,id, orientation);
    }

    private void setAppIcon(Drawable drawable, int id, LinearLayout parentView){
        int r = (int) generateView.dptopixel(50);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(r,r);
        layoutParams.topMargin = layoutParams.leftMargin= r/5;
        generateView.generateImageView(layoutParams, id, null,drawable, parentView);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private void setAppText(@Nullable String text, int id, LinearLayout parentView){
        int r = (int) generateView.dptopixel(50);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.bottomMargin = layoutParams.topMargin
                = r/5;

        generateView.generateTextView(layoutParams, id, text, null, getDrawable(R.drawable.bg_1), parentView, Gravity.CENTER_HORIZONTAL);
    }
    public void launchApp(Integer i, Integer j){
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageNames[i][j]);
        startActivity( launchIntent );
    }
}