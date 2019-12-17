#include<stdio.h>
#include<stdlib.h> // or <stdlib.h> rand, srand
#include<time.h> // or <time.h> time
#include<omp.h>
#include<math.h>
#define K 4

int num_threads;
long num_points;
long** points;  // 2D array points[x][0] -> point location, points[x][1] -> distance from cluster mean
int cluster[K][2] = {
    {75, 25}, {25, 25}, {25, 75}, {75, 75}
};
long cluster_count[K];

void populate_points() {
    // Dynamically allocate points[num_points][2] 2D array
    long i;
    points = (long **) malloc(sizeof(long) * num_points); ;
	for (i=0; i<num_points; i++)
	points[i] = (long *) malloc(sizeof(long) * 2);

    for (i=0; i<num_points; i++) {
    points[i][0] = rand() % 100;
    points[i][1] = rand() % 100;
    }
    // Initialize cluster_count
    for (i=0; i<K; i++) {
    cluster_count[i] = 0;
    }
}

double get_distance(int x1, int y1, int x2, int y2) {
    int dx = x2-x1, dy = y2-y1;
    return (double)sqrt(dx*dx + dy*dy);
}

void classify_points() {
    long i;
    #pragma omp parallel for num_threads(num_threads)
    for (i=0; i<num_points; i++) {
    double min_dist = 1000, cur_dist = 1;
    int cluster_index = -1,j;
    for (j=0; j<K; j++) {
        cur_dist = get_distance(points[i][0], points[i][1],cluster[j][0], cluster[j][1]);
        if (cur_dist<min_dist) {
            min_dist = cur_dist;
            cluster_index = j;
        }
    }
    cluster_count[cluster_index]++;
    }
}

int main(int argc, char* argv[]) {
    num_points = atoi(argv[1]);
    num_threads = atoi(argv[2]);
    populate_points();
    double t1 = omp_get_wtime();
    classify_points();
    double t2 = omp_get_wtime();
    double t = (t2 - t1) * 1000;
    printf("Time Taken: %lfms",t);
}