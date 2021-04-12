package lesson_7.classes;

import lesson_7.annotations.Test;

public class Test3 {

    @Test(priority = 9)
    public void testRun2() {
        System.out.println("Тест2 (9) - " + getClass().getName());
    }

    @Test(priority = 1)
    public void testRun3() {
        System.out.println("Тест3 (1) - " + getClass().getName());
    }

    @Test(priority = 2)
    public void testRun1() {
        System.out.println("Тест1 (2) - " + getClass().getName());
    }

}
