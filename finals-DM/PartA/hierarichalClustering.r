library(factoextra)
library(cluster)
data<-read.csv("/Users/piyushmehta/7thSemIse/finals-DM/PartA/diabetes.csv")
data$Result<-NULL
d<-dist(data, method = "euclidean")
hmodel<- hclust(d)
plot(hmodel)
