public class A
{public static void main(String args[])
{String s=new String("");
System.out.println("������="+s.getClass().getName());
System.out.println("������="+s.getClass().getSuperclass().getName());
System.out.println("����="+s.getClass().getPackage().getName());
System.out.println("����ϵͳ="+System.getProperty("os.name"));
System.out.println("Java�汾="+System.getProperty("java.vm.version"));
System.out.println("�ڴ�����="+Runtime.getRuntime().totalMemory());
System.out.println("ʣ��ռ�="+Runtime.getRuntime().freeMemory());
}
}
