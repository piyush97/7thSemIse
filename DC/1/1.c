#include <omp.h>
#include <stdio.h>
#include <stdlib.h>
void merge(int *a, int l, int mid, int r)
{
  int n1 = mid - l + 1;
  int n2 = r - mid;
  int b[n1], c[n2];
  int k = l;
  for (int i = 0; i < n1; i++)
    b[i] = a[k++];
  for (int i = 0; i < n2; i++)
    c[i] = a[k++];
  k = l;
  int i = 0, j = 0;
  while (i < n1 & j < n2)
  {
    if (b[i] < c[j])
    {
      a[k++] = b[i++];
    }
    else
    {
      a[k++] = c[j++];
    }
  }
  while (i < n1)
    a[k++] = b[i++];
  while (j < n2)
    a[k++] = c[j++];
}
void mergeSort(int *a, int l, int r)
{
  if (l < r)
  {
    int mid;
#pragma omp parallel sections
    {
      mid = (l + r) / 2;
#pragma omp section
      {
        //printf("thread id = %d\t l=%d\t mid=%d\n",omp_get_thread_num(),l,mid);
        mergeSort(a, l, mid);
      }
#pragma omp section
      {
        //printf("thread id = %d\t r=%d\t mid+1=%d\n",omp_get_thread_num(),r,mid+1);
        mergeSort(a, mid + 1, r);
      }
    }
    merge(a, l, mid, r);
  }
}
int main()
{
  omp_set_nested(1);
  int start = 1;
  /*a=(int*)malloc(100*sizeof(int));
for(inti = 0; i<100; i++)
a[i] = rand()%1000;
mergeSort(a,0,99);*/
  printf("\n\nInput Size\t1\t2\t4\t8\t");
  for (int i = 0; i < 4; i++)
  {
    int size = start * 10;
    start = size;
    int a[size];
    for (int j = 0; j < size; j++)
    {
      a[j] = rand() % 100000;
    }
    printf("\n\n%d\t", size);
    for (int i = 0; i < 4; i++)
    {
      omp_set_num_threads(2 * (i));
      double t1 = omp_get_wtime();
      mergeSort(a, 0, size - 1);
      double t2 = omp_get_wtime();
      printf("%lf\t", t2 - t1);
    }
  }
  return 0;
}
