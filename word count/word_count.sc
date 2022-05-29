%flink

// Set up the execution environment
val env = ExecutionEnvironment.getExecutionEnvironment

//
// Create an input data stream text
// .fromElements = Creates a data stream from the given sequence of objects
//
val text = env.fromElements("To be, or not to be,--that is the question:--", "Whether 'tis nobler in the mind to suffer", "The slings and arrows of outrageous fortune", "Or to take arms against a sea of troubles,")

val counts = text
//    .flatMap { _.toLowerCase.split("\\W+") }
//    .map { (_, 1) }
//    .groupBy(0)
//    .sum(1)

// Print result
counts.print()