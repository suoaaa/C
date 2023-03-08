package ÆÕÍ¨³ÌĞò;

public class ·ÂÉäÃÜÂë2 {
    public static void main(String[] args) {
        StringBuilder m=new StringBuilder();
        StringBuilder c=new StringBuilder("edsgickxhuklzveqzvkxwkzukvcuh");
        for(int i=0;i<c.length();i++){
            m.append((char)(((c.charAt(i)-'a')*3-4)%26+'a'));
        }
        System.out.println(m);
    }
    

}
