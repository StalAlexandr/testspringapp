package ru.alexandrstal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HumanImpl implements Human {

    @Autowired
    @Lazy
    private Human human;

    @Override
    @SuperWork
    public void doWork() {
        System.out.println("Do work!");
    }

    @Override
    public void doWorkInternal() {
        doWork();
    }

    @Override
    public void doWorkExternal() {
        human.doWork();
    }

    @PostConstruct
    public void init() {
        //   doWork();
    }
}
