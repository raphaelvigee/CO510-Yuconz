package Framework.Authentication;

public interface UserDataSourceInterface<ID>
{
    UserInterface getUser(ID id);

    UserInterface getUser(String username, String password);
}
