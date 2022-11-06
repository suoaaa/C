		int a=strlen(str);
    	int b=strlen(substr);
    	if(a<b) return 0;
    	else{
    	int c=0;
    	int j=0;
    	int num=0;
    	int i=0;
    	while(i<a)
    	{
        	if(str[i]==substr[j])
        	{
            	j++;
            	if(j==b)
        		{
            	num++;j=0;
        		}
        	}   else j=0;
             	i++;  
    	}
    	cout<<"match times="<<num;
    }