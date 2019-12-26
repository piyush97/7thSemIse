library(factoextra)
library(cluster)
data<-read.csv("/Users/piyushmehta/7thSemIse/finals-DM/PartA/A9/movie.csv")
d<- scale(dist(data,method="euclidean"))
kfit<- kmeans(d,3)
fviz_cluster(kfit,data)

plot(kfit)

