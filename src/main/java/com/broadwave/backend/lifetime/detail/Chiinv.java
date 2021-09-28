package com.broadwave.backend.lifetime.detail;

import org.formulacompiler.runtime.internal.RuntimeDouble_v2;
//import org.formulacompiler.runtime.internal.Runtime_v2;

public class Chiinv {
    public static double chiinv(double _x, double _degFreedom){
        return RuntimeDouble_v2.fun_CHIINV(_x, _degFreedom);
    }
//    final double NORMDIST(double input1,double input2,double input3,double input4) {
//        double d;
//        if (input3 <= 0.0) {
//            Runtime_v2.fun_ERROR("#NUM! because sigma <= 0 in NORMDIST");
//            d = (double) -1;
//        } else
//            d = (input4 != 0.0
//                    ? (Math.abs((input1 - input2) / input3
//                    * 0.7071067811865476) < 0.7071067811865476
//                    ? (0.5
//                    + 0.5 * RuntimeDouble_v2.fun_ERF((input1 - input2)
//                    / input3
//                    * 0.7071067811865476))
//                    : (input1 - input2) / input3 * 0.7071067811865476 > 0.0
//                    ? 1.0 - 0.5 * (RuntimeDouble_v2.fun_ERFC
//                    (Math.abs((input1 - input2) / input3 * 0.7071067811865476)))
//                    : 0.5 * (RuntimeDouble_v2.fun_ERFC
//                    (Math.abs((input1 - input2) / input3 * 0.7071067811865476))))
//                    : (Math.exp((input1 - input2) * (input1 - input2)
//                    / (-2.0 * input3 * input3))
//                    / (2.5066282746310002 * input3)));
//        return d;
//    }
//    public static void main(String[] args){
//        Chiinv c = new Chiinv();
//        double d = c.chiinv(0.05, 2);
//        System.out.println(d);
//    }
}