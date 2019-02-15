package com.razerdp.utils;

import java.lang.reflect.Array;

/**
 * Created by 大灯泡 on 2017/05/5.
 * <p>
 * 简单的对象池
 */

public class SimpleObjectPool<T> {

    protected T[] objsPool;
    protected int size;
    protected int curPointer = -1;

    public SimpleObjectPool(Class<T> type, int size) {
        this.size = size;
        objsPool = (T[]) Array.newInstance(type, size);
    }

    public synchronized T get() {
        if (curPointer == -1 || curPointer > objsPool.length) return null;
        T obj = objsPool[curPointer];
        objsPool[curPointer] = null;
        curPointer--;
        return obj;
    }

    public synchronized boolean put(T t) {
        if (curPointer == -1 || curPointer < objsPool.length - 1) {
            curPointer++;
            objsPool[curPointer] = t;
            return true;
        }
        return false;
    }

    public void clearPool() {
        for (int i = 0; i < objsPool.length; i++) {
            objsPool[i] = null;
        }
        curPointer = -1;
    }
}