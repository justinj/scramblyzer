data <- read.csv("data/results.csv")
png("chart/result.png")
plot(data)
dev.off()
