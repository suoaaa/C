package ���ָ�;

public class RoleText {
    public static void main(String[] args) {
        Role r1=new Role("�Ƿ�",100);
        Role r2=new Role("�Ħ��",100);
        boolean flag=true;
        while(flag){
            r1.attack(r2);
            if(r2.getBlood()>0) r2.attack(r1);
            if(r1.getBlood()<=0||r2.getBlood()<=0) flag=false;
        }
    }
}
