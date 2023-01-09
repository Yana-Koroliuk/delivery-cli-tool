# delivery-cli-tool
## Relevance
Delivery-CLI-tool is a program that can be used in all delivery services. For the company will be possible to save on delivery costs by using the most optimal way of delivering the product. Also, with the help of this program, you can build the most optimal way, taking into account that some roads are under repair or are closed to traffic. Providing this opportunity for business is essential. Failure to do so can lead to problems with business continuity, loss of profit.
## Concept

Delivery-CLI-tool is a program that allows you to find the most optimal path between two objects, taking into account the directions between the objects and the length of the path between them. Objects can be cities, houses, that is, any objects that, together with the connections between them, can be represented in the form of a graph. This program uses Dijkstra's algorithm to find the most optimal path. Dijkstra's algorithm is the iterative algorithmic process to provide us with the shortest path from one specific starting node to all other nodes of a graph. This algorithm works on the basis that any subpath B -> D of the shortest path A -> D between vertices A and D is also the shortest path between vertices B and D. Djikstra used this property in the opposite direction i.e. we overestimate the distance of each vertex from the starting vertex. Then we visit each node and its neighbors to find the shortest subpath to those neighbors.
The algorithm uses a greedy approach in the sense that we find the next best solution hoping that the end result is the best solution for the whole problem.

## Program
The program works as an interactive form. It allows users to find the most optimal path between existing objects, add, delete and update existing paths between objects both in one direction and in another direction. There are such commands:
* calc - to find the most optimal path between two objects
* add - to add a path between objects
* delete - to delete the path between objects
* quit - to exit

All code has been verified by SonarLint. All dependencies are listed in **pom.xml** flie.

## Technological stack
* Java
* JUnit
* SonarQube as linter and static code validator
* Maven as project builder

## Help
Ask questions at https://t.me/Koroliuk_Yana and post issues on GitHub.

## LicenseGNU
Don\`t forget about licence. This program is GNU General Public licensed.