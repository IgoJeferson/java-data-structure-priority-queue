package com.github.igojeferson.ds;

import com.github.igojeferson.ds.model.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrioritiesTest {

    private static final Priorities priorities = new Priorities();

    @Test
    void verifyTheInputRequired() {
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

        List<Student> expected = Arrays.asList(
                new Student(50, "Dan", 3.95),
                new Student(42, "Ashley", 3.9),
                new Student(35, "Shafaet", 3.7),
                new Student(46, "Maria", 3.6)
        );

        Assertions.assertFalse(students.isEmpty());
        assertThat(students, is(expected));

    }

    @Test
    void verifyThatHighestCGPAIsServedFirst() {
        List<String> events = new ArrayList<>();
        events.add("ENTER John 5.75 50");
        events.add("ENTER Mark 4.8 24");
        events.add("ENTER Shafaet 3.7 35");

        List<Student> students = priorities.getStudents(events);

        List<Student> expected = Arrays.asList(
                new Student(50, "John", 5.75),
                new Student(24, "Mark", 4.8),
                new Student(35, "Shafaet", 3.7)
        );

        Assertions.assertFalse(students.isEmpty());
        assertThat(students, is(expected));
    }

    @Test
    void verifyThatSameGPAIsServedFirstByAscendingName() {
        List<String> events = new ArrayList<>();
        events.add("ENTER Igo 5.75 50");
        events.add("ENTER Betty 5.75 24");
        events.add("ENTER Anna 5.75 35");

        List<Student> students = priorities.getStudents(events);

        List<Student> expected = Arrays.asList(
                new Student(35, "Anna", 5.75),
                new Student(24, "Betty", 5.75),
                new Student(50, "Igo", 5.75)
        );

        Assertions.assertFalse(students.isEmpty());
        assertThat(students, is(expected));

    }

    @Test
    void verifyThatSameGPAAndSameNameIsServedFirstByAscendingId() {
        List<String> events = new ArrayList<>();
        events.add("ENTER Betty 5.75 50");
        events.add("ENTER Betty 5.75 24");
        events.add("ENTER Betty 5.75 35");

        List<Student> students = priorities.getStudents(events);

        List<Student> expected = Arrays.asList(
                new Student(24, "Betty", 5.75),
                new Student(35, "Betty", 5.75),
                new Student(50, "Betty", 5.75)
        );

        Assertions.assertFalse(students.isEmpty());
        assertThat(students, is(expected));

    }

    @Test
    void shouldNotFailWhenEmptyEventsAreInformed() {
        List<Student> students = priorities.getStudents(Collections.emptyList());
        assertTrue(students.isEmpty());
    }

    @Test
    void shouldNotFailWhenNullEventsAreInformed() {
        List<Student> students = priorities.getStudents(null);
        assertTrue(students.isEmpty());
    }

    @Test
    void shouldThrowExceptionWhenEmptyEventIsInformed() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> priorities.getStudents(Collections.singletonList(""))
        );

        assertTrue(thrown.getMessage().contains("Invalid event informed."));
    }

    @Test
    void shouldThrowExceptionWhenBlankEventIsInformed() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> priorities.getStudents(Collections.singletonList("            "))
        );

        assertTrue(thrown.getMessage().contains("Invalid event informed."));
    }

    @Test
    void shouldThrowExceptionWhenInvalidEventIsInformedOneMoreArgument() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> priorities.getStudents(Collections.singletonList("ENTER John 3.75 50 XX"))
        );

        assertTrue(thrown.getMessage().contains("Invalid parameters, you must inform Event Type, Name, CGPA and ID"));
    }

    @Test
    void shouldThrowExceptionWhenInvalidEventIsInformedOneArgumentMissing() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> priorities.getStudents(Collections.singletonList("ENTER John 3.75"))
        );

        assertTrue(thrown.getMessage().contains("Invalid parameters, you must inform Event Type, Name, CGPA and ID"));
    }

    @Test
    void shouldThrowExceptionWhenInvalidEventTypeIsInformed() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> priorities.getStudents(Collections.singletonList("NOT_VALID John 3.75 99"))
        );

        assertTrue(thrown.getMessage().contains("Invalid Event Type (Allowed values are: ENTER/SERVED)."));
    }
}
