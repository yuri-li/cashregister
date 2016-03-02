package liyy.exercise.controller

import liyy.exercise.model.Product

/**
 * Created by Yuri on 2016/3/2.
 */
class CashRegister {
    static String getMessage(Strategy strategy){
        String text = '''\
***<没钱赚商店>购物清单***
${shoppingListMessage}${freeMessage}
总计：${total}(元)${saveMoneyMessage}
**********************
'''
        //补全产品的数量、金额等数据
        List<Product> products = buildProducts(strategy)
        double total = 0 //总计
        double saveMoney = 0 //节省
        String shoppingListMessage = ""
        String freeMessage = ""
        products.each {
            total += it.total
            if(shoppingListMessage){
                shoppingListMessage += "\n"
            }
            shoppingListMessage += "${it.shoppingListMessage}"

            if(it.saveMoney){
                saveMoney += it.saveMoney
            }
            if(it.freeNumber){
                if(freeMessage){
                    freeMessage += "\n"
                }
                freeMessage += "${it.freeMessage}"
            }
        }
        freeMessage = buildFreeMessage(freeMessage)
        String saveMoneyMessage = buildSaveMoneyMessage(saveMoney)
        def binding = ["shoppingListMessage":shoppingListMessage,"freeMessage":freeMessage,"total":total,"saveMoneyMessage":saveMoneyMessage]
        new groovy.text.SimpleTemplateEngine().createTemplate(text).make(binding).toString()
    }

    static String buildFreeMessage(String freeMessage) {
        String text = '''\
<% if(freeMessage){out.print("""
----------------------
买二赠一商品：
${freeMessage}""")} %>
----------------------\
'''
        new groovy.text.SimpleTemplateEngine().createTemplate(text).make(["freeMessage":freeMessage]).toString()
    }

    static String buildSaveMoneyMessage(double saveMoney) {
        String text = '''\
<% if(saveMoney){out.print("""
节省：${saveMoney}(元)""")} %>\
'''
        new groovy.text.SimpleTemplateEngine().createTemplate(text).make(["saveMoney":saveMoney]).toString()
    }

    private static List<Product> buildProducts(Strategy strategy) {
        Map<String,Product> map = [:]
        Map promotion = strategy.promotion
        List<Product> list = []
        strategy.shoppingList?.each {
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
            if(promotion){
                for(key in promotion.keySet()){
                    if(promotion.get(key).contains(tempId)){
                        tempProduct.promotion = key
                        break
                    }
                }
            }
            tempProduct.rebuildProduct()
            map << [(tempId):tempProduct]
        }
        return map.values().asList()
    }
}
