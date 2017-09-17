package com.demo.example.net.observer;

import com.demo.example.net.action.Action;

import java.util.ArrayList;

/**
 * Created by hzsunyj on 2017/8/30.
 */

public class Observable implements Observer {

    private final ArrayList<Observer> observers;

    public Observable() {
        observers = new ArrayList<>();
    }

    public synchronized void registerObserver(Observer o) {
        if (o == null)
            return;
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    public synchronized void unrigisterObserver(Observer o) {
        observers.remove(o);
    }

    public void onAction() {
        onAction(null);
    }

    @Override
    public void onAction(Action action) {
        Observer[] observers;
        synchronized (this) {
            observers = this.observers.toArray(new Observer[this.observers.size()]);
        }
        for (int i = observers.length - 1; i >= 0; i--) {
            observers[i].onAction(action);
        }
    }

    public synchronized void clearObservers() {
        observers.clear();
    }

    public synchronized int countObservers() {
        return observers.size();
    }
}
