#include <stdlib.h>
#include <stdio.h>
#include <time.h>

#include "mpi.h"

int main(int argc, char *argv[]);
int search(int a, int b, int c, int id, int p);
int f(int i);
void timestamp();

int main(int argc, char *argv[])

{
  int a;
  int b;
  int c;
  int i4_huge = 2147483647;
  int id;
  int j;
  int p;
  double wtime;

  MPI_Init(&argc, &argv);

  MPI_Comm_rank(MPI_COMM_WORLD, &id);

  MPI_Comm_size(MPI_COMM_WORLD, &p);

  a = 1;
  b = i4_huge;
  c = 45;

  if (id == 0)
  {
    timestamp();
    printf("\n");
    printf("SEARCH_MPI:\n");
    printf("  C/MPI version\n");
    printf("  Search the integers from A to B\n");
    printf("  for a value J such that F(J) = C.\n");
    printf("\n");
    printf("  A           = %d\n", a);
    printf("  B           = %d\n", b);
    printf("  C           = %d\n", c);
  }

  wtime = MPI_Wtime();

  j = search(a, b, c, id, p);

  wtime = MPI_Wtime() - wtime;

  if (j != -1)
  {
    printf("\n");
    printf("  Process %d found     J = %d\n", id, j);
    printf("  Verify F(J) = %d\n", f(j));
  }

  if (id == 0)
  {
    printf("  Elapsed wallclock time is %g\n", wtime);
  }
  /*
  Terminate MPI.
*/
  MPI_Finalize();

  /*
  Terminate.
*/
  if (id == 0)
  {
    printf("\n");
    printf("SEARCH_MPI:\n");
    printf("  Normal end of execution.\n");
    printf("\n");
    timestamp();
  }

  return 0;
}
/******************************************************************************/

int search(int a, int b, int c, int id, int p)

{
  int fi;
  int i;
  int j;

  j = -1;
  /*
  i = i + p can take us "over top" so that i becomes negative!
  So we have to be more careful here!
*/
  for (i = a + id; 0 < i && i <= b; i = i + p)
  {
    fi = f(i);

    if (fi == c)
    {
      j = i;
      break;
    }
  }

  return j;
}
/******************************************************************************/

int f(int i)

{
  int i4_huge = 2147483647;
  int j;
  int k;
  int value;

  value = i;

  for (j = 1; j <= 5; j++)
  {
    k = value / 127773;

    value = 16807 * (value - k * 127773) - k * 2836;

    if (value <= 0)
    {
      value = value + i4_huge;
    }
  }

  return value;
}
/******************************************************************************/

void timestamp()

{
#define TIME_SIZE 40

  static char time_buffer[TIME_SIZE];
  const struct tm *tm;
  time_t now;

  now = time(NULL);
  tm = localtime(&now);

  strftime(time_buffer, TIME_SIZE, "%d %B %Y %I:%M:%S %p", tm);

  printf("%s\n", time_buffer);

  return;
#undef TIME_SIZE
}
/******************************************************************************/

double cpu_time(void)

/******************************************************************************/

{
  double value;

  value = (double)clock() / (double)CLOCKS_PER_SEC;

  return value;
}