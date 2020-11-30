Object Oriented Programming Course Homework - VideosDB

November 2020

Dimitrie David 323CA

# Info
https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/tema

# About my implementation

## Models
I started by creating my own models:
* `Show` - abstract class with common methods & fields for both movies and serials (e.g. title,
year), but also abstract methods that differ for movies and serials (e.g. the rating system).
    * `Movie` - final class
    * `Serial` - final class
* `User` - final class
* `Actor` - final class
* `Action` - final class

## Main
I wanted my main function to look very clean. Because of this, I created my `Database` and
`ActionWorker` classes. In `main -> execute` I initialize a new empty Database (DB is Singleton)
and populate it with the `Input` object. After that, I initialize a new `ActionWorker` object
and then I execute all the actions, updating `arrayResult`.

## Database
I decided to store all my input data in a database-like class called `Database`.
 
This has a method `populate`, that takes an `Input` object and parses it into private arrays of
each model enumerated above. In this parsing, I also count the number of show favorites count
and show view count.

In the `Database` class, I also implemented some helper methods like `getUserByUsername` or
`getShowsByGenre`.
 
## ActionWorker
`ActionWorker` is a class that actually executes all the actions and update the result array.
Here, the first action type `switch` appears. (comparing action_type)
Based on the action type, a new class is created (`Command`, `Query` or `Recommendation`). All of
these classes take the current action as parameter in their constructors, and they get the
database instance.

## ActionCommon
This class is a parent for all the Action executors classes (`Command`, `Query` or 
`Recommendation`). It has an abstract method `execute` that executes the action and returns a
result String.

## Actual action execution

Each action type is parsed and executed inside its own class.
Each class implements the `execute` function with a switch based on the type of the command / query
/ recommendation, which calls the appropriate method.

`I think the code is well commented and I will not explain each action function in this readme.`

## Important notes
* I used very often the Stream API, which helped me along with sorting, filtering and ordering my
collections.
* For sorting, I used a Comparator with all the criterias chained with `comparing().thenComparing()`.
* For `Query`, I implemented 3 internal classes, that handle each object type because many methods
were implemented differently for each of them (e.g. filters).
* If I did not write a Javadoc for a method, it is because my method definitions are enough to
understand what a method does. (eg. `returnFistUnseen`)
* The main tools that I used were filtering and sorting collections. With these, I accomplished
almost every task.
* In my abstract `Show` model, I have some methods that are implemented differently for a Movie
or a Serial (e.g. `getShowRating` or `addRating`) because the rating systems are different
between these 2.