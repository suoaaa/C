/**/
#include<stdio.h>
#include<iostream>
#include<string.h>
#include<set>
#include<vector>
#include<map>
#include<algorithm>
using namespace std;
bool gen_dsa(char *pdata,int datalen, unsigned char sign_value[1024],int &sign_len);
bool verify_dsa(char *pdata,int datalen);
int main(){

    return 0;
}
bool gen_dsa(char *pdata,int datalen,unsigned char sign_value[1024],const int &sign_len)
{
	EVP_MD_CTX      sign_ctx;
    EVP_PKEY      *pkey;
	EVP_PKEY      *publickey;
    int i, ret;

     /* 载入所有的算法 */
     OpenSSL_add_all_algorithms();

     /*
      * 载入所有的错误信息
      */
     ERR_load_ERR_strings();
     ERR_load_crypto_strings();
	 ERR_print_errors_fp(stderr);

     /*
      * 从文件中装载私钥，密码为 "test"
      */
     if ((pkey = LoadPrivateKey("client.key", "test")) == NULL)
     {
           printf("unable to load private key\n");
           return (EXIT_FAILURE);
     }

     /* 初始化，选择 SHA1 + RSA 为签名算法 */
     EVP_SignInit(&sign_ctx, EVP_sha1());

     /* 如果有很多 messages，可以将 EVP_SignUpdate() 写成 loop */
     EVP_SignUpdate(&sign_ctx, pdata, datalen);
     /* 产生签名结果，存在 sign_value 中，长度存于 md_len */
     EVP_SignFinal(&sign_ctx, sign_value, (unsigned int *)&sign_len, pkey);
	 return true;
}