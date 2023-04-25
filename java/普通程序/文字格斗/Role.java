package 普通程序.文字格斗;

import java.util.Random;

public class Role {
    private String name;
    private int blood;

    private String face;

    String[] faces={"气宇轩昂","五官端正","相貌平平","歪瓜裂枣"};
    String[] attacks_desc={
            "%s使出了一招{背心钉}，转到对方身后，一掌想%s背后的灵台穴拍去,",
            "%s大喝一声，身形下附，依照{霹雷坠地}，垂向%s双腿,",
            "%s上步抢身，招中套招，依照{披挂连环}，连环攻向%s,"
    };
    String[] injures_desc={
            "给%s造成一处瘀伤,还剩%d滴血量",
            "一击命中，%s痛的弯下腰,还剩%d滴血量",
            "结果%s一声惨叫，双目狰狞,还剩%d滴血量",
    };

    public Role() {
        face="相貌平平";
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
        System.out.println("姓名为"+getName());
        System.out.println("血量为"+getBlood());
        System.out.println("外貌为"+getFace());
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
        else System.out.println(role.getName()+"阵亡,"+this.getName()+"获胜");
    }
}
