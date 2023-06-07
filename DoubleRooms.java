package NewFinalProject;

public class DoubleRooms extends Room{//双床房
    public DoubleRooms(String DeatailInf,int num,double price,int id)
    {
        setId(id);
        setDetailedSize(DeatailInf);
        setNumber(num);
        setPrice(price);
    }
    @Override
    public double RecentFee(int days, int vip) {//重写支付计算方法
        // TODO Auto-generated method stub
        Double money = this.getPrice()*days;//得到折扣前应付租金
        if(vip==1 && days>=2)
        {
            money=0.6*money;
        }
        else if(days >= 3) {
            money *= 0.8*money;
        }
        return money;//返回打折后应付金额
    }
}
