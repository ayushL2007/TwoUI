package com.ui.two.FileStructure;

import android.app.Service;
import android.content.Context;

import java.util.ArrayList;

public class FileHandler2 extends FileHandler{
    public FileHandler2(Context context){
        super(context);
        BASE_DIRECTORY = context.getFilesDir() + "/apps";
        APP_LIST_PATH = BASE_DIRECTORY + "/AppList.apl";
        createFileTree();
    }

    public ArrayList<Object []> getApps(){
        return super.getHiddenApps();
    }
}
