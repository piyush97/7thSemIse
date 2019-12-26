library(party)
library(caret)
library(e1071)
data <- readingSkills
index <- sample(2, nrow(data), replace=TRUE, prob=c(0.7,0.3))
train <- data[index==1,]
test <- data[index==2,]
features <- nativeSpeaker ~ age + shoeSize + score
model <- ctree(features, data=train)
plot(model)
test_predictions <- predict(model, newdata=test)
confusionMatrix(test_predictions, test$nativeSpeaker, positive="yes")