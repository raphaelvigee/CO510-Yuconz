package Yuconz.Configuration;

import com.sallyf.sallyf.Authentication.Configuration;
import com.sallyf.sallyf.Authentication.DataSource.InMemoryDataSource;
import com.sallyf.sallyf.Authentication.User;
import com.sallyf.sallyf.Authentication.UserDataSourceInterface;

import java.util.ArrayList;

public class AuthenticationConfiguration extends Configuration
{
    @Override
    public ArrayList<UserDataSourceInterface> getDataSources()
    {
        ArrayList<UserDataSourceInterface> dataSources = new ArrayList<>();

        InMemoryDataSource<User> memoryDS = new InMemoryDataSource<>();
        memoryDS.addUser(new User("admin", "password"));
        memoryDS.addUser(new User("user1", "password"));
        memoryDS.addUser(new User("user2", "password"));
        dataSources.add(memoryDS);


        return dataSources;
    }
}
