package com.yjkmust.lemon.event;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;

public class ItemData extends ArrayList<Object> {
    public ItemData() {
    }

    public ItemData(int initialCapacity) {
        super(initialCapacity);
    }

    public ItemData(@NonNull Collection<?> c) {
        super(c);
    }
}
