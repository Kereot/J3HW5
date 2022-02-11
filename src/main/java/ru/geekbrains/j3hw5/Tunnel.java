package ru.geekbrains.j3hw5;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private Semaphore s;

    public Tunnel(int cars) {
        int capacity = cars / 2;
        this.s = new Semaphore(capacity);
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
    }

        @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                s.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
                s.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
