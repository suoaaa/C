package 方法;

public class 拷贝数组 {
    public static void main(String[] args) {
        int num[]={0,1,2,3,4,5,6,7,8};
        int out[]=copyOfRange(num,3,7);
        System.out.println(out[1]);
    }
    public static int[] copyOfRange(int[] num,int from,int to) {
        if(to>from){
            int[] out=new int[to-from+1];
            for (int i = from-1,j=0; i < to;j++, i++) {
                out[j]=num[i];
            }
            return out;
        }
        else {
            int[] out=null;
            return out;
        }

    }
}
