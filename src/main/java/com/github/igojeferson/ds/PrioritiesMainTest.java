package com.github.igojeferson.ds;

import com.github.igojeferson.ds.model.Student;

import java.util.ArrayList;
import java.util.List;

public class PrioritiesMainTest {

    private static final Priorities priorities = new Priorities();

    public static void main(String[] args) {
        List<String> events = new ArrayList<>();
        events.add("ENTER John 3.75 50");
        events.add("ENTER Mark 3.8 24");
        events.add("ENTER Shafaet 3.7 35");
        events.add("SERVED");
        events.add("SERVED");
        events.add("ENTER Samiha 3.85 36");
        events.add("SERVED");
        events.add("ENTER Ashley 3.9 42");
        events.add("ENTER Maria 3.6 46");
        events.add("ENTER Anik 3.95 49");
        events.add("ENTER Dan 3.95 50");
        events.add("SERVED");

        List<Student> students = priorities.getStudents(events);

        if (students.isEmpty()) {
            System.out.println("EMPTY");
        } else {
            for (Student st: students) {
                System.out.println(st.getName() + " " +  st.getCGPA() + " " + st.getID());
            }
        }
    }
}
