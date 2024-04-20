package com.example.medicalservice.tools;


import java.math.BigDecimal;

public class Count {

    /**
     * 计算两个值的减法运算
     * @param arg1
     * @param arg2
     * @return
     */
    public static double sub(double arg1,double arg2) {
        String arg11 = arg1+"";
        String arg22 = arg2+"";
        int r1 = 0;
        int r2 = 0;
        int m = 0;
        int n = 0;
        try{
            r1=arg11.split("\\.")[1].length();
        }catch(Exception e){
            r1=0;
        }
        try{
            r2=arg22.split("\\.")[1].length();
        }catch(Exception e){
            r2=0;
        }

        m = (int) Math.pow(10, Math.max(r1, r2));
        //last modify by deeka
        //动态控制精度长度
        n = (r1 >= r2) ? r1 : r2;
        double result = ((arg1 * m - arg2 * m) / m);

        //BigDecimal.ROUND_HALF_UP表示四舍五入，BigDecimal.ROUND_HALF_DOWN也是五舍六入，BigDecimal.ROUND_UP表示进位处理（就是直接加1），BigDecimal.ROUND_DOWN表示直接去掉尾数。
        BigDecimal b = new BigDecimal(result);
        result = b.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }

    /**
     * 计算两个值的加法运算
     * @param arg1
     * @param arg2
     * @return
     */
    public static double add(double arg1,double arg2) {
        String arg11 = arg1+"";
        String arg22 = arg2+"";
        int r1 = 0;
        int r2 = 0;
        int m = 0;
        try{
            r1=arg11.split("\\.")[1].length();
        }catch(Exception e){
            r1=0;
        }
        try{
            r2=arg22.split("\\.")[1].length();
        }catch(Exception e){
            r2=0;
        }
        m=(int) Math.pow(10, Math.max(r1,r2));

        return (arg1 * m + arg2 * m)/m;
    }

    /**
     * 计算两个值的乘法运算
     * @param arg1
     * @param arg2
     * @return
     */
    public static double mul(double arg1,double arg2) {
        String arg11 = arg1+"";
        String arg22 = arg2+"";
        int m = 0;
        try{
            m+=arg11.split("\\.")[1].length();
        }catch(Exception e){
            System.out.println("计算出错");
        }
        try{
            m+=arg22.split("\\.")[1].length();
        }catch(Exception e){
            System.out.println("计算出错");
        }
        return Integer.parseInt(arg11.replace(".",""))* Integer.parseInt(arg22.replace(".",""))/ Math.pow(10,m);
    }


    /**
     * 计算两个值的除法运算
     * @param arg1
     * @param arg2
     * @return
     */
    public static double division(double arg1,double arg2) {
        String arg11 = arg1+"";
        String arg22 = arg2+"";
        int t1 = 0;
        int t2 = 0;
        int r1 = 0;
        int r2 = 0;
        try{
            t1=arg11.split("\\.")[1].length();
        }catch(Exception e){
            System.out.println("计算出错");
        }
        try{
            t2=arg22.split("\\.")[1].length();
        }catch(Exception e){
            System.out.println("计算出错");
        }
        r1= Integer.parseInt(arg11.replace(".",""));
        r2= Integer.parseInt(arg22.replace(".",""));
        double result = ((float)r1/r2)*(Math.pow(10,t2-t1));
        //BigDecimal.ROUND_HALF_UP表示四舍五入，BigDecimal.ROUND_HALF_DOWN也是五舍六入，BigDecimal.ROUND_UP表示进位处理（就是直接加1），BigDecimal.ROUND_DOWN表示直接去掉尾数。
        BigDecimal b = new BigDecimal(result);
        result = b.setScale(t1 + t2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }


}
