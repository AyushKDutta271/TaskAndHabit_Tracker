package userInterface;

import java.time.LocalDate;

import operation.BusinessLogic;
import pojo.Task;

public class Test {
    public static void main(String[] args) {
        System.out.println("This is working!");
        System.out.println(LocalDate.now());
        System.out.println(BusinessLogic.tasks);
        for(Task t: BusinessLogic.tasks)
        {
            System.out.println(t);
        }
    }
}
