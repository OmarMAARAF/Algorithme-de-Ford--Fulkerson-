import java.util.LinkedList;

    class FordFulkerson {
        static final int Noeuds = 6;

        /*
	Pour un algorithm de recherche, on a utiliser l'algorithm de parcours en largeur (BFS).
	Cette algorithm débute à partir d'un nœud source. Puis il liste tous les voisins de la source, pour ensuite les explorer un par un.
	Ce mode de fonctionnement utilise donc une file (on a utiliser une liste chainee) dans laquelle il prend le premier sommet et place en dernier ses voisins non encore explorés. 
	Les nœuds déjà visités sont marqués afin d'éviter qu'un même nœud soit exploré 	plusieurs fois.
	*/ 

	/*

	===== TERMINOLOGIE =====
	dest : noeud de destination.
	
	*/
        boolean bfs(int Graph[][], int source, int dest, int p[]) {
            boolean visited[] = new boolean[Noeuds];
            for (int i = 0; i < Noeuds; ++i)
                visited[i] = false;

            LinkedList<Integer> queue = new LinkedList<Integer>();
            queue.add(source);
            visited[source] = true;
            p[source] = -1;

            while (queue.size() != 0) {
                int u = queue.poll();

                for (int v = 0; v < Noeuds; v++) {
                    if (visited[v] == false && Graph[u][v] > 0) {
                        queue.add(v);
                        p[v] = u;
                        visited[v] = true;
                    }
                }
            }

            return (visited[dest] == true);
        }

        // Appliquons l'algorithm de Ford-Fulkerson.
	
        int fordFulkerson(int graph[][], int source, int dest) {
            int u, v;
            int Graph[][] = new int[Noeuds][Noeuds];

            for (u = 0; u < Noeuds; u++)
                for (v = 0; v < Noeuds; v++)
                    Graph[u][v] = graph[u][v];

            int p[] = new int[Noeuds];

            int max_flow = 0;

            
	    // Mise a jour du capacite residuel des arcs.
            while (bfs(Graph, source, dest, p)) {
                int path_flow = Integer.MAX_VALUE;
                for (v = dest; v != source; v = p[v]) {
                    u = p[v];
                    path_flow = Math.min(path_flow, Graph[u][v]);
                }

                for (v = dest; v != source; v = p[v]) {
                    u = p[v];
                    Graph[u][v] -= path_flow;
                    Graph[v][u] += path_flow;
                }

                // Ajout du flot.
                max_flow += path_flow;
            }

            return max_flow;
        }

        public static void main(String[] args) {
            int graph[][] = new int[][] { { 0, 8, 0, 0, 3, 0 }, { 0, 0, 9, 0, 0, 0 }, { 0, 0, 0, 0, 7, 2 },
                    { 0, 0, 0, 0, 0, 5 }, { 0, 0, 7, 4, 0, 0 }, { 0, 0, 0, 0, 0, 0 } };
            FordFulkerson m = new FordFulkerson();
	
            System.out.println("Le flot maximum par l'algorithm de Ford-Fulkerson est : " + m.fordFulkerson(graph, 0, 5));

        }
    }

