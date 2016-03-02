package liyy.exercise.controller

import liyy.exercise.model.AvilablePromotionEnum

/**
 * Created by Yuri on 2016/3/2.
 * 策略，概括示例中的4种场景，并且预先设置测试数据
 */
enum Strategy {
    haveThreeForTwo("有符合“买二赠一”优惠条件的商品",
            [(AvilablePromotionEnum.threeForTwo):["ITEM000001","ITEM000002"]],
            ["ITEM000001","ITEM000001-2","ITEM000002","ITEM000002-3","ITEM000002","ITEM000003-2"]),
    none("没有满足任何一种优惠活动的商品",null,
            ["ITEM000001-3","ITEM000002-5","ITEM000003-2"]),
    have_95Discount("有符合“95折”优惠条件的商品",[(AvilablePromotionEnum._95Discount):["ITEM000003"]],
            ["ITEM000001-3","ITEM000002-5","ITEM000003-2"]),
    both("有些商品同时满足两种优惠活动的条件",
            [(AvilablePromotionEnum.threeForTwo):["ITEM000001","ITEM000002"],
             (AvilablePromotionEnum._95Discount):["ITEM000001","ITEM000002","ITEM000003"]],
            ["ITEM000001-3","ITEM000002-6","ITEM000003-2"])

    String descrption
    Map promotion //优惠活动
    List<String> shoppingList //购物清单
    Strategy(String descrption,Map promotion,List<String> shoppingList){
        this.descrption = descrption
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
        this.promotion = promotion
        this.shoppingList = shoppingList
    }
}
