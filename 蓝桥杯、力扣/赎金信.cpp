/*���������ַ�����ransomNote �� magazine ���ж� ransomNote �ܲ����� magazine ������ַ����ɡ�

������ԣ����� true �����򷵻� false ��

magazine �е�ÿ���ַ�ֻ���� ransomNote ��ʹ��һ�Ρ�*/
#include <iostream>
#include<vector>
using namespace std;
class Solution {
public:
    bool canConstruct(string ransomNote, string magazine) {
        int m=ransomNote.length();
        int n=magazine.length();
        vector<bool>flag(n,false);
        bool ret;
        char temp;
        for(int i=0;i<m;i++){
            ret=false;
            temp=ransomNote.at(i);
            for(int j=0;j<n;j++){
                if(temp==magazine.at(j)&&flag[j]==false) {
                    flag[j]=true;
                    ret=true;
                    break;
                }
            }
            if(ret==false) return false;
        }
        return true;
    }
};