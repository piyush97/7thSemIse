#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>

/*Define length of dot product vectors*/
#define VECLEN 100

int main(int argc, char *argv[])
{
  int i, myid, numprocs, len = VECLEN;
  double *a, *b;
  double mysum, allsum;

  /* MPI initialization */

  MPI_Init(&argc, &argv);
  MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
  MPI_Comm_rank(MPI_COMM_WORLD, &myid);

  if (myid == 0)
    printf("Starting omp_dotprod_mpi. Using %d tasks...\n", numprocs);

  a = (double *)malloc(len * sizeof(double));
  b = (double *)malloc(len * sizeof(double));

  for (i = 0; i < len; i++)
  {
    a[i] = 1.0;
    b[i] = a[i];
  }

  mysum = 0.0;
  for (i = 0; i < len; i++)
  {
    mysum += a[i] * b[i];
  }

  printf("Task %d partial sum = %f\n", myid, mysum);

  MPI_Reduce(&mysum, &allsum, 1, MPI_DOUBLE, MPI_SUM, 0, MPI_COMM_WORLD);
  if (myid == 0)
    printf("Done MPI version: global sum = %f\n", allsum);

  free(a);
  free(b);
  MPI_Finalize();
}
