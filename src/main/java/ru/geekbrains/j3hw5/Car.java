package ru.geekbrains.j3hw5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private CyclicBarrier cb;
    private CountDownLatch cdl;
    private CountDownLatch cdlFinish;
    private Lock lock;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed, CyclicBarrier cb, CountDownLatch cdl, Lock lock, CountDownLatch cdlFinish) {
        this.cb = cb;
        this.cdl = cdl;
        this.lock = lock;
        this.cdlFinish = cdlFinish;
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            cb.await();
            cdl.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        boolean isLockAcquired = lock.tryLock();
        if (isLockAcquired) {
            System.out.println(this.name + " ПОБЕДИЛ!");
        } else {
            System.out.println(this.name + " закончил гонку");
        }
        cdlFinish.countDown();
    }
}
