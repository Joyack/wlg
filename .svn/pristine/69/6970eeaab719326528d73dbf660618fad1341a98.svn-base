package com.wlg.Test;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Administrator on 2016/7/14.
 */
public class Playcards {

    private volatile int cardSize =0;
    private int[] aAardlist=new int[13];
    private int[] bAardlist=new int[13];
    private int[] cAardlist=new int[13];
    private int[] dAardlist=new int[13];
    public Playcards(){
        Random rand = new Random();
        int j=0;
        for (int i=1;i<=52;i++){
            if (i/13==0){}
            int cardSize = rand.nextInt(SystemCofig.range); //生成0-52以内的随机数
            if (i>=1&&i<=13){
                aAardlist[j]=cardSize;
            }
        }

    }

    private int i = 10;
    private Object object = new Object();

    public static void main(String[] args) throws IOException {

        Random rand = new Random();

        int cardSize = rand.nextInt(SystemCofig.range); //生成0-52以内的随机数
//        Person aPerson=
//        Test test = new Test();
//        MyThread thread1 = test.new MyThread();
//        MyThread thread2 = test.new MyThread();
//        thread1.start();
//        thread2.start();
    }


    class MyThread extends Thread{
        @Override
        public void run() {

        }
    }
}

