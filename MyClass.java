
public class MyClass {
	public void foo(String x, String y)
	{
		if (x.length() > 5)
			y = "hello";
	}

	public void noNullsAllowed(String x, String y)
	{
		if (x.length() > 5)
			y.charAt(4);
	}
	
	public String nullOk(String x)
	{
		x = "hi there";
		return x;
	}
	
	public static void main(String[] args)
	{
		MyClass obj = new MyClass();
		try {
		obj.foo(null, "hi");
		}
		catch (Exception err){}
		try {
		obj.foo("hello", "hi");
		}
		catch (Exception err){}
		try {
		obj.foo("hi", "hi");
		}
		catch (Exception err){}
		try {
		obj.foo(null, null);
		}
		catch (Exception err){}
	}
}
