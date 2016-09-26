package com.ravi.hw1.question3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class DistributedCacheWordCount extends Configured implements Tool {

	public int run(String[] args) throws Exception {
		
		
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.addCacheFile(new Path("s3://cloudhw/input/word-patterns.txt").toUri());

		job.setJobName("distributed Word Count");
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		job.setMapperClass(DistributedCacheMapper.class);
		job.setReducerClass(DistributedCacheReducer.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setJarByClass(DistributedCacheWordCount.class);
		return job.waitForCompletion(true) ? 0 : 1;

	}

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new DistributedCacheWordCount(), args); 
		System.exit(res);

	}}
