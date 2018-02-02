// IBookManager.aidl
package com.vince.andframe.demo.aidl;

import com.vince.andframe.demo.aidl.Book;
import com.vince.andframe.demo.aidl.IOnNewBookArriveListener;

interface IBookManager {
    List<Book> getBookList();

    void addBook(in Book book);

    void setOnNewBookArriveListener(IOnNewBookArriveListener listener);

    void removeNewBookArriveListener(IOnNewBookArriveListener listener);
}
