package NewFinalProject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserSys {//用户系统类
    public static List<Room> customerBigBedRoom = new ArrayList<>();//全局集合，存放大床房
    public static List<Room> customerDoubleRooms = new ArrayList<>();//全局集合，存放双床房
    public static List<Room> customerSuite = new ArrayList<>();//全局集合，存放套房
    public static List<Accont> VIPacount=new ArrayList<>();//全局集合，存放VIP用户账户及密码

    private static int VipIdentify=0;
    public static void setVipIdentify(int Identify)
    {
        VipIdentify=Identify;
    }
    public static int getVipIdentify()
    {
        return VipIdentify;
    }


    public static void User(List<Room> BigBedRoom, List<Room> DoubleRooms,
                            List<Room> Suite, Scanner user) {//用户交互方法
        while (true) {
            //打印
            System.out.println("*****欢迎光临往世乐土民宿客房预约系统*****");
            System.out.println("首次使用请先注册");
            System.out.println("1-预订房间");
            System.out.println("2-查询当前系统房间");
            System.out.println("3-查看及编辑我的预订订单");
            System.out.println("4-VIP注册");
            System.out.println("5-VIP登录");
            System.out.println("6-返回主页面");
            System.out.println("请输入您想要进行的操作：");
            int using = user.nextInt();
            //判断用户输入命令
            switch (using) {
                case 1:
                    recentRoom(BigBedRoom, DoubleRooms, Suite, user);
                    break;
                case 2:
                    ManagerSys.queryRoom();//管理系统所有房间
                    break;
                case 3:
                    ManagerSys.queryOrder(customerBigBedRoom, customerDoubleRooms, customerSuite);//用户预订订单
                    break;
                case 4:
                    Register(user);//注册
                    break;
                case 5:
                    CheckPwd(user);//VIP登录验证
                    break;
                case 6:
                    ManagerSys.show();//主界面
                    break;
                default:
                    System.out.println("请做个遵守规则的用户哦\n");
            }
        }
    }
    private static void Register(Scanner user)//VIP账户注册方法
    {
        while (true)
        {
            System.out.println("请输入id账号(四位数字,开头不为0):");
            int id = user.nextInt();
            if(id<1000||id>9999)
            {
                System.out.println("请输入正常的id");
            }
            else
            {
                System.out.println("请输入密码:");
                String pwd = user.next();
                Accont accont=new Accont(id,pwd);
                VIPacount.add(accont);
            }
            break;
        }
    }
    private static void CheckPwd( Scanner user) //登录验证方法
    {
        int Times=0;
        while (true) {
            System.out.println("请输入账号:");
            int id = user.nextInt();
            System.out.println("请输入密码:");
            String pwd = user.next();
            for( Times=3;Times>=0;Times--)
            {
                for (int i=0;i<VIPacount.size();i++)
                {
                    if(Times==5)
                    {
                        break;
                    }
                    if(VIPacount.get(i).getId()==id)
                    {
                        for(int times=3;times>=0;times--)
                        {
                            if((pwd.equalsIgnoreCase(VIPacount.get(i).getPassword())))
                            {
                                setVipIdentify(1);
                                Times=5;
                                break;
                            }
                            else if (times==0)
                            {
                                System.out.println("次数已用完，将返回主界面");
                                ManagerSys.show();
                                break;
                            }
                            else
                            {
                                System.out.println("密码错误,还剩"+(times-1)+"次机会");
                                pwd = user.next();
                            }
                        }
                    }
                }//遍历确定账户
                if(Times==0)
                {
                    System.out.println("次数已用完，将返回主界面");
                    ManagerSys.show();
                    break;
                }
                if(Times==5)
                {
                    break;
                }
                System.out.println("找不到账号，还有"+Times+"次机会重新输入");
                id=user.nextInt();
            }
            if(Times==5)
            {
                break;
            }
        }
    }


    private static void recentRoom(List<Room> BigBedRoom, List<Room> DoubleRooms,
                                   List<Room> Suite, Scanner user) {//预订房间方法
        ManagerSys.queryRoom();//打印管理系统所有现存房间
        int userIn = ManagerSys.checkRoom(user);//提示用户输入房间类型并检测合法性
        System.out.println("房间信息如下：");
        if (userIn == 1) {
            for (Room room : BigBedRoom) {//遍历打印所有大床房供用户选择
                System.out.println(room.getDetailedSize() + " " + room.getPrice() + "元/天，早餐加100,在退房时结算");
            }
            System.out.println("请输入您想要预订的房间类型：");
            String userDetailedSize = user.next();
            Room bigbed = RoomFactory(userDetailedSize, BigBedRoom, DoubleRooms, Suite, userIn);//工厂创建一个新的大床房对象
            if (bigbed.getId() == 0) {//管理系统中找不到用户输入的房间
                System.out.println("您输入的房间类型有误！");
            } else {//管理系统中找到了用户输入的大床房
                System.out.println("请输入您想要预订的天数：");
                bigbed.setDays(user.nextInt());//预订天数存入大床房对象
                bigbed.setPayMoney(bigbed.RecentFee(bigbed.getDays(),getVipIdentify()));//计算预订费用并存入大床房对象
                customerBigBedRoom.add(bigbed);//在全局大床房集合中添加创建的大床房对象
                ManagerSys.removeBigBedRoom(bigbed);//在管理系统中移除创建的大床房对象，代表房间已经被预订
                System.out.println("预订成功！您预订的是：" + bigbed.getDetailedSize());
                System.out.println("共预订" + bigbed.getDays() + "天，需要支付的租金为：￥" + bigbed.getPayMoney());
            }
        } if (userIn == 2) {
            for (Room room : DoubleRooms) {//遍历打印所有双床房供用户选择
                System.out.println(room.getDetailedSize() + " " + room.getPrice() + "元/天，早餐加100，在退房时结算");
            }
            System.out.println("请输入您想要预订的房间类型：");
            String userDetailedSize = user.next();
            Room doublbed = RoomFactory(userDetailedSize, BigBedRoom, DoubleRooms, Suite, userIn);//工厂创建一个新的双床房对象
            if (doublbed.getId() == 0) {//管理系统中找不到用户输入的双床房对象
                System.out.println("您输入的房间类型有误！");
            } else {//管理系统中找到了用户输入的双床房
                System.out.println("请输入您想要预订的天数：");
                doublbed.setDays(user.nextInt());//预订天数存入双床房对象
                doublbed.setPayMoney(doublbed.RecentFee(doublbed.getDays(),getVipIdentify()));//计算预订费用并存入双床房对象
                customerDoubleRooms.add(doublbed);//在全局双床房集合中添加创建的双床房对象
                ManagerSys.removeDoubleRooms(doublbed);//在管理系统中移除创建的双床房对象，代表房间已被预订
                System.out.println("预订成功！您预订的是：" + doublbed.getDetailedSize());
                System.out.println("共预订" + doublbed.getDays() + "天，需要支付的租金为：￥" + doublbed.getPayMoney());
            }
        } if (userIn == 3) {
            for (Room room : Suite) {//遍历打印所有套房品牌供用户选择
                System.out.println(room.getDetailedSize() + " " + room.getPrice() + "元/天，早餐加100，在退房时结算");
            }
            System.out.println("请输入您想要预订的房间类型：");
            String userDetailedSize = user.next();
            System.out.println("请输入您想要租赁的房间类型：");
            Room set = RoomFactory(userDetailedSize, BigBedRoom, DoubleRooms, Suite, userIn);//工厂创建一个新的套房对象
            if (set.getId() == 0) {//管理系统中找不到用户输入的套房对象
                System.out.println("您输入的房间类型有误！");
            } else {//管理系统中找到了用户输入的套房对象
                System.out.println("请输入您想要预订的天数：");
                set.setDays(user.nextInt());//预订天数存入套房对象
                set.setPayMoney(set.RecentFee(set.getDays(), getVipIdentify()));//计算预订费用并存入套房对象
                customerSuite.add(set);//在全局双床房集合中添加创建的套房对象
                ManagerSys.removeSuite(set);//在管理系统中移除创建的套房对象，代表房间已被预订
                System.out.println("预订成功！您预订的是：" + set.getDetailedSize());
                System.out.println("共预订" + set.getDays() + "天，需要支付的租金为：￥" + set.getPayMoney());
            }
        }


    }

    public static Room RoomFactory(String userDetailedSize, List<Room> BigBedRooom, List<Room> DoubleRooms, List<Room> Suite, int useIn) {//房间工厂按需求创造对象
        String detailedsize = "";//初始化
        double price = 0;
        int id = 0;
        if (useIn == 1) {//创造大床房
            for (Room room : BigBedRooom) {
                if (userDetailedSize.equalsIgnoreCase(room.getDetailedSize()))//寻找系统是否存在该大床房
                {
                    detailedsize = room.getDetailedSize();
                    price = room.getPrice();
                    id = room.getId();
                    break;
                }
            }
            return new BigBedRoom(detailedsize,1,price, id);//返回一个新的大床房对象
        } else if (useIn == 2) {
            for (Room room : DoubleRooms) {
                if (userDetailedSize.equalsIgnoreCase(room.getDetailedSize())) //寻找系统是否存在该双床房
                {
                    detailedsize = room.getDetailedSize();
                    price = room.getPrice();
                    id = room.getId();
                    break;
                }
            }
            return new DoubleRooms(detailedsize, 1,price, id);//返回一个新的双床房对象
        } else if (useIn == 3) {
            for (Room room : Suite) {
                if (userDetailedSize.equalsIgnoreCase(room.getDetailedSize())) //寻找系统是否存在该套房
                {
                    detailedsize = room.getDetailedSize();
                    price = room.getPrice();
                    id = room.getId();
                    break;
                }
            }
            return new Suite(detailedsize,1, price, id);//返回一个新的套房对象
        } else {
            return null;
        }
    }
}
