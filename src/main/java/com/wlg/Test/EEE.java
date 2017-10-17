package com.wlg.Test;

import java.io.*;

/**
 * Created by Administrator on 2016/7/29.
 */
public class EEE {

    /**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     * @param filePath
     */
    public static void readTxtFile(String filePath) throws IOException {
        int i=1;
        String str = "";
        FileWriter fw = new FileWriter("D:/Test.txt");

        StringBuffer s  = new StringBuffer();
        try {
            String encoding="GBK";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    str = str + lineTxt;
                    if(i % 2 == 0){
                        System.out.println(str);
                        s.append(str+"\n");
                        fw.flush();
                        i=1;
                        str="";
                        i--;
                    }
                    i++;

//                    System.out.println(lineTxt);
                }
                fw.write(s.toString());
                fw.flush();
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

    }

    public static void main(String argv[]) throws IOException {
        String filePath = "d:\\eee.txt";
        readTxtFile(filePath);
    }
}
