package com.yueny.demo.hadoop.example;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCountEx {
	public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		private final Text keyEx = new Text();
		private final IntWritable result = new IntWritable();

		@Override
		public void reduce(final Text key, final Iterable<IntWritable> values, final Context context)
				throws IOException, InterruptedException {
			int sum = 0;
			for (final IntWritable val : values) {
				// map的结果
				sum += val.get();
			}
			result.set(sum);
			// 自定义输出key
			keyEx.set("输出:" + key.toString());
			context.write(key, result);
		}
	}

	public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

		private final static IntWritable one = new IntWritable(1);
		private final Text word = new Text();

		@Override
		public void map(final Object key, final Text value, final Context context)
				throws IOException, InterruptedException {
			// 分割字符串
			final StringTokenizer itr = new StringTokenizer(value.toString());
			while (itr.hasMoreTokens()) {
				final String tmp = itr.nextToken();
				// .

				word.set(tmp);
				context.write(word, one);
			}
		}
	}

	public static void main(final String[] args) throws Exception {
		// 配置信息
		final Configuration conf = new Configuration();

		// job名称
		final Job job = Job.getInstance(conf, "word count");
		job.setJarByClass(WordCountEx.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setCombinerClass(IntSumReducer.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// 输入、输出path
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		// 结束
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}

}
