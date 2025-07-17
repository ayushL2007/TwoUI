package com.ui.two;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetApps {
    List<ApplicationInfo> packages;

    Context context;
    int gridSize, numOfGrid;
    ArrayList<Boolean> SysAppList;

    /**
     * @func getPackagesName: return all non-System package names(ex. com.whatsapp)
     * @func getName: return all non-Sys app name(ex. whatsapp)
     * @func getPackagesIcon: return a drawable icon of app
     * @param context: For use of Android System Service
     * @param gridSize: For returning the apps in device in a matrix of "arr [n/gridSize][gridSize]"
     */
    GetApps(Context context, int gridSize){
        this.context = context;
        this.checkPermissions();
        PackageManager packageManager = context.getPackageManager();
        packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        this.gridSize = gridSize;
        numOfGrid = packages.size()/gridSize+1;
        SysAppList = new ArrayList<Boolean>();
        filterSysApp();
    }

    private boolean checkPermissions(){
       // checkPermission()
        return true;
    }

    private void filterSysApp(){
        Iterator<ApplicationInfo> applicationInfoIterator = packages.iterator();
        while(applicationInfoIterator.hasNext()){
            ApplicationInfo applicationInfo = applicationInfoIterator.next();
            boolean isInstalled = (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)!=ApplicationInfo.FLAG_SYSTEM;
            if (!isInstalled)  applicationInfoIterator.remove();
        }
    }

    /**
     * @return String package name in a matrix of given grid size arr[n/gridSize][gridSize]
     */
    public String[][] getPackagesName(){

        String[][] packageNames = new String[numOfGrid][gridSize];
        int j=0,i=0;
        for(ApplicationInfo package_:packages){
            if(j==gridSize){i+=1;   j=0;}
            packageNames[i][j]=package_.packageName;
            j++;
        }
        return packageNames;
    }

    /**
     *
     * @return Drawable package icon in a matrix of given grid size arr[n/gridSize][gridSize]
     */
    public Drawable[][] getPackagesIcon(){

        Drawable[][] icons = new Drawable[numOfGrid][gridSize];
        int j=0,i=0;
        for(ApplicationInfo package_:packages){
            if(j==gridSize){i+=1;   j=0;}
            icons[i][j]=package_.loadIcon(context.getPackageManager());
            j++;
        }
        return icons;
    }

    /**
     *
     * @return String app name in a matrix of given grid size arr[n/gridSize][gridSize]
     */
    public String[][] getName(){

        String[][] names = new String[numOfGrid][gridSize];
        int j=0,i=0;
        for(ApplicationInfo package_:packages){
            if(j==gridSize){i+=1;   j=0;}
            names[i][j]=(String) package_.loadLabel(context.getPackageManager());
            j++;
        }
        return names;
    }
}
