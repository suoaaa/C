package ÆÕÍ¨³ÌĞò;

public class ·ÂÉäÃÜÂë1 {
    public static void main(String[] args) {
        StringBuilder m=new StringBuilder();
        StringBuilder c=new StringBuilder("cqvjlvovqqtvoevvwshwbjmzrooevtzuhv");
        for(int i=0;i<c.length();i++){
            m.append((char)(((c.charAt(i)-'a'+1)*9-11)%26+'a'-1));
        }
        System.out.println(m);
    }
    

}
