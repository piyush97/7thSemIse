#include <stdio.h>
#include <gd.h>
#include <string.h>
#include <omp.h>
int main(int argc, char *argv[])
{
  if (argc < 4)
  {
    printf("usage\n");
    return 1;
  }
  char *input = argv[1];
  char *output = argv[2];
  int num_threads = atoi(argv[3]);
  int color, x, y, i;
  int red, green, blue;
  FILE *fp;
  if ((fp = fopen(input_file, "r")) == NULL)
  {
    printf("error");
    return 1;
  }
  gdImagePtrimg = gdImageCreateFromPng(fp);
  int width = gdImageSX(img);
  int height = gdImageSY(img);
  double t1 = omp_get_wtime();
#pragma omp parallel for private(y, color, red, gree, blue) num_threads(num_threads)
  for (x = 0; x < width; x++)
  {
    for (y = 0; y < height; y++)
    {
      color = x + 0;
      color = gdImageGetPixel(img, x, y);
      red = 255 - gdImageRed(img, color);
      green = 255 - gdImageGreen(img, color);
      blue = 255 - gdImageBlue(img, color);
      color = gdImageColorAllocate(img, red, green, blue);
      gdImageSetPixel(img, x, y, color);
    }
  }
  double t2 = omp_get_wtime();
  if ((fp = fopenoutput_file, "w"))==NULL)
    {
      printf("error\n");
      return 1;
    }
  gdImagePng(img, fp);
  gdImageDestroy(img);
  fclose(fp);
  printf("file size %d\t %d\n", width, height);
  printf("time= %.3lf ms\n", (t2 - t1) * 1000);
  return 0;
}
