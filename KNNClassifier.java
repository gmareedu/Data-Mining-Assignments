public class KNNClassifier
{

    private static class Instance
    
    {
    
    ArrayList<Double>mAttributes = new ArrayList<>();
    
    String mClass;
    
    @Override
    
    public String toString()
    
    {
    
    return mAttributes.toString() + "\t" + mClass;
    
    }
    
    }
    
    private static class PredictInstance extends Instance
    
    {
    
    ArrayList<Double>mDistances = new ArrayList<>();
    
    ArrayList<Integer>mNeighbours = new ArrayList<>();
    
    
    
    ArrayList<Double>mVote = new ArrayList<>();
    
    }
    
    private static String mInputFilename; private static String mInputTestFilename; private static String mOutputFilename; private static int K;
    
    private static String[] classNames = { "Y", "N" }; private static List<Instance>mTrainData;
    
    private static List<PredictInstance>mPredictData;
    
    public static void main(String[] args) throws IOException
    
    {
    
    if (args.length< 4)
    
    {
    
    System.out.println("Arguments are incorrect. Try Application #inputfilename #testfilename #outputfilename #k-value");
    
    System.exit(0);
    
    }
    
    else
    
    {
    
    mInputFilename = args[0]; mInputTestFilename = args[1]; mOutputFilename = args[2]; K = Integer.parseInt(args[3]); readData();
    
    classify();
    
    ArrayList<String> output = new ArrayList<>(); output.add("K-Value : " + K);
    
    for (PredictInstance t : mPredictData)
    
    {
    
    output.add(t.toString());
    
    }
    
    PutFileData(output);
    
    }
    
    }
    
    public static void classify()
    
    {
    
    // compute distances for all test instances
    
    for (PredictInstance t : mPredictData)
    
    {
    
    for (Instance i : mTrainData)
    
    {
    
    t.mDistances.add(getDistance(t.mAttributes, i.mAttributes));
    
    }
    
    }
    
    
    
    
    
    
    // sorting the distances and getting k-nearest class frequency
    
    for (PredictInstance t : mPredictData)
    
    {
    
    t.mNeighbours.addAll(getNearestNeighbours(t.mDistances)); for (Integer i : t.mNeighbours)
    
    {
    
    if (mTrainData.get(i).mClass.equalsIgnoreCase(classNames[0]))
    
    {
    
    t.mVote.set(0, t.mVote.get(0) + (1 / t.mDistances.get(i)));
    
    }
    
    else
    
    {
    
    
    t.mVote.set(1, t.mVote.get(1) + (1 / t.mDistances.get(i)));
    
    }
    
    }
    
    }
    
    
    
    // comparing weights and selecting a class
    
    for (PredictInstance t : mPredictData) {
    
    t.mClass = classNames[t.mVote.indexOf(Collections.max(t.mVote))];
    
    }
    
    }
    
    public static ArrayList<Integer>getNearestNeighbours(ArrayList<Double> distances)
    
    {
    
    ArrayList<Integer> indexes = new ArrayList<>(K); ArrayList<Double> values = new ArrayList<>(); values.addAll(distances);
    
    Collections.sort(values); for (inti = 0; i< K; i++)
    
    {
    
    indexes.add(distances.indexOf(values.get(i)));
    
    }
    
    return indexes;
    
    }
    
    public static double getDistance(ArrayList<Double> a, ArrayList<Double> b)
    
    {
    
    double distance = 0;
    
    for (inti = 0; i<a.size(); i++)
    
    {
    
    double x = a.get(i);
    
    
    double y = b.get(i); distance += (x - y) * (x - y);
    }
    
    return Math.sqrt(distance);
    
    }
    
    
    
    // get data
    
    public static void readData() throws IOException
    
    {
    
    StringTokenizer st;
    
    mTrainData = new ArrayList<>(); mPredictData = new ArrayList<>(); int size;
    
    ArrayList<String> line; line = GetFileData(); for (String str : line)
    {
    
    Instance value = new Instance(); st = new StringTokenizer(str, ","); size = st.countTokens();
    
    for (inti = 0; i< size - 1; i++)
    
    {
    
    value.mAttributes.add(Double.parseDouble(st.nextToken()));
    
    }
    
    value.mClass = st.nextToken(); mTrainData.add(value);
    }
    
    line = GetTestFileData();
    
    for (String str : line)
    
    {
    
    PredictInstance value = new PredictInstance();
    st = new StringTokenizer(str, ",");
    
    size = st.countTokens(); for (inti = 0; i< size; i++)
    
    {
    
    value.mAttributes.add(Double.parseDouble(st.nextToken()));
    
    }
    
    value.mClass = "unknown";
    
    for (int j = 0; j <classNames.length; j++)
    
    {
    
    value.mVote.add(0.0);
    
    }
    
    mPredictData.add(value);
    
    }
    
    
    
    }
    
    
    
    // File read write functions
    
    public static ArrayList<String>GetFileData() throws IOException
    
    {
    
    String line;
    ArrayList<String>fileData = new ArrayList<String>(); BufferedReaderbufferedReader = new
    
    BufferedReader(new FileReader(mInputFilename)); while ((line = bufferedReader.readLine()) != null)
    {
    
    
    
    fileData.add(line);
    
    }
    
    bufferedReader.close(); return fileData;
    }
    
    public static ArrayList<String>GetTestFileData() throws IOException
    
    {
    
    String line;
    
    ArrayList<String>fileData = new ArrayList<String>(); BufferedReaderbufferedReader = new
    
    BufferedReader(new FileReader(mInputTestFilename)); while ((line = bufferedReader.readLine()) != null)
    
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
    }  }
    
    