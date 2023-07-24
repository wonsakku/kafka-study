package org.example;

public class ShutdownHookTest {

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());

        System.out.println("!!!!!!!");
    }


    static class ShutdownHook extends Thread{

        @Override
        public void run() {
            System.out.println("hello shutdown hook");
        }
    }
}
