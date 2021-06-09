package com.github.igojeferson.ds;

import com.github.igojeferson.ds.model.Student;

import java.util.*;

public class Priorities {

    private static final int EVENT_TYPE_POSITION = 0;
    private static final int CGPA_POSITION = 2;
    private static final int ID_POSITION = 3;
    private static final int NAME_POSITION = 1;

    public List<Student> getStudents(List<String> events) {

        if (Objects.isNull(events) || events.isEmpty()) {
            return Collections.emptyList();
        }

        PriorityQueue<Student> sQueue = new PriorityQueue<>(events.size(),
                Comparator.comparing(Student::getCGPA).reversed()
                        .thenComparing(Student::getName)
                        .thenComparingInt(Student::getID)
        );

        events.stream().forEach(e -> {
            if (e.isBlank()) throw new IllegalArgumentException("Invalid event informed.");

            String[] st = e.split(" ");

            if ("ENTER".equals(st[EVENT_TYPE_POSITION])) {

                // The event structure will be eventType (ENTER/SERVED), NAME, CGPA, ID
                if (st.length != 4)
                    throw new IllegalArgumentException("Invalid parameters, you must inform Event Type, Name, CGPA and ID");

                var studentID = Integer.valueOf(st[ID_POSITION]);

                var studentName = st[NAME_POSITION];
                var studantCgpa = Double.valueOf(st[CGPA_POSITION]);

                sQueue.add(new Student(studentID, studentName, studantCgpa));

            } else if ("SERVED".equals(st[EVENT_TYPE_POSITION])) {
                // remove the top element
                sQueue.poll();

            } else {
                throw new IllegalArgumentException("Invalid Event Type (Allowed values are: ENTER/SERVED).");
            }
        });

        // Considering that priority queue iterator does not return the elements in any particular order.
        // using something like return new ArrayList(sQueue) will not guarantee the expected order
        // because of this, it is necessary to call the poll on all elements
        List<Student> students = new ArrayList<>();
        while (!sQueue.isEmpty()) {
            students.add(sQueue.poll());
        }

        return students;

    }

}