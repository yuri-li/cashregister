package liyy.exercise.controller

import liyy.exercise.model.Product
import liyy.exercise.model.PromotionEnum

/**
 * Created by Yuri on 2016/3/2.
 * 收银机
 */
class CashRegister {
    String getMessage(Strategy strategy){
        List<Product> shoppingList = buildShoppingList(strategy)
        List<String> freeMessageList = []
        shoppingList.each {
            if (it.freeMessage) {
                freeMessageList << it.freeMessage
            }
        }
        def binding = ["shoppingList":shoppingList,"freeMessageList":freeMessageList]
        new groovy.text.SimpleTemplateEngine().createTemplate(text).make(binding).toString()
    }
/**
 * 注：打折商品，需要输出节省的金额。所以，每个商品的具体信息，与活动有关
 */
    private static final String text = '''\
***<没钱赚商店>购物清单***
<%
    if(shoppingList){
        shoppingList.each{
            out.println(it.shoppingListMessage)
        }
        out.println "----------------------"
    }
%>\
<%
    if(freeMessageList){
        out.println "买二赠一商品："
        freeMessageList.each{
            out.println(it)
        }
        out.println "----------------------"
    }
%>\
总计：${shoppingList.sum{it.total}}(元)
<%
    double saveMoney = shoppingList.sum{it.saveMoney}
    if(saveMoney){
        out.println("节省：${saveMoney}(元)")
    }
%>**********************
'''
    /**
     * 根据活动详情、购物清单，填充商品的小计、节省金额等信息
     * @param strategy
     */
    private List<Product> buildShoppingList(Strategy strategy) {
        assert strategy.shoppingList : "购物清单为空，不能结算"
        List<Product> shoppingList = []
        Map promotions = strategy.promotions
        List<Product> list = []
        strategy.shoppingList.each {
            list << Product.getProduct(it)
        }
        list.groupBy{it.id}?.sort{a,b->a.key <=> b.key}?.each {
            List<Product> tempList = it.value
            int number = 0
            String tempId = it.key
            tempList.each {
                number += it.number
            }
            Product tempProduct = tempList?.get(0)
            tempProduct?.number = number
            if(promotions){
                for(key in promotions.keySet()){
                    if(promotions.get(key).contains(tempId)){
                        tempProduct.promotion = key
                        break
                    }
                }
            }
            if(!tempProduct.promotion){
                tempProduct.promotion = PromotionEnum.none
            }
            tempProduct.rebuild()
            shoppingList << tempProduct
        }
        return shoppingList
    }
}
