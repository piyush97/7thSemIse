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
}%                                                                            piyushmehta@Piyushs-iMac  ~/DC   master  gst        ✔  361  22:05:23
On branch master
Your branch is up to date with 'origin/master'.

nothing to commit, working tree clean
 piyushmehta@Piyushs-iMac  ~/DC   master  cat 6.c    ✔  362  22:05:58
#include <stdio.h>
#include <gd.h>
#include <string.h>
#include <omp.h>

int main(int argc, char *argv[]) {
	if (argc< 4) {
		printf("Usage: ./negative input.png output.png num_threads\n");
		return 1;
	}

	char *input_file = argv[1];
	char *output_file = argv[2];

	int num_threads = atoi(argv[3]);
	int color, x, y, i;
	int red, green, blue;
	FILE *fp;
	if((fp = fopen(input_file, "r")) == NULL) {
		printf("Error opening file %s\n", input_file);
		return 1;
	}

	gdImagePtr img = gdImageCreateFromPng(fp);
	int width = gdImageSX(img);
	int height = gdImageSY(img);
	double t1 = omp_get_wtime();

	#pragma omp parallel for private(y, color, red, green, blue) num_threads(num_threads)
	for(x=0; x<width; x++) {
	    #pragma omp critical
	    {
			for(y=0; y<height; y++) {
				color = x + 0;
				color = gdImageGetPixel(img, x, y);
				red   = 255 - gdImageRed(img, color);
				green = 255 - gdImageGreen(img, color);
				blue  = 255 - gdImageBlue(img, color);
				color = gdImageColorAllocate(img, red, green, blue);
				gdImageSetPixel(img, x, y, color);
	        }
	    }
	}

	double t2 = omp_get_wtime();
	if((fp=fopen(output_file, "w")) == NULL) {
	printf("Error opening output file %s\n", output_file);
	return 1;
	}
	gdImagePng(img, fp);
	gdImageDestroy(img);
	fclose(fp);
	printf("File Size: %dx%d\n", width, height);
	printf("Time Taken: %.3lfms\n",(t2 - t1) * 1000);
	return 0;
}