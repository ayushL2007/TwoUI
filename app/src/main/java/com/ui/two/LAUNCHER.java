package com.ui.two;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
            LinearLayout parentLayout = setLinearLayout(1000+i);
            setApp(name[i][0], icons[i][0], i, parentLayout);
            generateView.addViewToParent(parentLayout, superParentLayout);

        }
    }
    private LinearLayout setLinearLayout(int id){
        int height = (int) generateView.dptopixel(50.0f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        return generateView.generateLinearLayout(layoutParams,id, LinearLayout.HORIZONTAL);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setApp(@Nullable String text, @NonNull Drawable icon, int id, LinearLayout parentView){
        int r = (int) generateView.dptopixel(50);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,r);
        generateView.generateTextView(layoutParams, id, text, icon,getDrawable(R.drawable.bg_1), parentView);
    }


}