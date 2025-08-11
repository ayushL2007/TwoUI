package com.ui.two.FileStructure;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import com.ui.two.R;
import com.ui.two.SideLineTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler {
    static public String BASE_DIRECTORY;

    static public String APP_LIST_PATH;
    final private FileIO fileIO;

    final protected Context context;

    static private ArrayList<String[]> hiddenAppList;


    public FileHandler(Context context) {
        this.context = context;
        fileIO = new FileIO(context);

        BASE_DIRECTORY = context.getFilesDir() + "/hidden_apps";
        File file = new File(BASE_DIRECTORY);
        boolean s=false;
        if(!file.exists() ||  !file.isDirectory()) {
            s = file.mkdir();
        }
        Log.e(" ", String.valueOf(s));

        APP_LIST_PATH = BASE_DIRECTORY+ "/AppList.apl";

        createFileTree();
    }
    //...................................................PRIVATE & PROCTECTED CONSTRUCTOR METHOD...............................................................................

    /**
     * Called only once in every Runtime
     */
    protected void createFileTree() {
        try {
            File file = new File(APP_LIST_PATH);
            Log.e(BASE_DIRECTORY, APP_LIST_PATH);
            if (!file.exists()) file.createNewFile();

            //First Entry of AppList.apl is the app and its subsequent launcher app
            //"AppName###PackageName"
            String base = "";
            fileIO.writeFile(file, base, true);

        } catch (Exception e) {
            Log.e(e.toString(), e.getMessage() + " ");

        }

        refresh();
    }

    private void updateAppListFile(){
        StringBuilder stringBuilder = new StringBuilder();
        for (String[] appNode:hiddenAppList) {
            String tmp = appNode[0] + "###" + appNode[1];
            stringBuilder.append(tmp);
        }

        fileIO.writeFile(new File(APP_LIST_PATH),stringBuilder.toString(),false);
    }

    private void deleteAppNode(String[] appNode){
        hiddenAppList.remove(appNode);
    }

    private boolean isDuplicate(String[] AppNode){
        for (String[] node:hiddenAppList) {
            if (node == AppNode)    return true;
        }
        return false;
    }

    private void refresh(){
        String[] tmp = fileIO.readFile(new File(APP_LIST_PATH)).split("\n");
        hiddenAppList = new ArrayList<>();
        for (String s : tmp) {
            hiddenAppList.add(s.split("###"));
        }
    }

    private boolean checkHiddenIsNull(){return hiddenAppList.size()<2;}
    //...................................................PUBLIC CONSTRUCTOR METHOD...............................................................................


    /**
     * Call to Add a app to hidden Apps list
     * Calls {@link FileHandler#isDuplicate(String[])} to check if function is already hidden
     * @param AppName self explaining
     * @param PackageName self explaining
     * @param AppIcon self explaining
     */
    public void addAppNode(String AppName, String PackageName, Drawable AppIcon){
        if(isDuplicate(new String[]{AppName, PackageName}))
            return;
        Bitmap Icon = new SideLineTask(context).drawable_to_bitmap(AppIcon);

        String AppInfo = AppName+"###"+PackageName+"\n";
            fileIO.writeFile(new File(APP_LIST_PATH), AppInfo,true);

        byte[] AppDrawableByte = bitmapToByteArr(Icon);
        String drawable_path = BASE_DIRECTORY + "/drawable_"+AppName;
        File file = new File(drawable_path);
        try {
            if(file.exists()||file.createNewFile())
                fileIO.writeFileByte(file,AppDrawableByte,false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        refresh();
    }

    /**
     * Calls {@link FileHandler#findAppName(String)} to check whether appNode already exists or not<br>
     * Calls {@link FileHandler#deleteAppNode(String[])} to delete App from {@link  FileHandler#hiddenAppList}<br>
     * Calls {@link FileHandler#updateAppListFile()} to rewrite the AppList.apl File w.r.t {@link  FileHandler#hiddenAppList}
     * @param PackageName self explaining
     * @return  whether appNode has been removed successfully or not<br>
     * Can fail to remove AppNode:<br>
     * 1.If APP isn't hidden<br>
     * 2.Drawable Icon file for removed App Doesn't exist or can't be removed
     */
    public boolean removeAppNode(String PackageName){
        String AppName;
        if((AppName = findAppName(PackageName))==null)
            return false;
        deleteAppNode(new String[]{AppName, PackageName});
        updateAppListFile();

        String drawablePath = "drawable_"+AppName;
            File file = new File(BASE_DIRECTORY,drawablePath);
        return file.exists() && file.delete();
    }

    public String findAppName(String packageName){
        for (String[] appNode: hiddenAppList) {
            if (appNode[1].equals(packageName))    return appNode[0];
        }

        return null;
    }

    /**
     * @return ArrayList<String[]> of all Hidden app<br>
     * Format for AppNode is {AppName, PackageName}
     */
    public ArrayList<String[]> getHiddenAppsList(){
        return hiddenAppList;
    }

    public ArrayList<Object[]> getHiddenApps(){
        if(checkHiddenIsNull()) return null;
        ArrayList<Object[]> APPINFO = new ArrayList<>();
        for (String[] appNode:hiddenAppList) {
            String appName = appNode[0];
            String packageName = appNode[1];
            String drawable_path = "drawable_"+appName;
            Drawable appIcon = getAppIcon(new File(BASE_DIRECTORY,drawable_path));
            APPINFO.add(new Object[]{appName, packageName, appIcon});
        }

        return APPINFO;
    }

    private Drawable getAppIcon(File file){
        byte[] jpeg_img = fileIO.readFileByte(file);
        Bitmap bitmap = BitmapFactory.decodeByteArray(jpeg_img,0, jpeg_img.length);
        Drawable appIcon = new BitmapDrawable(context.getResources(), bitmap);
        return appIcon;
    }


    public byte[] bitmapToByteArr(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }
}
