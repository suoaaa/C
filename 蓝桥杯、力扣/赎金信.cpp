/*给你两个字符串：ransomNote 和 magazine ，判断 ransomNote 能不能由 magazine 里面的字符构成。

如果可以，返回 true ；否则返回 false 。

magazine 中的每个字符只能在 ransomNote 中使用一次。*/
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