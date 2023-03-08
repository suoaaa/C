package 普通程序;

import java.util.Scanner;

public class 金额转换 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        long money=1;
        System.out.println("请输入一个七位数以内的正整数：");
        while(true){
            money=sc.nextLong();
            if(money>0&&money<9999999) break;
            else System.out.println("数额错误，请重新输入：");
        }
        String out=change((int)(money));
        System.out.println("该数字转化为大写金额为：");
        System.out.println(out);
        sc.close();
    }

    public static String change(int num) {
        StringBuilder out=new StringBuilder("元");
        StringBuilder arr=new StringBuilder("");
        int length=0;
        while(num>0){
            arr.append(num%10);
            num=num/10;
            length++;
        }
        arr.reverse();
        char []index={'\0','拾','佰','仟','万','拾','佰','仟'};
        char []bigNum={'零','壹','贰','弎','肆','伍','陆','柒','捌','玖'};
        for(int i=length-1;i>=0;i--){
            if((i-1>=0&&arr.charAt(i)-'0'!=0)||i==0||i==length-1)	out.append(index[length-i-1]);
            if((i==length-1&&arr.charAt(i)=='0')||(arr.charAt(i)=='0'&&arr.charAt(i+1)=='0')){}
            else out.append(bigNum[arr.charAt(i)-'0']);
        }
        out.reverse();
        String ret=out.toString();
        return ret;
    }
}
