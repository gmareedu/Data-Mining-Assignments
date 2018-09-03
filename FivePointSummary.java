import java.io.BufferedReader; import java.io.BufferedWriter; import java.io.File;

import java.io.FileReader; import java.io.FileWriter; import java.io.IOException; import java.util.ArrayList; import java.util.Collections; import java.util.List;

import java.util.StringTokenizer; public class FivePointSummary
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

ArrayList<String> output = new ArrayList<>(); output.add("Five Point Summary : "); output.add("MIN : " + Collections.min(mData)); output.add("MAX : " + Collections.max(mData)); output.add("MEDIAN : " + calcMedian(mData)); output.add("FIRST QUARTILE : "

+ calcMedian(mData.subList(0, mData.size() / 2))); output.add("THIRD QUARTILE : "

+ calcMedian(mData.subList(mData.size() / 2, mData.size()))); PutFileData(output);

}

}


// median

public static double calcMedian(List<Double> mData2)

{

double median; int size;

Collections.sort(mData2); size = mData2.size();

if (size % 2 == 0)

{

median = mData2.get(size / 2) + mData2.get(size / 2 + 1); median /= 2;

}

else

{

median = mData2.get(size / 2);



}
return median;

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

BufferedReaderbufferedReader = new

BufferedReader(new FileReader(mInputFilename));



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

BufferedWriterbufferedWriter = new

BufferedWriter(new FileWriter(mOutputFilename, true)); bufferedWriter.newLine();

bufferedWriter.newLine(); for (String line : output)

{

bufferedWriter.write(line);

bufferedWriter.newLine();

}

bufferedWriter.close();

}   }
