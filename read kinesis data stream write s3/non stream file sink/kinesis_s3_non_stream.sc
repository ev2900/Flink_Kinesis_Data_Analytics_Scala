%flink

import java.util.Properties

import org.apache.flink.streaming.connectors.kinesis.config.AWSConfigConstants
import org.apache.flink.streaming.connectors.kinesis.config.ConsumerConfigConstants

import org.apache.flink.streaming.connectors.kinesis.FlinkKinesisConsumer

// Kinesis Configuration
val consumerConfig = new Properties()

consumerConfig.put(AWSConfigConstants.AWS_REGION, "us-east-1")
consumerConfig.put(ConsumerConfigConstants.STREAM_INITIAL_POSITION, "TRIM_HORIZON")

// Set up the execution environment
val env = StreamExecutionEnvironment.getExecutionEnvironment

// Create an input data stream kinesis
val kinesis = env.addSource(new FlinkKinesisConsumer[String]("<kinesis-data-stream-name>", new SimpleStringSchema, consumerConfig))

// Write the data stream to S3
kinesis.writeAsText("s3://<s3-bucker-name>/simple-scala-example.json");

env.execute()