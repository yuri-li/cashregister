package liyy.exercise

import liyy.exercise.controller.CashRegister
import liyy.exercise.controller.Strategy

/**
 * Created by Yuri on 2016/3/2.
 * 打印测试结果到控制台，或打开最后一行的注释，输出到文件中
 */
StringBuffer buffer = new StringBuffer()
CashRegister cash = new CashRegister()
Strategy.values().each {
    buffer << it.description << "\n"  << cash.getMessage(it)
}
println buffer.toString()
//new File("小票.txt") << buffer.toString()