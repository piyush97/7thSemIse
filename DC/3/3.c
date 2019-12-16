#include <omp.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int *prime_table(int prime_num)
{
  //printf("prime table by thread %d", omp_get_thread_num());
  int i;
  int j;
  int p;
  int prime;
  int *primes;
  primes = (int *)malloc(prime_num * sizeof(int));
  i = 2;
  p = 0;
  while (p < prime_num)
  {
    prime = 1;
    for (j = 2; j < i; j++)
    {
      if ((i % j) == 0)
      {
        prime = 0;
        break;
      }
    }
    if (prime)
    {
      primes[p] = i;
      p = p + 1;
    }
    i = i + 1;
  }
  return primes;
}
double *sine_table(int sine_num)
{
  //printf("sine table by thread %d", omp_get_thread_num());
  double a;
  int i;
  int j;
  double pi = 3.141592653589793;
  double *sines;
  sines = (double *)malloc(sine_num * sizeof(double));
  for (i = 0; i < sine_num; i++)
  {
    sines[i] = 0.0;
    for (j = 0; j <= i; j++)
    {
      a = (double)(j)*pi / (double)(sine_num - 1);
      sines[i] = sines[i] + sin(a);
    }
  }
  return sines;
}
int main()
{
  omp_set_nested(1);
  int size = 10;
  printf("\n\nInput Size\t1\t2\t4\t8\t");
  for (int i = 0; i < 5; i++)
  {
    printf("\n\n%d\t", size);
    for (int x = 0; x < 4; x++)
    {
      double t1 = omp_get_wtime();
#pragma omp parallel sections
      {
        omp_set_num_threads(2 * x);
#pragma omp section
        {
          int *a = (int *)malloc(size * sizeof(int));
          a = prime_table(size);
          /*for(int y=0; y<size; y++)
                    {
                        printf("%d\n",a[y]);
                    }*/
        }
#pragma omp section
        {
          double *b = (double *)malloc(size * sizeof(double));
          b = sine_table(size);
          for (int z = 0; z < size; z++)
          {
            printf("%lf\n", b[z]);
          }
        }
      }
      double t2 = omp_get_wtime();
      printf("%lf\t", t2 - t1);
    }
    size = size * 10;
  }
  return 0;
}
