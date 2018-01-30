package com.vince.andframe.demo.study;

import android.util.Log;

import com.vince.andframe.business.MainActivity;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by tianweixin on 2017-4-7.
 */

public class LockTest {
    public static Object obj = new Object();
    public static ReentrantLock lock = new ReentrantLock();
    Integer i = 2;

    public void test() {

        new Thread(new Consumer()).start();
        new Thread(new Product()).start();

        ClassLoader loader = MainActivity.class.getClassLoader();
        while (loader != null) {
            Log.d("CLTEST", loader.toString());
            loader = loader.getParent();
        }

    }

//    class Consumer implements Runnable {
//
//        @Override
//        public void run() {
//            int count = 10;
//            for (int i = 0; i < count; i++) {
//                try {
//                    LockTest.lock.lock();
//                    Log.d("jinxing", "A");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    LockTest.lock.unlock();
//                }
//
//
//            }
//        }
//    }
//
//    class Product implements Runnable {
//
//        @Override
//        public void run() {
//            int count = 10;
//            for (int i = 0; i < count; i++) {
//                try {
//                    LockTest.lock.lock();
//                    Log.d("jinxing", "B");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    LockTest.lock.unlock();
//                }
//            }
//        }
//    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            int count = 10;
            synchronized (LockTest.obj) {

                for (int i = 0; i < count; i++) {
                    Log.d("jinxing", "A");

                    LockTest.obj.notify();
                    try {
                        LockTest.obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    class Product implements Runnable {

        @Override
        public void run() {
            int count = 10;
            synchronized (LockTest.obj) {

                for (int i = 0; i < count; i++) {
                    Log.d("jinxing", "B");

                    LockTest.obj.notify();
                    try {
                        LockTest.obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }


}
