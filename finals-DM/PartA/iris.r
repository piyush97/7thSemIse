data<-iris
sepal<-data$Sepal.Width[5:10]
print(sepal)
tail(data,10)
plot(density(data$Petal.Length))
nrow(data)
attributes(data)