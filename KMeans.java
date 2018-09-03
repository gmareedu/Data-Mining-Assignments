import java.util.*;
import java.io.*;
public class Kmeans
{

public static class Instance

{

ArrayList<Double>mAttributes = new ArrayList<>();

String mClass;

@Override

public String toString()

{

return mAttributes.toString() + "\t" + mClass;

}

}

public static class Cluster

{

String name;

ArrayList<Double>oldCentroid = new ArrayList<>();


ArrayList<Double>newCentroid = new ArrayList<>(); public booleanisStillMoving()

{

double difference = 0.0;

for (inti = 0; i<oldCentroid.size(); i++)

{

difference += Math.abs(oldCentroid.get(i) - newCentroid.get(i));

}

return (difference > 0.1);

}

}

private static String mInputFilename; private static String mOutputFilename;

private static ArrayList<Instance> instances = new ArrayList<>(); private static ArrayList<Cluster> clusters = new ArrayList<>(); private static intclusterCount;

private static int iteration = 0;

public static void main(String[] args) throws IOException

{

if (args.length< 3)

{

System.out.println("Arguments are incorrect. Try Application #inputfilename #outputfilename #clusterCount");

System.exit(0);

}

else

{

mInputFilename = args[0]; mOutputFilename = args[1];


clusterCount = Integer.parseInt(args[2]); readData();

cluster();

ArrayList<String> output = new ArrayList<>(); output.add("ITERATIONS : " + iteration);
for (Instance i : instances)

{

output.add(i.toString());

}

PutFileData(output);

}

}



// random number generator in a range, upper bound not included

public static intrandInt(int min, int max)

{

Random random = new Random();

return random.nextInt((max - min)) + min;

}

public static void cluster()

{

do {

System.out.println(++iteration);



if (iteration == 1)

{

// initialize clusters

for (inti = 0; i<clusterCount; i++)




{
Cluster c = new Cluster(); c.name = i + "";

c.oldCentroid.addAll(instances.get(i).mAttributes); clusters.add(c);
}

}

else

{

// copy new mean to old

for (inti = 0; i<clusterCount; i++)

{

clusters.get(i).oldCentroid.clear();

clusters.get(i).oldCentroid.addAll(clusters.get(i).newCentroid);

}

}



// assign instances

for (Instance i : instances)

{

ArrayList<Double> distances = new ArrayList<>(); for (Cluster c : clusters)

{

distances.add(getDistance(i.mAttributes, c.oldCentroid));

}

i.mClass = clusters.get(distances.indexOf(Collections.min(distances))).name;

}


// calc new mean

for (int j = 0; j <clusterCount; j++)

{

ArrayList<Double> centroid = new ArrayList<>(); for (inti = 0; i<clusters.get(j).oldCentroid.size(); i++)

{

double sum = 0.0; int count = 0;

for (Instance inst : instances)

{

if (inst.mClass.equals(clusters.get(j).name))

{

sum += inst.mAttributes.get(i); count++;
}

}

centroid.add(sum / count);

}

clusters.get(j).newCentroid.clear(); clusters.get(j).newCentroid.addAll(centroid);

}



} while (isStillMoving()&& iteration < 10);

}

public static booleanisStillMoving()

{

for(inti=0;i<clusterCount;i++)

{


if(clusters.get(i).isStillMoving())

{

return true;

}

}

return false;



}



public static double getDistance(ArrayList<Double> a, ArrayList<Double> b)

{

double distance = 0;

for (inti = 0; i<a.size(); i++)

{

double x = a.get(i); double y = b.get(i); distance += (x - y) * (x - y);

}

return Math.sqrt(distance);

}



// get data

public static void readData() throws IOException

{

StringTokenizer st; int size; ArrayList<String> line; line = GetFileData();

for (String str : line)

{

Instance value = new Instance(); st = new StringTokenizer(str, ","); size = st.countTokens();

for (inti = 0; i< size; i++)

{

value.mAttributes.add(Double.parseDouble(st.nextToken()));

}

instances.add(value);

}

}



// File read write functions

public static ArrayList<String>GetFileData() throws IOException

{

String line;

ArrayList<String>fileData = new ArrayList<String>();

BufferedReaderbufferedReader = new

BufferedReader(new FileReader(mInputFilename)); while ((line = bufferedReader.readLine()) != null)
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

for (String line : output)

{

bufferedWriter.write(line);

bufferedWriter.newLine();

}

bufferedWriter.close();

}

}
