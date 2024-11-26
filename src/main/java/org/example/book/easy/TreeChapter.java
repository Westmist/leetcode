package org.example.book.easy;


import org.example.basic.convert.ano.Title;

/**
 * 力扣书籍 - 初级算法
 * 树章节
 *
 * @link {<a href="https://leetcode.cn/leetbook/read/top-interview-questions-easy/x28wnt/">...</a>}
 */
@Title("初级算法:树")
public class TreeChapter   {

    private static int data = 1;

    protected static void main(String[] args) {
        Thread t1 = new Thread(() -> System.out.println("aa"));

        Thread t2 = new Thread(() -> System.out.println("bb"));

        t1.start();
        t2.start();



        // -1 的补码
        // 原 1000 0001
        // 反 1111 1110
        // 补 1111 1111

        // 原 1111 1111
        // 反 1000 0000
        // 补 1000 0001

        /**
         *
         *
         *
         *
         *
         *
         *
         *
         */





        //  1000 0000 0000 0001
        //  1111 1111 1111 1110
        //  1111 1111 1111 1111

        // FFFF
        /**
         *       15 + 15 * 16^1 + 15 * 16^2 + 15 * 16^3
         *
         *       10000    //   16^4  = 2 ^ 16 -1
         *
         *
         *       1111 1111 1111 1111
         *
         *
         *      补 1111 1111 1111 1111
         *
         *         1000 0000 0000 0000
         *
         *
         *         1000 0000 0000 0001
         *
         *
         *         -（2（n-1）-1）    2^(n-1)-1
         *
         *
         *         90H
         *
         *         9 * 16^1 == 9 * 16
         *
         *         144
         *
         *
         *         128 + 16
         *
         *         1001 0000
         *
         *         1110 1111
         *
         *         1 111 0000
         *
         *         -    (  64 + 32 + 16   )
         *
         *         -112  == 2 * X
         *
         *
         *
         *
         */

















        // 两次异或   两次求补码


        // 补码的基础上符号位取反



        // 补码 、 移码  都是+0

        //






    }

//    /**
//     * @param s 原数组
//     * @link {<a href='https://leetcode.cn/leetbook/read/top-interview-questions-easy/xnhbqj/'>...</a>}
//     * 方法：相向双指针
//     * 时间复杂度：O(n)
//     */
//    @Title("反转字符串")
//    @Params(pc = {@Convert(value = "['h','e','l','l','o']", convert = ArrayConvert.class, type = char.class)})
//    @Answer(c = @Convert(value = "['o','l','l','e','h']", convert = ArrayConvert.class, type = char.class),
//            section = SectionFactory.FristParamSection.class)
//    public void reverseString(char[] s) {
//        int left = 0, right = s.length - 1;
//        while (left < right) {
//            char temp = s[left];
//            s[left] = s[right];
//            s[right] = temp;
//            left++;
//            right--;
//        }
//    }

    /**
     *
     * 核心态，用户态
     * 数据库分级模式
     * 规范化理论,关系代数和数据演算，ER图
     *
     *
     *
     *
     * cookies
     *
     *
     * 七层模型
     *
     * 物理层            高低电频 中继器  集线器
     * 数据链路层         帧   ， 交换机
     *
     * 网络层             IP IGMP      3层交换机，路由器
     * 传输层             UDP TCP
     *
     * 会话层
     * 表现层
     * 应用层
     *
     *
     *  1   -->  2
     *       1   <--  2
     *        1   -->   2
     *
     *   Telnet  FTP   SMTP               DHCP  DNS
     *
     *   UDP
     *   ICMP    ARP   IP   RARP
     *  固定，动态
     *
     *  169.254.*.*
     *  0.0.0.0
     *
     *
     *  DNS 域名转ip  ip 换 mac
     *  迭代查询和递归查询
     *  域名服务器
     *  计算机网络
     *  全球性网络因特网
     *
     *  实用性、开放性 和 先进性
     *  技术快速迭代
     *  IPv4 不够用
     *  a  2^24 -2
     *  256  8bit  1byte
     *  子网掩码
     *  5 次方
     *    10
     *   0000 0000     0000 0000      0000 00 00     0000 0000
     *  255.255.252.0
     *
     *  B，类 & 子网
     *
     *  1221  2^4-1
     *  127.0.0.1  本地环回
     *
     *  192.168.0.0
     *
     *  10.0.0.0
     *  172.16.0.0
     *  192.168.0.0
     *
     *  HTML 标签
     *
     *
     *  无线广域网
     *
     *
     *  网络接入技术
     *
     *  视频 & 图片
     *  PSTN
     *  DDN
     *  ISDN
     *  ADSL   非对称数字用户电路 上下行
     *  HFC    同轴光纤
     *  性能瓶颈    IO     磁盘io
     *
     *  面相对象, 类把对象的共性抽取出来
     *  数据库设计、边界类、
     *  ci  t   q    x
     *
     *  设计模式原则
     *  1、单一职责
     *  2、开闭原则
     *  3、迪米特法则 （最少知道原则）
     *  4、里氏替换，重写，
     *  5、接口隔离
     *  6、组合重用
     *  7、依赖抽象，面相接口编程
     *  UML 统一建模语言
     *
     *  泛化、实现、依赖、关联
     *
     *   静态图：类图，包图，构件图，部署图
     *   动态图：用例图，时序图，状态图，活动图，通信图，交互概念图
     *
     *
     *
     *   设计模式
     *
     *   创建型
     *   1、工厂
     *   2、抽象工厂
     *   3、构建
     *   4、单例
     *   5、原型
     *
     *   结构性
     *   1、适配器
     *   2、装饰器
     *   3、外观
     *   4、享元
     *   5、组合
     *   6、代理
     *   7、桥接
     *
     *   行为型
     *  1、责任链
     *  2、命令
     *  3、解释
     *  4、迭代
     *  5、策略
     *  6、观察
     *  7、中介
     *  8、状态
     *
     *
     *
     *  面相对象程序设计
     *
     *
     *  c++  & java
     *  规范写法
     *
     *
     *  分模块 高内聚 低耦合
     *
     *
     *
     *
     *   贪心
     *   分治
     *   回溯
     *   动态规划
     *
     *  cpu  程序控制，
     *
     *  寄存器
     *  算术运算，逻辑运算
     *
     *
     *
     * CPU：
     * 1、控制器      控制整个cpu的运行                IR   PC  AR  ID
     * 2、运算器      进行算术运算和逻辑运算           ALU   AC  DR  PSW
     *  外设 --> 内存 --> DR  -->
     *
     *
     *
     *  对阶码
     *
     *  小阶 对 大阶
     *
     *  对阶
     *  小阶向大阶对
     *  浮点数的计算
     *
     *
     *
     *  计算机基础
     *
     *
     *  unreal
     *  正规式
     *
     *
     *
     */


}
