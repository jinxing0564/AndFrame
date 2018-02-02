package com.vince.andframe.demo.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vince.aframe.R;
import com.vince.andframe.base.ui.activity.BaseBizActivity;
import com.vince.andframe.base.ui.prompt.ToastUtils;

import java.util.List;

/**
 * Created by tianweixin on 2018-1-30.
 */

public class BookClientActivity extends BaseBizActivity {
    public static final int NEW_BOOK_ARRIVE = 100;

    private Button btnGetBook;
    private Button btnAddBook;
    private TextView tvBookList;

    private IBookManager bookManager;

    IOnNewBookArriveListener newBookArriveListener = new IOnNewBookArriveListener.Stub() {
        @Override
        public void onNewBookArrive(Book book) throws RemoteException {
            Message msg = Message.obtain();
            msg.what = NEW_BOOK_ARRIVE;
            msg.obj = book;
            mHandler.sendMessage(msg);
        }
    };

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bookManager = IBookManager.Stub.asInterface(service);

            try {
                bookManager.setOnNewBookArriveListener(newBookArriveListener);
                bookManager.asBinder().linkToDeath(deathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NEW_BOOK_ARRIVE:
                    String newText = tvBookList.getText().toString() + msg.obj.toString() + "\n";
                    tvBookList.setText(newText);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (bookManager != null) {
                bookManager.asBinder().unlinkToDeath(deathRecipient, 0);
            }

            //重新连接
            Intent intent = new Intent(BookClientActivity.this, BookManagerServerService.class);
            bindService(intent, mConnection, BIND_AUTO_CREATE);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_client);
        setTitle("测试进程间通信-AIDL");
        btnGetBook = (Button) findViewById(R.id.btn_get_book);
        btnAddBook = (Button) findViewById(R.id.btn_add_book);
        tvBookList = (TextView) findViewById(R.id.tv_book_list);
        btnGetBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (bookManager == null) {
                        ToastUtils.showToast("disconnect service", Toast.LENGTH_SHORT);
                        return;
                    }
                    List<Book> bookList = bookManager.getBookList();
                    String bookText = "";
                    for (Book book : bookList) {
                        bookText += book.toString() + "\n";
                    }
                    tvBookList.setText(bookText);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookManager == null) {
                    ToastUtils.showToast("disconnect service", Toast.LENGTH_SHORT);
                    return;
                }
                Book book = new Book("new book", 1001);
                try {
                    bookManager.addBook(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        Intent intent = new Intent(this, BookManagerServerService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bookManager != null) {
            try {
                bookManager.removeNewBookArriveListener(newBookArriveListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
    }
}
