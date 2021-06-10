package com.github.igojeferson.ds;

import com.github.igojeferson.ds.model.Student;

import java.util.*;

public class Priorities {

    private static final int EVENT_TYPE_POSITION = 0;
    private static final int CGPA_POSITION = 2;
    private static final int ID_POSITION = 3;
    private static final int NAME_POSITION = 1;

    public List<Student> getStudents(List<String> events) {

        if (Objects.isNull(events) || events.isEmpty()) { // O(1) + O(1)
            return Collections.emptyList();
        }

        PriorityQueue<Student> sQueue = new PriorityQueue<>(events.size(),
                Comparator.comparing(Student::getCGPA).reversed()
                        .thenComparing(Student::getName)
                        .thenComparingInt(Student::getID)
        );

        // O(n log n)
        events.stream().forEach(e -> { // -> O(n)
            if (e.isBlank()) throw new IllegalArgumentException("Invalid event informed."); // O(1)

            String[] eventLine = e.split(" "); // O(n)

            if ("ENTER".equals(eventLine[EVENT_TYPE_POSITION])) { // O(1)
                sQueue.add(parseEventToStudent(eventLine));  // O(n) + O(log n)

            } else if ("SERVED".equals(eventLine[EVENT_TYPE_POSITION])) { // O(1)
                // remove the top element
                sQueue.poll();  // O(log n)

            } else {
                throw new IllegalArgumentException("Invalid Event Type (Allowed values are: ENTER/SERVED).");
            }
        });

        // Considering that priority queue iterator does not return the elements in any particular order.
        // using something like return new ArrayList(sQueue) will not guarantee the expected order
        // because of this, it is necessary to call the poll on all elements
        List<Student> students = new ArrayList<>();
        while (!sQueue.isEmpty()) { // O(n)
            students.add(sQueue.poll()); // O(log n)
        }

        return students;

    }

    // O(n)
    private Student parseEventToStudent(String[] eventLine) {
        // The event structure will be eventType (ENTER/SERVED), NAME, CGPA, ID
        if (eventLine.length != 4) // O(1)
            throw new IllegalArgumentException("Invalid parameters, you must inform Event Type, Name, CGPA and ID");

        var studentID = Integer.valueOf(eventLine[ID_POSITION]); // O(n)
        var studentName = eventLine[NAME_POSITION]; // O(1)
        var studentCgpa = Double.valueOf(eventLine[CGPA_POSITION]); // O(n)

        return new Student(studentID, studentName, studentCgpa);
    }

}