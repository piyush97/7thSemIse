data <- read.csv(file = "input.csv")

# Exploration
head(data)

names(data)

attributes(data)

summary(data)

mean(data$height)

median(data$weight)

range(data$ID)

quantile(data$height)

var(data$weight)

table(data$blood_group)


#Covarience and Correlation
cov(data$weight, data$height)

cor(data$weight, data$height)


#Visualization

hist(data$height)

plot(density(data$weight))

pie(table(data$blood_group))

barplot(table(data$blood_group))

plot(data$height)
