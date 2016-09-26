package com.ravi.hw1.question2;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.StringTokenizer;
import java.io.IOException;




public class DoubleMapper extends Mapper<Object, Text, Text, IntWritable> {

	private Text key1 = new Text();
	private final static IntWritable one = new IntWritable(1);

	@Override
	public void map(Object key, Text value, Context contex) throws IOException,
			InterruptedException {

		String lastWord = "";
		String curWord = "";
		
		// get first line
		
		StringTokenizer lineList = new StringTokenizer(value.toString());
		if (lineList.hasMoreTokens()) {
			// get last word
			lastWord = lineList.nextToken(); 
		}
		while (lineList.hasMoreTokens()) {
			curWord = lineList.nextToken();
			
			// get the key which is lastword+space+currentword
			key1.set(lastWord + " " + curWord);
			contex.write(key1, one);
			lastWord = curWord; 
		}
	}
}