# Successor-State-Axiom-in-Prolog
## Project 2 Report: ∃ a ∃ s[Snapped(IronMan, Result(a, s))]

### Team Members:
#### Malak Elshenawy

#### Nadeen Ahmad 

- A description of the implementation of GenGrid.

The main aim of implementing a Java method to split the input string describing the grid is to
facilitate traversing the string, since Prolog doesn’t have a definition for loops so recursion is the
only way in Prolog to split a string. Our implementation of the Generate Grid Java method writes
the knowledge base of the agent in the path of the .pl file given and produces four predicates:

- grid (5,5). --> splitting the string on the occurrence of “;” the first part is used to
    produce a predicate to define the dimensionality of the input grid
- thanos (0,4).--> predicate to define the location of the enemy (Thanos)
- stones(X): -
    X = [ (2,1), (1,1), (1,2), (1,3)]. --> predicate to create a list with the location of the 4
    infinity stones
- ironman ((2,2), [], s0). --> predicate to define the initial situation of Ironman {initial
    location, empty list of stones (no stones collected yet), initial situation s0}
    Example Output:  
    
    
![alt text](https://raw.githubusercontent.com/NadeenAhmad/Successor-State-Axiom-in-Prolog/master/image1.JPG)  


- A discussion of the syntax and semantics of the action terms and predicate symbols you
employ.  
![alt text](https://raw.githubusercontent.com/NadeenAhmad/Successor-State-Axiom-in-Prolog/master/image2.JPG)    

The predefined predicate in Prolog include/1 is used to import the knowledge base in the output
file of the Java method within the Endgame.pl that defines the successor state axiom.

Actions:

- Collect --> collect stones without changing ironman’s location
- Snap --> when the goal state is reached (collect 4 infinity stones and find thanos)
- Up --> when ironman translates from X+1 to X
- Down --> when ironman translates from X-1 to X
- Right --> when ironman translates from Y-1 to Y
- Left --> when ironman translates from Y+1 to Y


- A discussion of your implementation of the successor-state axioms.  
![alt text](https://raw.githubusercontent.com/NadeenAhmad/Successor-State-Axiom-in-Prolog/master/image3.JPG)    
We implemented a successor state axiom to represent the fluent of ironman. Since we will
query with the goal state our implementation totally relies on back-tracking from the goal state
to the initial state in the knowledge base. The first case represents the situation of the goal;
ironman is at the same coordinates as Thanos therefore the action will be snap, the step leading
to ironman being in the same location as Thanos is either ironman was above/below/left or right
Thanos. For example, if the state before the goal had coordinates M and Y such that ironman is
below Thanos so M = X coordinate of Thanos +1 and they have the same Y coordinates
(columns), so the action to move from X+1 to X is up and of course the old state M must be
within the grid. If the old location of ironman before reaching Thanos was above Thanos then
the old (M, Y) M = X coordinate of Thanos -1, so the action to move from X-1 to X is down also
the old state must be within the grid limits >= 0, same goes for left and right possibilities.

In the second case if ironman is standing in a location of any of the 4 infinity stones which has
not been collected (this is validated through the predefined predicate of Prolog member /2). We
are back-tracking so if the encountered location of stones was in the list then it is not collect, if
not collected delete it using the pre-defined Prolog predicate for list deletion delete/3, the
action will be collect and the predicate will be called with the same coordinates and the new list
of stones and current situation.

For ironman to move up go from X+1 to X, the old location M must be within the row limit of the
grid and also the cell of thanos must be blocked so we make sure ironman is not at the same
coordinates as thanos and action will be up. Same goes for the rest of the movements.

We didn’t include the persistence (actions that change nothing (have no effect) in the situation),
because of the Depth first search, useless actions will occupy a huge space of the tree
prolonging the computation of queries.


- A description of the predicate snapped(S) used to query the KB to generate the plan.  
![alt text](https://raw.githubusercontent.com/NadeenAhmad/Successor-State-Axiom-in-Prolog/master/image4.JPG)    
Our predicate snapped calls the predicate representing the successor state axiom of ironman
with the goal state {coordinates of the enemy Thanos (the goal location), a list containing the
locations of the 4 collected infinity stones, situation S}. Using call with depth limit is because
Prolog uses Depth first search which can get stuck in infinite loop, depth limit allows the
termination of search at the given limit. Stones (X) is used to bind the locations of the 4 stones
in the knowledge base to the locations of the collected stones. Thanos (A, B) is called to bind A
and B to the coordinates ironman must reach to achieve the goal state.

- At least two running examples from your implementation.

Example #1: 5,5;1,2;3,4;1,1,2,1,2,2,3,  
![alt text](https://raw.githubusercontent.com/NadeenAhmad/Successor-State-Axiom-in-Prolog/master/image5.JPG)    

**0 1 2 3 4
0
1** S I
**2** S S
**3** S T
**4**
Knowledge base:

grid (5,5).

thanos (3,4).

stones(X): -

X = [ (1,1), (2,1), (2,2), (3,3)].

ironman ((1,2), [], s0).

**Query:**

snapped(S).

**Example Output:**

S = result (snap, result (right, result (collect, result (down, result (right, result (collect, result
(right, result (collect, result (down, result (collect, result (left, s0))))))))))).

**Query:**

snapped (result (snap, result (right, result (collect, result (down, result (right, result (collect,
result (right, result (collect, result (down, result (collect, result (left, s0)))))))))))).


**Example Output:**

**true.**

Example #2: 5,5;2,2;0,4;2,1,1,1,1,2,1,  
![alt text](https://raw.githubusercontent.com/NadeenAhmad/Successor-State-Axiom-in-Prolog/master/image5.JPG)    
**0 1 2 3 4
0** T
**1** S S S
**2** S I
**3
4**
Knowledge base:

grid (5,5).

thanos (0,4).

stones(X): -

X = [ (2,1), (1,1), (1,2), (1,3)].

ironman ((2,2), [], s0).

**Query:**

snapped(S).

**Example Output:**

S = result (snap, result (up, result (right, result (collect, result (right, result (collect, result (right,
result (collect, result (up, result (collect, result (left, s0))))))))))).

**Query:**

snapped (result (snap, result (up, result (right, result (collect, result (right, result (collect, result
(right, result (collect, result (up, result (collect, result (left, s0)))))))))))).

**Example Output:**

**true.**




