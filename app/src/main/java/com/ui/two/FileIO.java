package com.ui.two;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileIO {
    final Context context;
    FileIO(Context context){
        this.context = context;
    }

    public String readFile(File file){
        StringBuilder stringBuilder = new StringBuilder("");
        try {
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String t;
            while ((t = bufferedReader.readLine()) != null) stringBuilder.append(t);
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        }catch (IOException E){
            Log.e("EXCEPTION : ", E.toString());
        }
        return stringBuilder.toString();
    }

    public void writeInternalFile(File file,String content,boolean isAppended){
        try {
            if(!file.exists() && !file.createNewFile())  return;
            FileOutputStream fileOutputStream = new FileOutputStream(file, isAppended);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            Log.e("Exception : ", e.toString());
        }
    }
}
