package com.imTest;

public class Mythread {
    public static void main(String[] args) {

        new Thread() {
            public void run() {
                for (int x = 0; x < 1000; x++) {
                    System.out.println(Thread.currentThread().getName() + "....." + x);
                }
            }
        }.start();


        for (int x = 0; x < 1000; x++) {
            System.out.println(Thread.currentThread().getName() + "....." + x);
        }

        new Thread(new Runnable(){
            @Override
            public void run(){
                for (int x = 0; x < 1000; x++) {
                    System.out.println(Thread.currentThread().getName() + "....." + x);
                }
            }
        }){

        }.start();


        //new Test1().start();
    }

}
