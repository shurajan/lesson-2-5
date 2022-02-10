package com.geekbrains;

public class Main {


    public static void main(String[] args) {
        fillArrayOneThread();
        System.out.println("");
        fillArrayTwoThreads();
    }

    private static void fillArrayOneThread() {
        int size = 10000000;
        float[] arr = new float[size];
        int h = size / 2;

        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }

        long a = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.println("One thread : " + (System.currentTimeMillis() - a));

        for (int i = h - 3; i < h + 3; i++) {
            System.out.printf(String.valueOf(arr[i]) + " ");
        }

    }

    private static void fillArrayTwoThreads() {
        int size = 10000000;
        int h = size / 2;
        float[] arr = new float[size];
        float[] a1 = new float[h];
        float[] a2 = new float[h];

        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }

        long a = System.currentTimeMillis();

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        Thread runnableThread1 = new Thread(new fillArrayTask(a1, 0));
        Thread runnableThread2 = new Thread(new fillArrayTask(a2, h));
        runnableThread1.start();
        runnableThread2.start();

        try {
            runnableThread1.join();
            runnableThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);

        System.out.println("Two threads : " + (System.currentTimeMillis() - a));

        for (int i = h - 3; i < h + 3; i++) {
            System.out.printf(String.valueOf(arr[i]) + " ");
        }

    }

    private static class fillArrayTask implements Runnable {
        private float[] arr;
        private int offset;

        fillArrayTask(float[] arr, int offset) {
            this.arr = arr;
            this.offset = offset;
        }

        @Override
        public void run() {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (float) (arr[i] * Math.sin(0.2f + (i + offset) / 5) * Math.cos(0.2f + (i + offset) / 5)
                        * Math.cos(0.4f + (i + offset) / 2));
            }
        }
    }
}
