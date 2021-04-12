package lesson_7.classes;

import lesson_7.annotations.AfterSuite;
import lesson_7.annotations.BeforeSuite;
import lesson_7.annotations.Test;

public class Test2 {

    @AfterSuite
    public void afterRun() {
        System.out.println("After - " + getClass().getName());
    }

    @Test(priority = 2)
    public void testRun1() {
        System.out.println("Тест1 (2) - " + getClass().getName());
    }

    @Test(priority = 5)
    public void testRun2() {
        System.out.println("Тест2 (5) - " + getClass().getName());
    }

    @Test(priority = 5)
    public void testRun3() {
        System.out.println("Тест3 (5) - " + getClass().getName());
    }

    @BeforeSuite
    public void beforeRun() {
        System.out.println("Before - " + getClass().getName());
    }
}
