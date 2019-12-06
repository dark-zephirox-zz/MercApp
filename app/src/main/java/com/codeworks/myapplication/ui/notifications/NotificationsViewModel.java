package com.codeworks.myapplication.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Usted tiene actualmente 0 productos en el carrito, por valor de $0");
    }

    public LiveData<String> getText() {
        return mText;
    }
}