package com.wepiao.model;

public class OutCla {
    private static String msg  = "msg";
    private String        msg2 = "msg2";

    public static class StaticCla {
        private void print() {
            System.out.println(msg);
        }
    }

    public class InnerCla {
        private void print() {
            System.out.println(msg);
            System.out.println(msg2);
        }
    }

    public static void main(String[] args) {
        OutCla.StaticCla t = new OutCla.StaticCla();
        t.print();
        OutCla t2 = new OutCla();
        OutCla.InnerCla t3 = t2.new InnerCla();
        t3.print();
    }

}
