package com.wlg.Util;

/**
 * Created by LvLiangFeng on 2016/11/29.
 */
import java.io.File;
import java.io.IOException;


public class FileUtil {
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

    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {

        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
}

