package com.ui.two;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ui.two.FileStructure.FileHandler;
import com.ui.two.FileStructure.FileIO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * {@link HideApps#onCreate(Bundle)} -> {@link HideApps#setApps()} -> 
 * {@link HideApps#addApp_toList(int, Drawable, String, int, LinearLayout)}
 *                 |-->{@link HideApps#setAppIcon(Drawable, int, LinearLayout)}
 *                |--->{@link HideApps#setAppText(String, int, LinearLayout)}
 *               |---->{@link HideApps#setCheckBox(int, LinearLayout)}
 */
public class HideApps extends AppCompatActivity {

    final static public byte[] REGEX_FOR_DRAWABLE = "###".getBytes();

    private GenerateView generateView;
    Drawable[][] icons;
    String[][] names, packageNames;
    FileHandler fileHandler;
    SideLineTask sideLineTask;
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

        sideLineTask = new SideLineTask(this, this);
        fileHandler = new FileHandler(this);
        setApps();
    }

    public void setApps(){
        GetApps getApps = new GetApps(this,1);
        icons = getApps.getPackagesIcon();
        names = getApps.getName();
        packageNames = getApps.getPackagesName();
        LinearLayout superParentLayout = findViewById(R.id.scroll);
        generateView = new GenerateView(this);
        for (int i = 0; i < icons.length; i++) {
            if(names[i][0]!=null) {
                addApp_toList(i, icons[i][0], names[i][0], icons.length, superParentLayout);
            }
        }
    }

    /**
     * Id correlation with "i":<br>
     * App Icon:ImageView:i+arr.length<br>
     * App Name:TextView:i<br>
     * App CheckBox:CheckBox:i+(arr.length*2)<br>
     * LinearLayout:orientation=horizontal:10000+i<br>
     *             :Contains the AppIcon, AppName, CheckBox
     * @param i
     * @param icon
     * @param AppName
     * @param length
     * @param superParentLayout
     */
    public void addApp_toList(int i, Drawable icon, String AppName,int length, LinearLayout superParentLayout){
        View view = generateView.generateVertStroke();
        LinearLayout parentLayout = setLinearLayout(10000 + i);
        setAppIcon(icon,i+length,parentLayout);
        setAppText(AppName, i, parentLayout);
        setCheckBox(i+length*2, parentLayout);

        setOnClickListener(parentLayout, HideApps.class, "selectApp", i, length);
        generateView.addViewToParent(parentLayout, superParentLayout);
        generateView.addViewToParent(view, superParentLayout);
    }

    public void setOnClickListener(View view,Class cl, String methodName, int param1,int param2)  {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Method method = null;
                try {
                    method = cl.getMethod(methodName, new Class[]{Integer.class, Integer.class});
                    method.invoke(HideApps.this,param1, param2);
                } catch (Exception e) {
                    Log.e("class", cl.toString());
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSelectedApps();
            }
        });
        button.invalidate();
    }

    public void hideSelectedApps(){
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList<Byte> imageData = new ArrayList<>();
        CheckBox checkBox;

        for (int i = 0; i < icons.length; i++) {
            checkBox = findViewById(i+icons.length*2);

            if(checkBox!=null && checkBox.isChecked())
            {
//FIXME $date$ Fix the hiding selected app part
            }
        }

        Log.e("stringbuilder",stringBuilder.toString());

        //fileIO.writeFileByte(new File(getFilesDir(),"image.txt"), imageData.toArray(), true);
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
    private void setAppText(@Nullable String text, int id, LinearLayout parentView){
        int r = (int) generateView.dptopixel(50);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(r*3, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.bottomMargin = layoutParams.leftMargin = layoutParams.topMargin
                = r/5;

        generateView.generateTextView(layoutParams, id, text, null, getDrawable(R.drawable.bg_1), parentView, Gravity.NO_GRAVITY);
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