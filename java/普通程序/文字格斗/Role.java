package ��ͨ����.���ָ�;

import java.util.Random;

public class Role {
    private String name;
    private int blood;

    private String face;

    String[] faces={"��������","��ٶ���","��òƽƽ","�������"};
    String[] attacks_desc={
            "%sʹ����һ��{���Ķ�}��ת���Է����һ����%s�������̨Ѩ��ȥ,",
            "%s���һ���������¸�������{����׹��}������%s˫��,",
            "%s�ϲ������������У�����{��������}����������%s,"
    };
    String[] injures_desc={
            "��%s���һ������,��ʣ%d��Ѫ��",
            "һ�����У�%sʹ��������,��ʣ%d��Ѫ��",
            "���%sһ���ҽУ�˫Ŀ����,��ʣ%d��Ѫ��",
    };

    public Role() {
        face="��òƽƽ";
    }

    public Role(String name, int blood) {
        this.name = name;
        this.blood = blood;
        Random r=new Random();
        face=faces[r.nextInt(faces.length)];
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBlood() {
        return blood;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getFace() {
        return face;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public void showInfo() {
        System.out.println("����Ϊ"+getName());
        System.out.println("Ѫ��Ϊ"+getBlood());
        System.out.println("��òΪ"+getFace());
    }

    public void attack(Role role){
        int hurt;
        Random r=new Random();
        hurt =r.nextInt(19)+1;
        role.blood-=hurt;
        System.out.printf(attacks_desc[r.nextInt(attacks_desc.length)],this.getName(),role.getName());
        if(role.getBlood()>0) {
            System.out.printf(injures_desc[r.nextInt(injures_desc.length)], role.getName(),role.getBlood());
            System.out.println();
        }
        else System.out.println(role.getName()+"����,"+this.getName()+"��ʤ");
    }
}
