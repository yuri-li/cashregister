package liyy.exercise.model

import groovy.transform.ToString

/**
 * Created by Yuri on 2016/3/2.<br/>
 * 针对当前环境抽象出来的model<br/>
 * 为了便于演示，每种产品，都写死id,name,quantifier,price
 */
@ToString
class Product {
   String id //条形码。如果是'ITEM000003-2'形式的条形码，使用“-”拆分表达式，前面截取的内容是id，后面的内容是数量number
   String name //商品名称
   String quantifier //商品的单位量词。瓶,个,斤
   Double price //单价
   Integer number //数量
   Integer realNumber //赠送活动的产品，需要在总数量中去掉赠送的产品的数量，才去计算商品总价
   Integer freeNumber //赠送产品的数量
   Double total //总价
   double saveMoney //节省的钱
   String shoppingListMessage //购物清单
   String freeMessage //买二赠一，赠送物品的清单
   PromotionEnum promotion //享受的优惠活动
   //预设值各种商品的id<-->参数
   private static final Map products = ["ITEM000001":["id":"ITEM000001","name":"可口可乐","quantifier":"瓶","price":3.00],
                           "ITEM000002":["id":"ITEM000002","name":"羽毛球","quantifier":"个","price":1.00],
                           "ITEM000003":["id":"ITEM000003","name":"苹果","quantifier":"斤","price":5.50]]

   private Product(){}
   /**
    * 根据条形码，获得产品编号、数量
    * @param code
    */
   static Product getProduct(String code){
      assert code : "商品条形码不允许为空"
      String tempId
      int tempNumber
      String[] arr = code.split("-")
      if(arr.size() > 1){
         tempId = arr[0]
         tempNumber = Integer.parseInt(arr[1])
      }else{
         tempId = arr[0]
         tempNumber = 1
      }
      assert tempId : "截取ID失败，请检查输入的编码格式：${code}"

      Product product = products.get(tempId) as Product
      product.number = tempNumber
      return product
   }
   /**
    * 汇总完商品后，补全小计、节省金额等
    */
   void rebuild(){
      Map params = promotion?.params
      params?.each {
         String key = it.key
         def binding = ["product":this]
         def text = new groovy.text.SimpleTemplateEngine().createTemplate(it.value).make(binding).toString()
         if("realNumber" == key || "freeNumber" == key || "total" == key || "saveMoney" == key){
            this."${key}" = Eval.me(text)
         }else{
            this."${key}" = text
         }
      }
   }
}
