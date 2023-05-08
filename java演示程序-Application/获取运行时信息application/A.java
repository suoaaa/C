public class A
{public static void main(String args[])
{String s=new String("");
System.out.println("本类名="+s.getClass().getName());
System.out.println("超类名="+s.getClass().getSuperclass().getName());
System.out.println("包名="+s.getClass().getPackage().getName());
System.out.println("操作系统="+System.getProperty("os.name"));
System.out.println("Java版本="+System.getProperty("java.vm.version"));
System.out.println("内存总量="+Runtime.getRuntime().totalMemory());
System.out.println("剩余空间="+Runtime.getRuntime().freeMemory());
}
}
