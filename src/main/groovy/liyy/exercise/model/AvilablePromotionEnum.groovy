package liyy.exercise.model

/**
 * Created by Yuri on 2016/3/2.<br/>
 * 枚举当前所有正在生效的活动，比如打折，是95折，还是98折等<br/>
 * 约定：规则的ordinal为其优先级。即：“买二赠一”的优先级>“打95折”
 */
enum AvilablePromotionEnum {
    //total:优惠后的总花费,saveMoney:节省的钱,shoppingListMessage:购物清单的信息
    _95Discount("打95折",["total":'${product.number}*${product.price}*0.95',
                                    "saveMoney":'${product.number}*${product.price}*0.05',
                                    "shoppingListMessage":'名称：${product.name}，数量：${product.number}${product.quantifier}，单价：${product.price}(元)，小计：${product.total}(元)，节省${product.saveMoney}(元)']),
    //realNumber:实际纳入计算的商品数量,freeNumber:赠送商品的数量,freeMessage:赠送商品的信息
    threeForTwo("买二赠一",["freeNumber":'(int)(${product.number}/3)',
                                    "realNumber":'${product.number-product.freeNumber}',
                                    "total":'${product.realNumber}*${product.price}',
                                    "saveMoney":'${product.freeNumber}*${product.price}',
                                    "shoppingListMessage":'名称：${product.name}，数量：${product.number}${product.quantifier}，单价：${product.price}(元)，小计：${product.total}(元)',
                                    "freeMessage":'名称：${product.name}，数量：${product.freeNumber}${product.quantifier}'])

    String promotion//属于哪一种优惠活动
    Map params //列出与促销活动相关的参数与表达式
    AvilablePromotionEnum(promotion,params){
        this.promotion = promotion
        this.params = params
    }
}
