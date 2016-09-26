package com.ravi.hw1.question3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DistributedCacheMapper extends Mapper<Object, Text, Text, IntWritable> {
	List<String> fileWordList = new ArrayList<String>(); 
	private Text word = new Text();
	private final static IntWritable one = new IntWritable(1);
	StringTokenizer lines ;
	BufferedReader bufferReader;
	String nextLine = "";

	/* (non-Javadoc)
	 * @see org.apache.hadoop.mapreduce.Mapper#map(KEYIN, VALUEIN, org.apache.hadoop.mapreduce.Mapper.Context)
	 */
	@Override
	public void map(Object key, Text value, Context contex) throws IOException,
			InterruptedException {
		lines = new StringTokenizer(value.toString());

		String nextToken = "";
		while (lines.hasMoreTokens()) {
			// get next token
			nextToken = lines.nextToken();
			
			// generate key,value pair if token exists in the list of words from cached file
			if (fileWordList.contains(nextToken)) {
				word.set(nextToken);
				contex.write(word, one);
			}
		}
	}

	
	@Override
	protected void setup(Mapper<Object, Text, Text, IntWritable>.Context context)
			throws IOException, InterruptedException {		
		processFile();
		super.setup(context);

	}

	
	/**
	 * processes file to add words in list, from cached file
	 * @param cachedFile
	 * @throws IOException 
	 */
	private void processFile() throws IOException {
		File cacheFile = new File("./word-patterns.txt");
			
			bufferReader = new BufferedReader(new InputStreamReader(new FileInputStream(cacheFile)));

			nextLine = bufferReader.readLine();
			
			while (nextLine != null) {
				lines = new StringTokenizer(nextLine);
				while (lines.hasMoreTokens()) {
					fileWordList.add(lines.nextToken());
				}
			}
			bufferReader.close();
		

	}
}