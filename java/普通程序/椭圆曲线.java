package ��ͨ����;

public class ��Բ���� {
    public static void main(String[] args) {
        for(int i=1;i<14;i++){
            int j=1;
            while(j<80){
                int k=0;
                while(k<400){
                    if(i*i*i+i*3+2==j*j+k*7||i*i*i+i*3+2==j*j-k*7){
                        System.out.println(i+"��"+j);
                        j=80;
                        break;
                    }
                k++;
                }
                j++;
            }
        }
    }
}
