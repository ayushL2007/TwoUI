package com.ui.two;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
        GetApps getApps = new GetApps(this);
        Drawable[][] icons = getApps.getPackagesIcon();

        LinearLayout superParentLayout = findViewById(R.id.main);
        generateView = new GenerateView(this,this);

        for (int i = 0; i < icons.length; i++) {
            LinearLayout parentLayout = setLinearLayout(icons.length*4+i);
            for (int j = 0; j < icons[i].length; j++) {
                    setIcon(null, icons[i][j], 100, parentLayout);

            }
            generateView.addViewToParent(parentLayout, superParentLayout);
        }
    }
    private LinearLayout setLinearLayout(int id){
        int height = (int) generateView.dptopixel(50.0f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        return generateView.generateLinearLayout(layoutParams,id, LinearLayout.HORIZONTAL);
    }

    private void setIcon(@Nullable String text, @NonNull Drawable icon, int id, LinearLayout parentView){
        int r = (int) generateView.dptopixel(50);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(r, r);
        generateView.generateView(layoutParams, id, text, icon, parentView);
    }


}