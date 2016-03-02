package liyy.exercise

import liyy.exercise.controller.CashRegister
import liyy.exercise.controller.Strategy

/*
def text = '买${m}赠${n}'
def binding = [m:2,n:1,s:10]
def engine = new groovy.text.SimpleTemplateEngine()
def template = engine.createTemplate(text).make(binding)

println template.toString()
*/
/*
def text = '''\
***<没钱赚商店>购物清单***
<% list.each{out.println it} %>\
----------------------
总计：${total}(元)
**********************\
'''
def binding = [
        total:25.00,
        list:["名称：可口可乐，数量：3瓶，单价：3.00(元)，小计：9.00(元)",
        "名称：羽毛球，数量：5个，单价：1.00(元)，小计：5.00(元)",
        "名称：苹果，数量：2斤，单价：5.50(元)，小计：11.00(元)"]
]
def template = new groovy.text.StreamingTemplateEngine().createTemplate(text)
String response = template.make(binding)
println response*/
/*
int i=4,j=1
def text = "${i-j}"
println text*/
/*
CocaCola cocaCola = new CocaCola(id:"001",name:"可口可乐")
println cocaCola.id*/

/*CocaCola cocaCola = new CocaCola()
println cocaCola.id
println cocaCola.name*/
/*
String id = "ITEM000003"
String[] arr = id.split("-")
if(arr.size() > 1){
    println arr[0]
    println arr[1]
}else{
    println arr[0]
}*/
/*
String code = ""
assert code : "商品条形码不允许为空"*/
//Map promotion = [(AvilablePromotionEnum._95Discount):["ITEM000001","ITEM000002","ITEM000003"],(AvilablePromotionEnum.threeForTwo):["ITEM000001", "ITEM000002"]]
/*
LinkedHashMap promotion = [(AvilablePromotionEnum.threeForTwo):["ITEM000001", "ITEM000002"],(AvilablePromotionEnum._95Discount):["ITEM000001","ITEM000002","ITEM000003"]]

if(promotion?.size() == 2){
    promotion = promotion.sort {a,b ->
        b.key.ordinal() <=> a.key.ordinal()
    }
    def temp
   promotion.eachWithIndex{entry, int i ->
       if(i == 0){
           temp = entry
           promotion << temp
       }else{
           promotion << [(entry.key):(entry.value - temp.value)]
       }
   }
}
promotion.each {key,value->
    println "${key}:${value}"
}
*/
/*
Strategy.both.promotion.each {
    println it
}*/

Strategy.values().each {
    println it.descrption
    println CashRegister.getMessage(it)
    println "==============="
}
