package com.wlg.Test;

/**
 * Created by LvLiangFeng on 2016/11/29.
 */
import java.io.File;
import java.io.IOException;


public class DeleteFile {
    public static void main(String[] args) {
        File f=new File("d:/try/");
        try {
            System.out.println(f.getCanonicalPath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Sorry,can't get canonical path");
        }
        recurDelete(f);
//	System.out.println(f.list());
    }

    public static void deleteAllFiles(String path){
            File f = new File(path);
            recurDelete(f);
    }

    public static void recurDelete(File f){
        try{
            for(File fi:f.listFiles()){
                if(fi.isDirectory()){
                    recurDelete(fi);
                }
                else{
                    fi.delete();
                }
            }
            f.delete();
        }
        catch(NullPointerException n){
            System.out.println("Sorry,No such file");
        }
    }
}

