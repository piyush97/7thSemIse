data<-iris
#Number of records
nrow(data)
#Number of Attributes
attributes(data)
#last 10
tail(data,10)
#5 values of sepal width
head(data$Sepal.Width,5)
#Plotting density of petal length
plot(density(data$Petal.Length))
