package lesson_7.classes;

import lesson_7.annotations.BeforeSuite;
import lesson_7.annotations.Test;

public class Test1 {

    @Test(priority = 2)
    public void testRun1() {
        System.out.println("Тест1 (2) - " + getClass().getName());
    }

    @Test(priority = 7)
    public void testRun2() {
        System.out.println("Тест2 (7) - " + getClass().getName());
    }

    public void notTestRun1() {
        System.out.println("Don't do this");
    }

    @Test(priority = 3)
    public void testRun3() {
        System.out.println("Тест3 (3) - " + getClass().getName());
    }

    @BeforeSuite
    public void beforeRun() {
        System.out.println("Before - " + getClass().getName());
    }

    @BeforeSuite
    public void beforeRun2() {
        System.out.println("Before2 - " + getClass().getName());
    }
}
