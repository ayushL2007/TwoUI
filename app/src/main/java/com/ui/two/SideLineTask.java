package com.ui.two;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SideLineTask {
    final Context context;
    final Activity activity;
    SideLineTask(Context context, Activity activity){
        this.context = context;
        this.activity=activity;
    }

    public void showToast(String text, int duration,@Nullable View view){
        Toast toast = new Toast(context);
        toast.setText(text);
        toast.setDuration(duration);
        if (view!=null) toast.setView(view);
        toast.show();
    }

    public Object[][] changeDimension(Object[][] matrix, int row){
        int column = (int) Math.ceil(matrix.length/(double) row);
        Object[][] modified_matrix = new Object[column][row];
        int k=0,m=0;
        for (Object[] tmp:matrix) {
            for (Object elem:tmp) {
                if(k>=row)  m++;
                modified_matrix[m][k]=elem;
                k++;
            }
        }

        return modified_matrix;
    }


}
