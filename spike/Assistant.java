package com.thoughtworks.step;

public class Assistant implements Listener {

    @Override
    public void full() {
        System.out.println("Assistant : No space");
    }

    @Override
    public void notFull() {
        System.out.println("Assistant : Has space");
    }
}
