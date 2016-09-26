package com.ravi.hw1.question2;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.io.Text;

public class DoubleWordCount {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Job job = Job.getInstance(new Configuration());
		
		FileInputFormat.setInputPaths(job, new Path(args[0])); 
		FileOutputFormat.setOutputPath(job, new Path(args[1])); 
		
		job.setJobName("DoubleWordCount");
		job.setOutputKeyClass(Text.class); 
		job.setOutputValueClass(IntWritable.class); 

		job.setMapperClass(DoubleMapper.class); 
		job.setReducerClass(DoubleReducer.class);

		job.setInputFormatClass(TextInputFormat.class);  
		job.setOutputFormatClass(TextOutputFormat.class); 


		job.setJarByClass(DoubleWordCount.class); 

		System.exit(job.waitForCompletion(true) ? 0 : 1);

	}

}