package org.example.guava;

import com.google.common.util.concurrent.RateLimiter;

public class Guava {
    public static void main(String[] args) {
        RateLimiter rateLimiter = RateLimiter.create(10);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < 20; i++) {
            boolean b = rateLimiter.tryAcquire();
            System.out.println("通过：" + b);
        }

    }
}
