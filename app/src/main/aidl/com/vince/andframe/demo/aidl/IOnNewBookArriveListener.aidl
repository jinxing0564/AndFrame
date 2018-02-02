// IOnNewBookArriveListener.aidl
package com.vince.andframe.demo.aidl;

import com.vince.andframe.demo.aidl.Book;

// Declare any non-default types here with import statements

interface IOnNewBookArriveListener {
    void onNewBookArrive(in Book book);
}
