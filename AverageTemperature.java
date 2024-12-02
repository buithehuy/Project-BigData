import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class AverageTemperature {

    public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

        private Text country = new Text();
        private IntWritable temperature = new IntWritable();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            // Split line by ,
            String[] fields = value.toString().split(",");
            if (fields.length > 1) {
                try {
                    String countryName = fields[0]; // Country name
                    // Iterate over temperature values from the years
                    for (int i = 3; i < fields.length; i++) {
                        int temp = (int) Math.round(Double.parseDouble(fields[i])); // Get temperature and round it to an integer
                        country.set(countryName + "-" + (2024 - (i - 3))); // Create key: "Country-Year"
                        temperature.set(temp); // Set temperature value
                        context.write(country, temperature); // Emit key-value pair
                    }
                } catch (NumberFormatException e) {
                    // Skip lines with invalid data
                }
            }
        }
    }

    public static class AverageTemperatureReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            int count = 0;
            for (IntWritable val : values) {
                sum += val.get();
                count++;
            }
            int average = sum / count;
            result.set(average); // Set result value
            context.write(key, result); // Result: "Country-Year, Average Temperature"
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Average Temperature");
        job.setJarByClass(AverageTemperature.class);

        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(AverageTemperatureReducer.class);
        job.setReducerClass(AverageTemperatureReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0])); // Input path
        FileOutputFormat.setOutputPath(job, new Path(args[1])); // Output path

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
