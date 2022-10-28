while(user_name[i][j]==name[i][j])
    {
        j++;
    }
    while(user_name[i]==name[i])
    {
        i++;
    }
    while(user_pass[j]=pass[j])
    {
        j++;
    }
    if(user_name[i]=='\0'&&user_pass[j]=='\0'&&name[i]=='\0'&&pass[j]=='\0'&&)
    {
        return 0;
    } else return -1;