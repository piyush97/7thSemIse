# Importing Libraries
library(arules) # for apriori
library(arulesViz) # for visualization

patterns = random.patterns(nItems = 1000)

trans = random.transactions(nItems = 1000, nTrans = 1000, method = "agrawal",  patterns = patterns)

image(trans)

rules = apriori(trans, parameter=list(support=0.01, confidence=0.5))
inspect(rules)

plot(rules, method="grouped") # plotting graph