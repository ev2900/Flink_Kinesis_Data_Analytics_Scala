%flink

import java.util.Properties

import org.apache.flink.core.fs.Path

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.common.serialization.SimpleStringEncoder

import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy

import org.apache.flink.streaming.connectors.kinesis.config.AWSConfigConstants
import org.apache.flink.streaming.connectors.kinesis.config.ConsumerConfigConstants
import org.apache.flink.streaming.connectors.kinesis.FlinkKinesisConsumer

// Kinesis Configuration
val consumerConfig = new Properties()

consumerConfig.put(AWSConfigConstants.AWS_REGION, "us-east-1")
consumerConfig.put(ConsumerConfigConstants.STREAM_INITIAL_POSITION, "TRIM_HORIZON")

// Set up the execution environment
val env = StreamExecutionEnvironment.getExecutionEnvironment

// Enable checkpointing 
env.enableCheckpointing(1000)

// Create an input data stream kinesis
val kinesis = env.addSource(new FlinkKinesisConsumer[String]("scala-data-stream", new SimpleStringSchema, consumerConfig))

// Write the data stream to S3
val sink: StreamingFileSink[String] = StreamingFileSink
    .forRowFormat(new Path("s3://sfsdf5454/simple-scala-example"), new SimpleStringEncoder[String]("UTF-8"))
    .withRollingPolicy(
        DefaultRollingPolicy.builder()
            .withRolloverInterval(60000) // 60 seconds
            .withInactivityInterval(60000) // 60 seconds
            .withMaxPartSize(1024) // 1 KB
            .build())
    .build()

kinesis.addSink(sink)

env.execute()