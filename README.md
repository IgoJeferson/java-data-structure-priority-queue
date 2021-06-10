# Java Data Structure - Priority Queue

This repository aims to demonstrate the use of PriorityQueue.

## Time Complexity

Time complexity is a way of showing how the runtime of a function increases as the size of the input increases

In the getStudent method, we must consider that the PriorityQueue will be created from scratch and is not ordered.
Because of this, insertion operations will occur at a cost of O(log n). And how will this operation occur according to the size of events that are of type ENTER (n)

Therefore, the largest cost involved in the getStudent method will be O(n log n)

T = O(n log n) = log-linear/quasilinear complexity

## Space Complexity

Space complexity measures the total amount of memory that an algorithm or operation needs to run according to its input size.

Space Complexity = Input space + Auxiliary Space

S = O(n) + O(n) + O(n) -> Initial Space N for the size of events + N for the Priority queue that uses Heap + Auxiliary list for returned students N

S = O(n) = Linear space complexity

## Overall analysis of just PriorityQueue:

* O(log n) time for the enqueing and dequeing methods (offer, poll, remove() and add)
* O(n) for the remove(Object) and contains(Object) methods
* O(1) for the retrieval methods (peek, element, and size)
* To ensure that the complexity O(n log n) time for adding n elements you may state the size of your n elements to omit extension of the container using:
  ```new PriorityQueue<>(int initialCapacity)```

## Running the Unit Tests

```./mvnw clean install```

## General notes

* Space efficiency and time efficiency reach at two opposite ends
* The more time efficiency you have, the less space efficiency you have and vise versa