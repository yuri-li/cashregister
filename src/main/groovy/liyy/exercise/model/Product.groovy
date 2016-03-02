package liyy.exercise.model

import groovy.transform.ToString

/**
 * Created by Yuri on 2016/3/2.<br/>
 * 抽象产品，针对当前环境抽象出来的model<br/>
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
   Double saveMoney //节省的钱
   String shoppingListMessage //购物清单
   String freeMessage //买二赠一，赠送物品的清单
   AvilablePromotionEnum promotion //享受的优惠活动

   private Product(){}
   /**
    * 根据code，获得相应的产品
    * @param code
    */
   static Product getProduct(String code){
      assert code : "商品条形码不允许为空"
      def product
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
      //实际项目中，根据ID，从数据库中查找相应产品的默认属性
      switch(tempId){
         case "ITEM000001":
            product = new Product(id:tempId,name:"可口可乐",quantifier:"瓶",price:3.00,number:tempNumber)
            break;
         case "ITEM000002":
            product = new Product(id:tempId,name:"羽毛球",quantifier:"个",price:1.00,number:tempNumber)
            break;
         case "ITEM000003":
            product = new Product(id:tempId,name:"苹果",quantifier:"斤",price:5.50,number:tempNumber)
            break;
      }
      return product
   }
   void rebuildProduct(){
      Map params = promotion?.params
      params?.each {
         String key = it.key
         def binding = ["product":this]
         def text = new groovy.text.SimpleTemplateEngine().createTemplate(it.value).make(binding).toString()
         if("realNumber" == key || "freeNumber" == key){
            this."${key}" = Eval.me(text)
         }else if("total" == key || "saveMoney" == key){
            this."${key}" = Eval.me(text)
         }else{
            this."${key}" = text
         }
      }
      if(!promotion){
         this.total = this.number * this.price
         this.shoppingListMessage = "名称：${name}，数量：${number}${quantifier}，单价：${price}(元)，小计：${total}(元)"
      }
   }
}
