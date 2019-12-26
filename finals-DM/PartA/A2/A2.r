install.packages("party")
library(party)
data <- read.csv("/Users/piyushmehta/7thSemIse/finals-DM/PartA/a2/fever.csv")
print(head(data))
png(file = "decision_tree.png")
output.tree <- ctree(
  i1 ~ i2 + i3 + i4 + Result, 
  data = input.dat)
