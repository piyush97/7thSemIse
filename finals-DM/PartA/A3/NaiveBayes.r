#load libraries

library(party)  #Contains the decision tree functions
library(caret)  #Contains confusion matrix functions
library(e1071)  #Contains the naive bayes functions

data <- read.csv("/Users/piyushmehta/7thSemIse/finals-DM/A3/hopital.csv")
data

#Split dataset into test and train
index <- sample(2, nrow(data), replace=TRUE, prob=c(0.7,0.3))
train <- data[index==1,]
test <- data[index==2,]

#Select the dependent and independent features
features <- Type~i1+i2+i3+i4

#NAIVE BAYES CLASSIFIER


model <- naiveBayes(features, data=train)

#Model Summary
print(model)

#Evaluate model on test data
res <- predict(model, newdata = test)
confusionMatrix(res, test$Type)