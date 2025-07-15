package com.ui.two;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.List;

public class GetApps {
    List<ApplicationInfo> packages;

    Context context;
    int gridSize, numOfGrid;
    GetApps(Context context){
        this.context = context;
        this.checkPermissions();
        PackageManager packageManager = context.getPackageManager();
        packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        gridSize = 4;
        numOfGrid = packages.size()/gridSize+1;
    }

    private boolean checkPermissions(){
       // checkPermission()
        return true;
    }

    public String[][] getPackagesName(){

        String[][] packageNames = new String[numOfGrid][gridSize];
        int j=0,i=0;
        for(ApplicationInfo package_:packages){
            if(j>3){i+=1;   j=0;}
            packageNames[i][j]=package_.packageName;
            j++;
        }
        return packageNames;
    }

    public Drawable[][] getPackagesIcon(){

        Drawable[][] icons = new Drawable[numOfGrid][gridSize];
        int j=0,i=0;
        for(ApplicationInfo package_:packages){
            if(j>3){i+=1;   j=0;}
            icons[i][j]=package_.loadIcon(context.getPackageManager());
            j++;
        }
        return icons;
    }

    public String[][] getName(){

        String[][] names = new String[numOfGrid][gridSize];
        int j=0,i=0;
        for(ApplicationInfo package_:packages){
            if(j>3){i+=1;   j=0;}
            names[i][j]=(String) package_.loadLabel(context.getPackageManager());
            j++;
        }
        return names;
    }
}
