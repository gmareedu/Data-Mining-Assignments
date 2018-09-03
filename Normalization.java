import java.io.BufferedReader; import java.io.BufferedWriter; import java.io.File;

import java.io.FileReader; import java.io.FileWriter; import java.io.IOException; import java.util.ArrayList; import java.util.Collections; import java.util.List;

import java.util.StringTokenizer; public class Normalization
{

private static String mInputFilename; private static String mOutputFilename; private static List<Double>mData;
public static void main(String[] args) throws IOException

{

if (args.length< 2)

{

System.out.println("Arguments are incorrect. Try Application #inputfilename #outputfilename");

System.exit(0);

}

else

{

mInputFilename = args[0]; mOutputFilename = args[1];

readData();

ArrayList<String> output = new ArrayList<>(); output.add("Normalization : ");

output.add("Zero Score :" + normalizeDataByZScore(mData).toString());

output.add("Decimal Scale : " + normalizeDataByDecScaling(mData).toString());

output.add("Min Max : " + normalizeDataByMinMax(mData, 0,

1).toString());

PutFileData(output);

}

}



public static double calcMean(List<Double> values)

{

double mean = 0, sum = 0; int size = values.size(); for (Double d : values)

{

sum += d;

}

mean = sum / size; return mean;

}



public static double calcStandardDev(List<Double> values)

{

double variance = 0, sum = 0, mean = 0; int size = values.size() - 1;

mean = calcMean(values);

for (Double d : values)

{

sum += Math.pow((d - mean), 2);

}

variance = sum / size; return Math.sqrt(variance);
}

public static ArrayList<Double>normalizeDataByZScore(List<Double> values)

{

ArrayList<Double>nData = new ArrayList<Double>(); double mean = calcMean(values);

double stdDev = calcStandardDev(values); for (Double d : values)
{

nData.add((d - mean) / stdDev);

}

return nData;

}

public static ArrayList<Double>normalizeDataByMinMax(List<Double> values, double newMin, double newMax)

{

ArrayList<Double>nData = new ArrayList<Double>(); double min = Collections.min(values);
double max = Collections.max(values);



for (Double d : values)

{

nData.add(newMin + (((d - min) / (max - min)) * (newMax - newMin)));


}
return nData;

}

public static ArrayList<Double>normalizeDataByDecScaling(List<Double> values)

{

ArrayList<Double>nData = new ArrayList<Double>(); double absMax;

double max = Math.abs(Collections.max(values)); double min = Math.abs(Collections.min(values)); if (max < min)

{

absMax = min;

} else

{

absMax = max;

}

String s = absMax + ""; int factor = s.length(); for (Double d : values)
{

nData.add(d / Math.pow(10, factor));

}

return nData;

}



// get data

public static void readData() throws IOException

{

StringTokenizer stringTokenizer;

ArrayList<String> line;

line = GetFileData();

mData = new ArrayList<>(); for (String s : line)

{

stringTokenizer = new StringTokenizer(s, ","); while (stringTokenizer.hasMoreTokens())

{

mData.add(Double.parseDouble(stringTokenizer.nextToken()));

}

}

}



// File read write functions

public static ArrayList<String>GetFileData() throws IOException

{

String line;

ArrayList<String>fileData = new ArrayList<String>();

BufferedReaderbufferedReader = new BufferedReader(new FileReader( mInputFilename));

while ((line = bufferedReader.readLine()) != null)

{

fileData.add(line);

}



bufferedReader.close(); return fileData;
}

public static void PutFileData(ArrayList<String> output) throws IOException

{
File f = new File(mOutputFilename); if (!f.exists())

{
f.createNewFile();
}
BufferedWriterbufferedWriter = new BufferedWriter(new FileWriter( mOutputFilename, true));

bufferedWriter.newLine();

bufferedWriter.newLine(); for (String line : output)

{
bufferedWriter.write(line);

bufferedWriter.newLine();
}

bufferedWriter.close();

}}
