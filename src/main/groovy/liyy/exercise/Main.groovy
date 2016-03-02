package liyy.exercise

import liyy.exercise.controller.CashRegister
import liyy.exercise.controller.Strategy

/**
 * Created by Yuri on 2016/3/2.
 * 打印测试结果到控制台，或打开最后一行的注释，输出到文件中
 */
StringBuffer buffer = new StringBuffer()
Strategy.values().each {
    buffer << it.descrption << "\n"  << CashRegister.getMessage(it) << "====================分割线============================\n"
}
println buffer.toString()
new File("cashregister.txt") << buffer.toString()