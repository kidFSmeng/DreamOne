package NewFinalProject;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ManagerSys {//管理系统类
    private static List<Room> BigBedRoom = new ArrayList<>();//管理系统大床房集合
    private static List<Room> DoubleRooms = new ArrayList<>();//管理系统双床房集合
    private static List<Room> Suite = new ArrayList<>();//管理系统套房集合
    public static List<Accont> Normolacount = new ArrayList<>();//全局集合，存放普通用户账户及密码

    public static void main(String[] args) {
        //默认添加管理系统3种大床房
        BigBedRoom.add(new BigBedRoom("高级大床房", 2, 600, 1));
        BigBedRoom.add(new BigBedRoom("豪华大床房", 2, 700, 2));
        BigBedRoom.add(new BigBedRoom("景观大床房", 2, 850, 3));
        //默认添加管理系统3种双床房
        DoubleRooms.add(new DoubleRooms("标准双床房", 2, 600, 1));
        DoubleRooms.add(new DoubleRooms("高级双床房", 2, 800, 2));
        DoubleRooms.add(new DoubleRooms("豪华双床房", 2, 1000, 3));
        //默认添加管理系统2种货车
        Suite.add(new Suite("景致小套房", 1, 1500, 1));
        Suite.add(new Suite("婴儿小套房", 1, 2000, 2));
        Suite.add(new Suite("亲子大套房", 1, 2500, 3));

        show();//系统选择交互界面
    }

    public static void show() {//系统选择交互界面方法
        while (true) {
            System.out.println("--------欢迎进入往世乐土民宿客房预约系统--------");
            System.out.println("1-进入管理员系统");
            System.out.println("2-进入普通租赁用户系统");
            System.out.println("3-退出");
            System.out.println("请输入您想要进行的操作：");
            Scanner user = new Scanner(System.in);
            int temp = user.nextInt();
            //输入命令判断
            switch (temp) {
                case 1:
                    CheckPwd(user);//管理员登录验证
                    break;
                case 2:
                    System.out.println("首次使用请先注册，如果已有账户请直接登录");
                    System.out.println("1-注册，2-登录");
                    int choice = user.nextInt();
                    if (choice == 1) {
                        Register(user);
                    } else if (choice == 2) {
                        CheckPwdNom(user);
                    }
                    break;
                case 3:
                    System.out.println("感谢您的信任与支持，再见！");
                    System.exit(0);
                    break;
                default:
                    System.out.println("请做个遵守规则的用户哦\n");
            }
        }
    }

    private static void Register(Scanner user)//VIP账户注册方法
    {
        int id = 1;
        while (id != 0) {
            System.out.println("请输入id账号(四位数字,开头不为0):");
            id = user.nextInt();
            if (id < 1000 || id > 9999) {
                System.out.println("请输入正常的id");
            } else {
                System.out.println("请输入密码:");
                String pwd = user.next();
                Accont accont = new Accont(id, pwd);
                Normolacount.add(accont);
            }
            break;
        }
    }

    private static void CheckPwdNom(Scanner user) {
        int Times;
        int id = 1;
        while (id != 0) {
            System.out.println("请输入账号:");
            id = user.nextInt();
            System.out.println("请输入密码:");
            String pwd = user.next();
            for (Times = 3; Times >= 0; Times--) {
                for (int i = 0; i < Normolacount.size(); i++) {
                    if (Times == 5) {
                        break;
                    }
                    if (Normolacount.get(i).getId() == id) {
                        for (int times = 3; times > 0; times--) {
                            if ((pwd.equalsIgnoreCase(Normolacount.get(i).getPassword()))) {
                                UserSys.User(BigBedRoom, DoubleRooms, Suite, user);//用户系统
                                Times = 5;
                                break;
                            } else if (times == 0) {
                                System.out.println("次数已用完，将返回主界面");
                                ManagerSys.show();
                                break;
                            } else {
                                System.out.println("密码错误,还剩" + (times - 1) + "次机会");
                                pwd = user.next();
                            }
                        }
                    }
                }//遍历确定账户
                if (Times == 0) {
                    System.out.println("次数已用完，将返回主界面");
                    ManagerSys.show();
                    break;
                }
                if (Times == 5) {
                    break;
                }
                System.out.println("找不到账号，还有" + Times + "次机会重新输入");
                id = user.nextInt();
            }
            if (Times == 5) {
                break;
            }
        }
    }

    private static void CheckPwd(Scanner user) {//登录验证方法
        while (true) {
            System.out.println("请输入管理员账号:");
            int id = user.nextInt();
            System.out.println("请输入管理员密码:");
            String pwd = user.next();
            if ((pwd.equalsIgnoreCase("Klein")) && (id == 1849)) {
                Manager(user);//管理员交互界面
                break;
            } else {
                System.out.println("账号或密码错误,请重新输入!");
            }
        }
    }

    private static void Manager(Scanner user) {//管理员交互界面方法
        while (true) {
            System.out.println("*****欢迎进入客房管理系统*****");
            System.out.println("1-查询当前系统房型");
            System.out.println("2-查询用户预约订单");
            System.out.println("3-增加客房数量");
            System.out.println("4-删除房型");
            System.out.println("5-修改客房价格");
            System.out.println("6-返回主页面");
            System.out.println("请输入您想要进行的操作：");
            int manage = user.nextInt();
            //输入命令判断
            switch (manage) {
                case 1:
                    queryRoom();//查询当前系统房间
                    break;
                case 2:
                    queryOrder(UserSys.customerBigBedRoom, UserSys.customerDoubleRooms, UserSys.customerSuite);//查询用户租赁订单
                    break;
                case 3:
                    addRoom(user);//增加房间
                    break;
                case 4:
                    deleteRoom(user);//删除房间
                    break;
                case 5:
                    updatePrice(user);//修改房间租金
                    break;
                case 6:
                    show();//主页面
                    break;
                default:
                    System.out.println("请做个遵守规则的用户哦\n");
            }
        }
    }

    private static void updatePrice(Scanner user) {//修改房间租金
        int userIn = checkRoom(user);//提示用户输入房间类型并检测合法性
        System.out.println("请输入您需要修改的房间编号");
        int userId = user.nextInt();
        System.out.println("请输入您需要修改的房间价格：");
        double roomPrice = user.nextDouble();
        if (userIn == 1) {//修改大床房租金
            for (int i = 0; i < BigBedRoom.size(); i++) {
                if (BigBedRoom.get(i).getId() == userId) {//遍历寻找唯一ID
                    System.out.println("房间：" + BigBedRoom.get(i).getDetailedSize()
                            + "的价格已成功修改为" + roomPrice);
                    BigBedRoom.get(i).setPrice(roomPrice);
                    return;
                } else if (i == BigBedRoom.size() - 1) {
                    System.out.println("对不起，没有找到您想要修改价格的房间！");
                }
            }
        } else if (userIn == 2) {//修改双床房价格
            for (int i = 0; i < DoubleRooms.size(); i++) {//遍历寻找唯一ID
                if (DoubleRooms.get(i).getId() == userId) {
                    System.out.println("房间：" + DoubleRooms.get(i).getDetailedSize()
                            + "的价格已成功修改为" + roomPrice);
                    DoubleRooms.get(i).setPrice(roomPrice);
                    return;
                } else if (i == DoubleRooms.size() - 1) {
                    System.out.println("对不起，没有找到您想要修改价格的房间！");
                }
            }
        } else if (userIn == 3) {//修改套房价格
            for (int i = 0; i < Suite.size(); i++) {//遍历寻找唯一ID
                if (Suite.get(i).getId() == userId) {
                    System.out.println("房间：" + Suite.get(i).getDetailedSize()
                            + "的价格已成功修改为" + roomPrice);
                    Suite.get(i).setPrice(roomPrice);
                    return;
                } else if (i == Suite.size() - 1) {
                    System.out.println("对不起，没有找到您想要修改价格的房间！");
                }
            }
        }
    }

    private static void deleteRoom(Scanner user) {//删除房间方法
        System.out.println("当前您的房间清单如下：");
        queryRoom();//打印当前系统房间
        int userIn = checkRoom(user);//提示用户输入房间类型并检测合法性
        System.out.println("请输入您需要删除房间的编号");
        int userId = user.nextInt();
        if (userIn == 1) {//删除大床房
            for (int i = 0; i < BigBedRoom.size(); i++) {
                if (BigBedRoom.get(i).getId() == userId) {
                    System.out.println("房间：" + BigBedRoom.get(i).getDetailedSize() + "成功删除！");
                    BigBedRoom.remove(BigBedRoom.get(i));
                    return;
                } else if (i == BigBedRoom.size() - 1) {
                    System.out.println("对不起，没有找到您想要删除的房间！");
                }
            }
        } else if (userIn == 2) {//删除双床房
            for (int i = 0; i < DoubleRooms.size(); i++) {
                if (DoubleRooms.get(i).getId() == userId) {
                    System.out.println("房间：" + DoubleRooms.get(i).getDetailedSize() + "成功删除！");
                    DoubleRooms.remove(DoubleRooms.get(i));
                    return;
                } else if (i == DoubleRooms.size() - 1) {
                    System.out.println("对不起，没有找到您想要删除的房间！");
                }
            }
        } else if (userIn == 3) {//删除套房
            for (int i = 0; i < Suite.size(); i++) {
                if (Suite.get(i).getId() == userId) {
                    System.out.println("房间：" + Suite.get(i).getDetailedSize() + "成功删除！");
                    Suite.remove(Suite.get(i));
                    return;
                } else if (i == Suite.size() - 1) {
                    System.out.println("对不起，没有找到您想要删除的房间！");
                }
            }
        }
    }


    private static void addRoom(Scanner user) {//添加房间方法
        int userIn = checkRoom(user);//提示用户输入房间类型并检测合法性
        System.out.println("请您输入要添加房间的具体信息");
        String detaliedsize = user.next();
        System.out.println("请您输入要添加房间的数量");
        int num = user.nextInt();
        System.out.println("请您输入要添加房间的价格");
        double price = user.nextDouble();
        System.out.println("请您输入要添加房间的编号");
        int id = user.nextInt();
        switch (userIn) {
            case 1:
                for (int i = 0; i < BigBedRoom.size(); i++) {
                    if (BigBedRoom.get(i).getId() == id) {
                        System.out.println("id已存在，请重输入一个id");
                        id = user.nextInt();
                        i = 0;
                    }
                }
            case 2:
                for (int i = 0; i < DoubleRooms.size(); i++) {
                    if (DoubleRooms.get(i).getId() == id) {
                        System.out.println("id已存在，请重输入一个id");
                        id = user.nextInt();
                        i = 0;
                    }
                }
            case 3:
                for (int i = 0; i < Suite.size(); i++) {
                    if (Suite.get(i).getId() == id) {
                        System.out.println("id已存在，请重输入一个id");
                        id = user.nextInt();
                        i = 0;
                    }
                }
        }
        switch (userIn) {
            case 1:
                BigBedRoom.add(new BigBedRoom(detaliedsize, num, price, id));//添加大床房对象
                break;
            case 2:
                DoubleRooms.add(new DoubleRooms(detaliedsize, num, price, id));//添加双床房对象
                break;
            case 3:
                Suite.add(new Suite(detaliedsize, num, price, id));//添加套房对象
                break;
        }
        System.out.println("房间:" + detaliedsize + "添加成功!");
    }

    public static int checkRoom(Scanner user) {//用户输入房间类型方法
        boolean flag = true;
        int sc = 0;
        while (flag) {
            System.out.println("请您输入要操作房间的类型,1-大床，2-双床,3-套房");
            int userIn = user.nextInt();
            if (userIn == 1 || userIn == 2 || userIn == 3) {
                sc = userIn;
                flag = false;
            } else {
                System.out.println("您输入的类型有误！");
            }
        }
        return sc;
    }

    public static void queryRoom() {//查询打印当前系统车辆
        System.out.println("-----大床房(3天以上8折，VIP2天以上6折)-------");
        System.out.println("房间具体信息\t\t" + "房间价格\t\t" + "房间编号\t\t" + "房间数量");
        for (Room com : BigBedRoom) {
            System.out.println(com.getDetailedSize() + "\t\t" + com.getPrice() + "\t\t" + com.getId() + "\t\t" + com.getNumber());
        }
        System.out.println("-----双床房(3天以上8折，VIP2天以上6折)-------");
        System.out.println("房间具体信息\t\t" + "房间价格\t\t" + "房间编号");
        for (Room com : DoubleRooms) {
            System.out.println(com.getDetailedSize() + "\t\t" + com.getPrice() + "\t\t" + com.getId() + "\t\t" + com.getNumber());
        }
        System.out.println("------套房(3天以上8折，VIP2天以上6折)--------");
        System.out.println("房间具体信息\t\t" + "房间价格\t\t" + "房间编号");
        for (Room com : Suite) {
            System.out.println(com.getDetailedSize() + "\t\t" + com.getPrice() + "\t\t" + com.getId() + "\t\t" + com.getNumber());
        }

    }

    public static void queryOrder(List<Room> customerBigBedRoom, List<Room> customerDoubleRooms,
                                  List<Room> customerSuite) {//查询当前用户订单
        System.out.println("*******订单信息*******");
        System.out.println("房间具体信息\t\t" + "房间价格\t\t" + "房间编号\t\t" + "预订天数\t\t" + "预订金额");
        for (Room room : customerBigBedRoom) {//遍历输出大床房
            System.out.println(room.getDetailedSize() + "\t\t\t" + room.getPrice() + "\t\t" + room.getId() + "\t\t"
                    + room.getDays() + "\t\t" + room.getPayMoney());
        }
        for (Room room : customerDoubleRooms) {//遍历输出双床房
            System.out.println(room.getDetailedSize() + "\t\t\t" + room.getPrice() + "\t\t" + room.getId() + "\t\t"
                    + room.getDays() + "\t\t" + room.getPayMoney());
        }
        for (Room room : customerSuite) {//遍历输出套房
            System.out.println(room.getDetailedSize() + "\t\t\t" + room.getPrice() + "\t\t" + room.getId() + "\t\t"
                    + room.getDays() + "\t\t" + room.getPayMoney());
        }
        /*
         * 删除订单
         * */
        System.out.println("请输入您想要进行的操作： 1-取消订单，2-返回页面");
        Scanner user = new Scanner(System.in);
        int temp = user.nextInt();
        int mark = 0;
        while (mark == 0) {
            if (temp == 1) {
                mark = 1;
                System.out.println("请输入预订房间的类型1-大床房，2-双床房，3-套房：");
                int size = user.nextInt();
                System.out.println("请输入预订房间的编号：");
                int id = user.nextInt();
                if (size == 1) {
                    for (int i = 0; i < customerBigBedRoom.size(); i++) {
                        if (customerBigBedRoom.get(i).getId() == id) {
                            customerBigBedRoom.remove(i);
                            System.out.println("房间：" + customerBigBedRoom.get(i).getDetailedSize() + "成功删除！");
                        }
                    }
                } else if (size == 2) {
                    for (int i = 0; i < customerDoubleRooms.size(); i++) {
                        if (customerDoubleRooms.get(i).getId() == id) {
                            customerDoubleRooms.remove(i);
                            System.out.println("房间：" + customerDoubleRooms.get(i).getDetailedSize() + "成功删除！");
                        }
                    }
                } else if (size == 3) {
                    for (int i = 0; i < customerSuite.size(); i++) {
                        if (customerSuite.get(i).getId() == id) {
                            customerSuite.remove(i);
                            System.out.println("房间：" + customerSuite.get(i).getDetailedSize() + "成功删除！");
                        }
                    }
                }
            } else if (temp == 2) {
                mark = 1;
            } else {
                System.out.println("请输入正确的指令：");
                temp = user.nextInt();
            }
        }
    }

    public static void removeBigBedRoom(Room BigBed) {//在管理系统中移除用户创建的大床房对象，代表房间已被预订
        for (int i = 0; i < BigBedRoom.size(); i++) {
            if (BigBed.getId() == BigBedRoom.get(i).getId()) {
                BigBedRoom.remove(i);
                return;
            }
        }
    }

    public static void removeDoubleRooms(Room DoubleBed) {//在管理系统中移除用户创建的双床房对象，代表房间已被预订
        for (int i = 0; i < DoubleRooms.size(); i++) {
            if (DoubleBed.getId() == DoubleRooms.get(i).getId()) {
                DoubleRooms.remove(i);
                return;
            }
        }
    }

    public static void removeSuite(Room set) {//在管理系统中移除用户创建的套房对象，代表房间已被预订
        for (int i = 0; i < Suite.size(); i++) {
            if (set.getId() == Suite.get(i).getId()) {
                Suite.remove(i);
                return;
            }
        }
    }
}
