package NewFinalProject;

public abstract class  Room{//抽象父类
    private String Size;//房型
    private String DetailedSize;//具体信息
    private int id;//编号id
    private int number;//数量
    private double price;//价格(间/天)
    private int days;//住宿天数
    private double payMoney;//应付住宿金额

    public Room(){
    }//空构造
    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }
    public int getId()
    {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public String getDetailedSize() {
        return DetailedSize;
    }

    public void setDetailedSize(String DetailedSize) {
        this.DetailedSize = DetailedSize;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public abstract double RecentFee(int days, int vip);//抽象支付计算方法

}

