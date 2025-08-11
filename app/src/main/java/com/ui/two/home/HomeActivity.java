package com.ui.two.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ui.two.FileStructure.FileHandler;
import com.ui.two.GenerateView;
import com.ui.two.HideApps;
import com.ui.two.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private FileHandler fileHandler;

    private GenerateView generateView;
    private static ArrayList<Object[]> HIDDEN_APP_INFO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fileHandler = new FileHandler(this);
        generateView = new GenerateView(this);
        HIDDEN_APP_INFO = fileHandler.getHiddenApps();

        if (HIDDEN_APP_INFO==null)  return;
        setUpApps();
    }

    public void setUpApps(){
        int length = HIDDEN_APP_INFO.size();
        LinearLayout layout = findViewById(R.id.main_page);
        for (int i = 0; i < length; i++) {
            ViewGroup[] childs = new ViewGroup[4];
            for (int j = 0; j < 4; j++) {
                childs[j] = addAppLayout(i,length, (Drawable) HIDDEN_APP_INFO.get(i)[2]
                                                ,(String) HIDDEN_APP_INFO.get(i)[0]);

                i++;
            }

            setupHorizontalLayout(length, i, childs, layout);
        }
    }

    public void setupHorizontalLayout(int length, int uid, ViewGroup[] childs, ViewGroup parentLayout){
        LinearLayout horizontalLayout = generateView.generateLinearLayout(new LinearLayout.LayoutParams(-1,-2),
                                                                        length*3+uid,LinearLayout.HORIZONTAL);
        for (ViewGroup child:childs) {
            horizontalLayout.addView(child);
        }
        horizontalLayout.invalidate();

        if (parentLayout!=null) parentLayout.addView(horizontalLayout);

    }

    public ViewGroup addAppLayout(int uid, int length, Drawable appIcon, String AppLabel){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2,-2);
        LinearLayout layout = generateView.generateLinearLayout(layoutParams,
                                                            length*2+uid,LinearLayout.VERTICAL);

        generateView.generateTextView(layoutParams,uid,AppLabel,null,null,layout, Gravity.CENTER_HORIZONTAL);
        generateView.generateImageView(layoutParams,uid+length,null,appIcon,layout);

        layout.setOnClickListener((View.OnClickListener) v -> {launchHidden((String) HIDDEN_APP_INFO.get(uid)[1]);});
        layout.invalidate();

        return layout;
    }

    public void launchHidden(String packageName){
        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
        startActivity(intent);
    }

    public void addImage(){

    }

    public void hideApps(View view){
        Intent intent =
                new Intent(this, HideApps.class);
        startActivity(intent);
    }
}