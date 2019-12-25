# Install packages use only 1 time
install.packages("party")
install.packages("e1071")
install.packages("caret")

#load libraries

library(party)  #Contains the decision tree functions
library(caret)  #Contains confusion matrix functions
library(e1071)  #Contains the naive bayes functions

data <- read.csv("/Users/piyushmehta/7thSemIse/finals-DM/fever.csv")
View(data)

#Split dataset into test and train
index <- sample(2, nrow(data), replace=TRUE, prob=c(0.7,0.3))
train <- data[index==1,]
test <- data[index==2,]

#Select the dependent and independent features
features <- Patient_Name ~ Age + Fever


#DECISION TREE

#Obtain a decision tree model
model <- ctree(features, data=train)

#Plot the model
plot(model)

