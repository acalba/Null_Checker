import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Around;

@Aspect
public class NullInferer {
	Map<String, NullInfo> data = new HashMap<String, NullInfo>();
	
	@Pointcut("execution(* MyClass.*(..))")
	public void foobar() {}
	
	@Pointcut("execution(public static void *.main(String[]))")
	public void mains() {}

	@Around("foobar() && target(foo)")
	public Object inferNulls(ProceedingJoinPoint jp, MyClass foo) throws Throwable
	{
		String sig = jp.getSignature().toLongString();
		NullInfo info = data.get(sig);
		
		boolean[] params = new boolean[jp.getArgs().length];
		boolean threwException = false;
		
		if (info == null)
		{
			info = new NullInfo(jp.getArgs().length);
			data.put(sig,  info);
		}

		for (int ndx = 0; ndx < params.length; ndx++)
			params[ndx] = jp.getArgs()[ndx] != null;
		
		try {
			Object ret = jp.proceed();
			return ret;
		} catch (Throwable e) {
			threwException = true;
			throw e;
		}
		finally {

			info.addDataPoint(params, threwException);
		}
	}
	
	@After("mains()")
	public void printResults() {
		for (Entry<String, NullInfo> entry : data.entrySet())
		{
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}

	}

}
