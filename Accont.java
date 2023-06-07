package NewFinalProject;

public class Accont {//存放VIP账户名单和密码
    private int id;
    private String password;
    public Accont()
    {
    }
    public Accont(int id,String passd)
    {
        setId(id);
        setPassword(passd);
    }
    public void setId(int ID)
    {
        id=ID;
    }
    public int getId()
    {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
