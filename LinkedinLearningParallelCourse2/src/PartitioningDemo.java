/*
4 step methodology in videos, for
designing complex programs
that are good for parallelprog.

1. Ppartitioning
Divide the job into parts to solve in the program.
Decompose as many subtasks as possible.
Lots of small equally sized partitions.
2. Communication.
Consider the partitions to be perform.
3. Agglomeration.
Two dimensional split into different tasks.
4. Decomposition.
Functional decomposition considers all separate tasks
computationally.
Data composition considers the data used.
Making cupcakes could be functionally
divided in steps.
Data or domain would consider the different datavariables involved.

Functional and domain approaches are both important,
as you use must consider dependencies both in data
and in functions.

 Communication.
 If you would want to decorate a bunch of cupcakes in different colors,
 and do the cupcakes in parallel, you would have an interdependency
 between threads. Either have one color per thread,
 or communication so as to not create too many of one color.

 Collective communication would be a broadcast from a task.
 A scatter would give different types of data to other members.
 Global communication must be considered for scales as well.
 If one tassk syncronites distributed workers,
 if the numbers of workers grow, you could have a
 bottleneck. Divide and conquer communication and tasks
 to avoid a scaling in the communication that you cant handle.

 Synchronus communication or blocking communication
 involves waiting for all tasks to finish communicating.
 This can be a detriment.
 Nonblocking communication can begin doing other work instead.
 You should cconsider overhead too.

 Latency the time to send messages is important,
 as well as overhead from communication and bandwith,
 how much data we can communicate per time.

 Multiprocess or multithread, latency and bandwith might not be primary concerns.
 Distributing processing along systems might be important for performance.
 */

/*
    Modify a design to execute more efficiently.
    As a parallel program executes,

    Parallism types:
    Fin-grained breaks into small tasks.
    Advantege is load balancing,
    but there is a risk of overhead.

    Coarse grained allows small number of large tasks.
    Lower overhead, but less possibility to give load balance.
    You cant really spread out the load on more processors,
    but theres less risk of having a lot of overhead from the
    parallelization itself.

    A task split of two instead of four,
    would mean more complicated or larger conversation
    with two tasks and two more complicated or larger tasks.

    Mapping doesent count if you have a single processor
    or cant control your scheduling.
    OS often handles the scheduling itself.

 */


public class PartitioningDemo {



}

