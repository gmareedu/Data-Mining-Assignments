import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
public class SplitIndex
{
private static final String[][] nominalValues =
{{ "youth", "midaged", "senior" }, { "low", "medium", "high" } };
private static final String[] attributes = { "Age", "Income" };
private static final String[] buy = { "yes", "no" };
private static final int[] typeCounts = { 3, 3 };
private static String mInputFilename;
private static String mOutputFilename;
private static List<ArrayList<String>>mData;
private static Map<String, Double>infoGain, giniIndex;
private static int instanceCount;
public static void main (String[] args) throws IOException
{
if (args.length< 2)
{
System.out.println("Arguments are incorrect. Try Application #inputfilename #outputfilename");
System.exit(0);
}
else
{
mInputFilename = args[0];
mOutputFilename = args[1];
readData();
ArrayList<String> output = new ArrayList<>();
calcInfoGainAndGiniIndex();
output.add("Information Gain");
output.add(infoGain.toString());
output.add("Gini Index");
output.add(giniIndex.toString());
PutFileData(output);
}
}
public static void calcInfoGainAndGiniIndex()
{
intpos, neg;
double infoSum, giniSum;
// hashmap to store counts for each nominal attribute [total,pos,neg]
Map<String, ArrayList<Integer>> values = new HashMap<>();
ArrayList<Integer> temp = new ArrayList<>();
// for nominal values
for (inti = 0; i<attributes.length; i++)
{
for (int j = 0; j <typeCounts[i]; j++)
{
temp = new ArrayList<>();
pos = getCount(nominalValues[i][j], buy[0]);
neg = getCount(nominalValues[i][j], buy[1]);
temp.add(pos + neg);
temp.add(pos);
temp.add(neg);
values.put(nominalValues[i][j], temp);
}
}
// hashmap to store infogain for each attribute
infoGain = new HashMap<>();
giniIndex = new HashMap<>();
pos = getCount(buy[0], buy[0]);
neg = getCount(buy[1], buy[1]);
infoGain.put("Information", getInfoValue(pos, neg));
giniIndex.put("ClassAttribute", getGiniValue(pos, neg));
// for attributes
for (inti = 0; i<attributes.length; i++)
{
infoSum = 0.0;
giniSum = 0.0;
for (int j = 0; j <typeCounts[i]; j++)
{
infoSum += ((double) values.get(nominalValues[i][j]).get(0) /
(double) instanceCount)
getInfoValue(values.get(nominalValues[i][j]).get(1),
values.get(nominalValues[i][j]).get(2));
giniSum += ((double) values.get(nominalValues[i][j]).get(0) /
(double) instanceCount)
getGiniValue(values.get(nominalValues[i][j]).get(1),
values.get(nominalValues[i][j]).get(2));
}
infoSum = infoGain.get("Information") - infoSum;
infoGain.put(attributes[i], infoSum);
giniIndex.put(attributes[i], giniSum);
}
}
// split attribute calculation
public static double getInfoValue(int p, int n)
{
double pos = 0.0, neg = 0.0, info = 0.0;
if (p != 0 && n != 0)
{
pos = (double) p / (double) (p + n);
neg = (double) n / (double) (p + n);
info = -pos * Math.log(pos) - neg * Math.log(neg);
info /= Math.log(2);
return info;
}
else
{
return info;
}
}
public static double getGiniValue(int p, int n)
{
double pos = 0.0, neg = 0.0, gini = 0.0;
if (p != 0 && n != 0)
{
pos = (double) p / (double) (p + n);
neg = (double) n / (double) (p + n);
gini = 1 - Math.pow(pos, 2) - Math.pow(neg, 2);
return gini;
}
else
{
return gini;
}
}
public static intgetCount(String s, String result)
{
int count = 0;
for (inti = 0; i<mData.size(); i++)
{
if (mData.get(i).contains(s) &&mData.get(i).contains(result))
{
count++;
}
}
return count;
}
// get data
public static void readData() throws IOException
{
StringTokenizer st;
mData = new ArrayList<>();
ArrayList<String> line;
ArrayList<String> values;
line = GetFileData();
instanceCount = line.size();
for (String str : line)
{
values = new ArrayList<>();
st = new StringTokenizer(str, ",");
while (st.hasMoreTokens())
{
values.add(st.nextToken());
}
mData.add(values);
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
bufferedReader.close();
return fileData;
}
public static void PutFileData(ArrayList<String> output) throws IOException
{
File f = new File(mOutputFilename);
if (!f.exists())
{
f.createNewFile();
}
BufferedWriterbufferedWriter = new
BufferedWriter(new FileWriter(mOutputFilename, true));
bufferedWriter.newLine();
bufferedWriter.newLine();
for (String line : output)
{
bufferedWriter.write(line);
bufferedWriter.newLine();
}
bufferedWriter.close();
}
}
