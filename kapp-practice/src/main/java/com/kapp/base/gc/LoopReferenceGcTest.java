package com.kapp.base.gc;

public class LoopReferenceGcTest {


    public static void main(String[] args) {
        Instance instance_A = new Instance();
        Instance instance_B = new Instance();

        instance_B.instance = instance_A;
        instance_A.instance = instance_B;

        instance_A = null;
        instance_B = null;

        System.gc();
    }

    public static class Instance {
        private Instance instance;
        private final byte[] bytes = new byte[2028];
    }
}
