#include <iostream>
#include <ctime>
#include <omp.h>
#include <cstdlib>
#include <math.h>
#define k 4
using namespace std;
int num_threads;
long num_points;
long **points;
int cluster[k][2] = {{75, 25}, {25, 25}, {25, 75}, {75, 75}};
long cluster_count[k];
void populate_points()
{
  points = new long *[num_points];
  for (long i = 0; i < num_points; i++)
    points[i] = new long[2];
  srand(time(NULL));
  for (long i = 0; i < num_points; i++)
  {
    points[i][0] = rand() % 100;
    points[i][1] = rand() % 100;
  }
  for (int i = 0; i < k; i++)
  {
    cluster_count[i] = 0;
  }
}
double get_dist(int x1, int y1, int x2, int y2)
{
  int dx = x2 - x1, dy = y2 - y1;
  return ((double)sqrt(dx * dx + dy * dy));
}
void classify()
{
#pragma omp parallel for num_threads(num_threads)
  for (long i = 0; i < num_points; i++)
  {
    double min_dist = 1000, cur_dist = 1;
    int cluster_index = -1;
    for (int j = 0; j < k; j++)
    {
      cur_dist = get_dist(points[i][0], points[i][1], cluster[j][0], cluster[j][1]);
      if (cur_dist < min_dist)
      {
        min_dist = cur_dist;
        cluster_index = j;
      }
    }
    cluster_count[cluster_index]++;
  }
}
int main(int argc, char *argv[])
{
  num_points = atol(argv[1]);
  num_threads = atoi(argv[2]);
  populate_points();
  double t1 = omp_get_wtime();
  classify();
  double t2 = omp_get_wtime();
  double t = (t2 - t1) * 1000;
  cout << "time" << t << "ms" << endl;
  return 0;
}
