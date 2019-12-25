library(rpart)
library(rpart.plot)
library(party)
library(caret)
data<- read.csv("/Users/piyushmehta/7thSemIse/finals-DM/PartA/A2/fever.csv")
data
index<- sample(2, nrow(data), TRUE, c(0.7,0.3))
train<- data[index==1,]
test<- data[index==2,]
features<- Result~i1+i2+i3+i4
tree<- rpart(features, train, method="class")
rpart.plot(tree)
tree2<- ctree(features,train)
plot(tree2)
res<- predict(tree2,test)
confusionMatrix(res1, test$Result)