import java.util.Arrays;


public class NullInfo {
	public static class Counts {
		int pass = 0;
		int fail = 0;
	}
	
	Counts[] data;
	
	public NullInfo(int params) {
		data = new Counts[(int) Math.pow(2, params)];
		for (int ndx = 0; ndx < data.length; ndx++)
			data[ndx] = new Counts();
	}
	
	/**
	 * A true value in data means that the param is non-null.
	 */
	public void addDataPoint(boolean[] params, boolean threwException)
	{
		int ndx = 0;
		for (boolean b : params) {
			ndx = ndx << 1;
			if (b)
				ndx++;
		}
		if (threwException)
			data[ndx].fail++;
		else
			data[ndx].pass++;
	}
	
	public String toString() {
		String str = "";
		for (int ndx = 0; ndx < data.length; ndx++)
		{
			str += "Index " + ndx + "\n";
			str += "\tPass: " + data[ndx].pass + "\n";
			str += "\tFail: " + data[ndx].fail +"\n";
		}
		return str;
	}
}
