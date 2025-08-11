package com.ui.two.FileStructure;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

public class FileIO {
    final Context context;
    public FileIO(Context context){
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

    public void writeFile(File file,String content,boolean isAppended){
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

    public byte[] readFileByte(File file){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                return Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public void writeFileByte(File file,byte[] bytes, boolean append){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file, append);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();
        }catch (IOException E){
            Log.e(E.toString(), E.toString());
        }
    }

}
