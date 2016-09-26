package com.ravi.hw1.question2;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.util.Iterator;
import java.io.IOException;



public class DoubleReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	private IntWritable doubleWordSum = new IntWritable();

	@Override
	public void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {
		Iterator<IntWritable> it = values.iterator();
		int count = 0;
		while (it.hasNext()) {
			count += it.next().get();
		}
		doubleWordSum.set(count);
		context.write(key, doubleWordSum);
	}
}
