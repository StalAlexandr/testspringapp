package ru.alexandrstal;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Launcher {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Human h = context.getBean(Human.class);

        h.doWorkInternal();

        h.doWorkExternal();
    }
}
