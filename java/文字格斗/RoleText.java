package ÎÄ×Ö¸ñ¶·;

public class RoleText {
    public static void main(String[] args) {
        Role r1=new Role("ÇÇ·å",100);
        Role r2=new Role("ð¯Ä¦ÖÇ",100);
        boolean flag=true;
        while(flag){
            r1.attack(r2);
            if(r2.getBlood()>0) r2.attack(r1);
            if(r1.getBlood()<=0||r2.getBlood()<=0) flag=false;
        }
    }
}
