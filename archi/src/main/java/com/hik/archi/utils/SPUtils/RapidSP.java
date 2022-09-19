/*
 * Copyright 2020-present hikvision
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hik.archi.utils.SPUtils;

import android.content.Context;
import android.os.FileObserver;
import android.util.Log;
import android.util.LruCache;

import androidx.annotation.Nullable;

import com.hik.archi.utils.SPUtils.io.ReadWriteManager;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by linzijian on 2021/3/5.
 */
public class RapidSP implements EnhancedSP {

    private static final String TAG = "RapidSP";
    private static final RspCache RSP_CACHE = new RspCache();
    private static final ExecutorService SYNC_EXECUTOR = Executors.newFixedThreadPool(4);
    private static Context sContext = null;

    public static void init(Context context) {
        if (context == null) {
            return;
        }
        sContext = context.getApplicationContext();
    }

    public static void setMaxSize(int maxSize) {
        RSP_CACHE.resize(maxSize);
    }

    public static RapidSP get(String name) {
        if (name == null || name.length() == 0) {
            return null;
        }
        synchronized (RapidSP.class) {
            return RSP_CACHE.get(name);
        }
    }

    private final String name;
    private final Map<String, Object> keyValueMap;
    private final RspEditor editor = new RspEditor();
    private final AtomicBoolean needSync = new AtomicBoolean(false);
    private final AtomicBoolean syncing = new AtomicBoolean(false);
    //这个锁主要是为了锁住拷贝数据的过程，当进行数据拷贝的时候，不允许任何写入操作
    private final ReadWriteLock copyLock = new ReentrantReadWriteLock();
    private final DataChangeObserver observer;

    private RapidSP(String name) {
        this.name = name;
        this.keyValueMap = new ConcurrentHashMap<>();
        reload();
        observer = new DataChangeObserver(ReadWriteManager.getFilePath(sContext, name));
        observer.startWatching();
    }

    @Override
    public Map<String, ?> getAll() {
        return keyValueMap;
    }

    @Nullable
    @Override
    public String getString(String s, @Nullable String s1) {
        if (keyValueMap.containsKey(s)) {
            return (String) keyValueMap.get(s);
        }
        return s1;
    }

    @Override
    public Serializable getSerializable(String key, @Nullable Serializable defValue) {
        if (keyValueMap.containsKey(key)) {
            return (Serializable) keyValueMap.get(key);
        }
        return defValue;
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String s, @Nullable Set<String> set) {
        if (keyValueMap.containsKey(s)) {
            return (Set<String>) keyValueMap.get(s);
        }
        return set;
    }

    @Override
    public int getInt(String s, int i) {
        if (keyValueMap.containsKey(s)) {
            return (int) keyValueMap.get(s);
        }
        return i;
    }

    @Override
    public long getLong(String s, long l) {
        if (keyValueMap.containsKey(s)) {
            return (long) keyValueMap.get(s);
        }
        return l;
    }

    @Override
    public float getFloat(String s, float v) {
        if (keyValueMap.containsKey(s)) {
            return (float) keyValueMap.get(s);
        }
        return v;
    }

    @Override
    public boolean getBoolean(String s, boolean b) {
        if (keyValueMap.containsKey(s)) {
            return (boolean) keyValueMap.get(s);
        }
        return b;
    }

    @Override
    public boolean contains(String s) {
        return keyValueMap.containsKey(s);
    }

    @Override
    public EnhancedEditor edit() {
        return editor;
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {

    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {

    }

    private void reload() {
        Log.d(TAG, "reload data");
        Object loadedData = new ReadWriteManager(sContext, name).read();
        this.keyValueMap.clear();
        if (loadedData != null) {
            this.keyValueMap.putAll((Map<? extends String, ?>) loadedData);
        }
    }

    private int sizeOf() {
        File file = new File(ReadWriteManager.getFilePath(sContext, name));
        if (!file.exists()) {
            return 0;
        }
        return (int) (file.length() / 1024);
    }

    private class RspEditor implements EnhancedEditor {
        @Override
        public EnhancedEditor putSerializable(String key, @Nullable Serializable value) {
            put(key, value);
            return this;
        }

        @Override
        public EnhancedEditor putString(String s, @Nullable String s1) {
            put(s, s1);
            return this;
        }

        @Override
        public EnhancedEditor putStringSet(String s, @Nullable Set<String> set) {
            put(s, set);
            return this;
        }

        @Override
        public EnhancedEditor putInt(String s, int i) {
            put(s, i);
            return this;
        }

        @Override
        public EnhancedEditor putLong(String s, long l) {
            put(s, l);
            return this;
        }

        @Override
        public EnhancedEditor putFloat(String s, float v) {
            put(s, v);
            return this;
        }

        @Override
        public EnhancedEditor putBoolean(String s, boolean b) {
            put(s, b);
            return this;
        }

        private void put(String s, Object obj) {
            copyLock.readLock().lock();
            keyValueMap.put(s, obj);
            copyLock.readLock().unlock();
        }

        @Override
        public Editor remove(String s) {
            copyLock.readLock().lock();
            keyValueMap.remove(s);
            copyLock.readLock().unlock();
            return this;
        }

        @Override
        public EnhancedEditor clear() {
            copyLock.readLock().lock();
            keyValueMap.clear();
            copyLock.readLock().unlock();
            return this;
        }

        @Override
        public boolean commit() {
            sync();
            return true;
        }

        @Override
        public void apply() {
            sync();
        }

        private void sync() {
            needSync.compareAndSet(false, true);
            postSyncTask();
        }

        private synchronized void postSyncTask() {
            if (syncing.get()) {
                return;
            }
            SYNC_EXECUTOR.execute(new SyncTask());
        }

        private class SyncTask implements Runnable {

            @Override
            public void run() {
                if (!needSync.get()) {
                    return;
                }
                //先把syncing标记置为true
                syncing.compareAndSet(false, true);
                //copy map，copy的过程中不允许写入
                copyLock.writeLock().lock();
                Map<String, Object> storeMap = new HashMap<>(keyValueMap);
                copyLock.writeLock().unlock();
                //把needSync置为false，如果在此之后有数据写入，则需要重新同步
                needSync.compareAndSet(true, false);
                observer.stopWatching();
                ReadWriteManager manager = new ReadWriteManager(sContext, name);
                manager.write(storeMap);
                //解除同步过程
                syncing.compareAndSet(true, false);
                Log.d(TAG, "write to file complete");
                //如果数据被更改，则需要重新同步
                if (needSync.get()) {
                    Log.d(TAG, "need to sync again");
                    postSyncTask();
                } else {
                    Log.d(TAG, "do not need to sync, start watching");
                    observer.startWatching();
                }
            }
        }
    }

    private class ReloadTask implements Runnable {
        @Override
        public void run() {
            reload();
        }
    }

    private class DataChangeObserver extends FileObserver {

        private static final int FILE_EVENTS = MODIFY | CLOSE_WRITE | DELETE;

        public DataChangeObserver(String path) {
            super(path, FILE_EVENTS);
        }

        @Override
        public void onEvent(int event, @Nullable String path) {
            Log.d(TAG, "DataChangeObserver: " + event);
            switch (event) {
                case CLOSE_WRITE:
                    onCloseWrite(path);
                    break;
                case DELETE:
                    onDelete(path);
                    break;
            }
        }

        public void onCloseWrite(String path) {
            if (syncing.get()) {
                //如果正在同步，则取消reload
                return;
            }
            SYNC_EXECUTOR.execute(new ReloadTask());
        }

        public void onDelete(String path) {
            keyValueMap.clear();
        }
    }

    private static class RspCache extends LruCache<String, RapidSP> {

        private static final int DEFAULT_MAX_SIZE = (int) (Runtime.getRuntime().maxMemory() / 1024 / 16);

        public RspCache() {
            this(DEFAULT_MAX_SIZE);
        }

        public RspCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected int sizeOf(String key, RapidSP value) {
            int size = 0;
            if (value != null) {
                size = value.sizeOf();
            }
            Log.d(TAG, "RspCache sizeOf " + key + " is: " + size);
            return size;
        }

        @Override
        protected RapidSP create(String key) {
            return new RapidSP(key);
        }

        @Override
        protected void entryRemoved(boolean evicted, String key, RapidSP oldValue, RapidSP newValue) {
            Log.d(TAG, "RspCache entryRemoved: " + key);
        }
    }
}
