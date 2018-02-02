package com.vince.andframe.demo.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by tianweixin on 2018-1-30.
 */

public class BookManagerServerService extends Service {

    private CopyOnWriteArrayList<Book> allBooks = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArriveListener> allListener = new RemoteCallbackList<>();

    IBookManager.Stub mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return allBooks;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            BookManagerServerService.this.addBook(book);
        }

        @Override
        public void setOnNewBookArriveListener(IOnNewBookArriveListener listener) throws RemoteException {
//            if(!allListener.contains(listener)) {
//                allListener.add(listener);
//            }
//            onNewBookArriveListener = listener;
            allListener.register(listener);
        }

        @Override
        public void removeNewBookArriveListener(IOnNewBookArriveListener listener) throws RemoteException {
//            onNewBookArriveListener = null;
            allListener.unregister(listener);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        allBooks.clear();
        allBooks.add(new Book("iOS", 101));
        allBooks.add(new Book("android", 102));
        Log.d("jinxing", "Server start");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("jinxing", "onBind");
        return mBinder;
    }

    private void addBook(Book book) {
        allBooks.add(book);
        int N = allListener.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArriveListener listener = allListener.getBroadcastItem(i);
            if (listener == null) {
                return;
            }
            try {
                listener.onNewBookArrive(book);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        allListener.finishBroadcast();
    }
}
