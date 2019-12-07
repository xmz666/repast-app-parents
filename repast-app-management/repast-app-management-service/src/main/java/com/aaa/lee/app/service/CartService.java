package com.aaa.lee.app.service;

import com.aaa.lee.app.domain.Cart;
import com.aaa.lee.app.domain.Member;
import com.aaa.lee.app.domain.Product;
import com.aaa.lee.app.domain.SkuStock;
import com.aaa.lee.app.mapper.CartMapper;
import com.aaa.lee.app.mapper.MemberMapper;
import com.aaa.lee.app.mapper.ProductMapper;
import com.aaa.lee.app.mapper.SkuStockMapper;
import com.aaa.lee.app.utils.JSONUtil;
import com.aaa.lee.app.vo.CartInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CartService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private SkuStockMapper skuStockMapper;
    @Autowired
    private MemberMapper memberMapper;

    private Exception exception = new Exception("操作失败");
    private ScheduledExecutorService mScheduledExecutorService = Executors.newScheduledThreadPool(4);
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * @Author Li Yuzhao
     * @Description
     *
     *      data:{
     *          "cart":[{"pid":1,"quantity":10},{"pid":2,"quantity":10}],
     *          "shoppingWay":1,
     *          "token":xx,
     *          "shopId":1
     *      }
     *
     * @Param  * @param data
     * @Return java.lang.Boolean
     * @Date 2019/11/23
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean addCart(Map<String,Object> data, MemberService memberService){
        String token = JSONUtil.toJsonString(data.get("token"));
        if(null == token || "" == token){
            return false;
        }else{
            Long memberId = memberService.getMemberId(token);
            if(null == memberId){
                return false;
            }else{
                //获取member信息
                Member member = memberMapper.selectByPrimaryKey(memberId);
                try {
                    //获取购物车里商品ID商品购买数量
                    List<Map> cartList = JSONUtil.toList(JSONUtil.toJsonString(data.get("cart")), Map.class);
                    //获取点餐/外卖状态码
                    int shoppingWay = Integer.parseInt(JSONUtil.toJsonString(data.get("shoppingWay")));

                    //获取当前时间
                    Date date = new Date();
                    String format = simpleDateFormat.format(date);
                    Date formatDate = simpleDateFormat.parse(format);

                    //遍历前台传的购物车信息
                    for (int i = 0; i < cartList.size(); i++) {
                        //遍历出每一个map
                        Map<String, Object> cartMap = cartList.get(i);
                        //取出商品ID
                        Long pId = Long.parseLong(JSONUtil.toJsonString(cartMap.get("pid")));

                        //通过商品ID查询出购物车所需要的信息
                        CartInfo cartInfo = cartMapper.selectProductSkuByPid(pId);
                        //取出商品购买数量
                        Integer buyNum = Integer.parseInt(JSONUtil.toJsonString(cartMap.get("quantity")));

                        //通过商品ID查询商品详细信息
                        Product productAll = productMapper.selectByPrimaryKey(pId);
                        //new cart用来设置值 当参数 用于sql
                        Cart cart = new Cart();
                        cart.setProductId((long) pId);
                        cart.setMemberId(member.getId());
                        cart.setShoppingWay(shoppingWay);

                        //查询购物车里该用户该店铺未删除的所有数据
                        Map<String, Object> cartProduct = cartMapper.selectByMidPid(cart);

                        //new product用来设置值 当参数 用于sql
                        Product product = new Product();
                        //new skuStock用来设置值 当参数 用于sql
                        SkuStock skuStock = new SkuStock();

                        //判断购买数量是否大于0
                        if (buyNum > 0) {


                            product.setId(pId);
                            product.setStock(buyNum);
                            skuStock.setProductId(pId);
                            skuStock.setStock(buyNum);

                            //判断购物车里是否有数据
                            if (null != cartProduct) {
                                //如果购买数量大于0并且购物车有该商品 则需要把数据库的购买数量更新

                                //获取此商品的购物车ID
                                Long cartId = Long.parseLong(JSONUtil.toJsonString(cartProduct.get("id")));
                                //判断商品上下架
                                if (1 == productAll.getPublishStatus()) {


                                    cart.setId(cartId);
                                    cart.setQuantity(buyNum);
                                    cart.setModifyDate(formatDate);

                                    //判断是否为商品类型
                                    if (4 == shoppingWay) {

                                        //判断购买数量是否小于库存
                                        if (buyNum <= productAll.getStock()) {
                                            //修改购物车购买数量
                                            int updateCartByIdBuyNum = cartMapper.updateCartByIdBuyNum(cart);

                                            if (updateCartByIdBuyNum == 0) {
                                                throw exception;
                                            }else{
                                                //修改商品表库存
                                                int updateProStock = productMapper.updateProStock(product);

                                                if (updateProStock == 0) {
                                                    throw exception;
                                                }else{
                                                    //修改库存表库存
                                                    int updateSkuStock = skuStockMapper.updateSkuStock(skuStock);

                                                    if (updateSkuStock == 0) {
                                                        throw exception;
                                                    }else{
                                                        if(0 == cartMapper.selectDeleteStatus(cartId)){
                                                            //定时任务

                                                            setTime(product,skuStock);
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            throw exception;
                                        }
                                    } else {

                                        int updateCartByIdBuyNum = cartMapper.updateCartByIdBuyNum(cart);

                                        if (updateCartByIdBuyNum == 0) {
                                            throw exception;
                                        }
                                    }
                                }else {

                                    int deleteCartById = cartMapper.deleteCartById(cartId);

                                    if (deleteCartById == 0) {
                                        throw exception;
                                    }
                                }

                            } else {

                                if (1 == productAll.getPublishStatus()) {
                                    //把获取到的值存到cart实体类里
                                    cart.setProductId(cartInfo.getProductId())
                                            .setProductSkuId(cartInfo.getProductSkuId())
                                            .setMemberId(member.getId())
                                            .setShopId(cartInfo.getShopId())
                                            .setQuantity(buyNum)
                                            .setPrice(cartInfo.getPrice())
                                            .setSp1(cartInfo.getSp1())
                                            .setSp2(cartInfo.getSp2())
                                            .setSp3(cartInfo.getSp3())
                                            .setProductPic(cartInfo.getProductPic())
                                            .setProductName(cartInfo.getProductName())
                                            .setProductSubTitle(cartInfo.getProductSubTitle())
                                            .setProductSkuCode(cartInfo.getProductSkuCode())
                                            .setMemberNickname(member.getNickname())
                                            .setCreateDate(formatDate)
                                            .setModifyDate(formatDate)
                                            .setDeleteStatus(0)
                                            .setProductCategoryId(cartInfo.getProductCategoryId())
                                            .setProductBrand(cartInfo.getProductBrand())
                                            .setProductSn(cartInfo.getProductSn())
                                            .setProductAttr(null)
                                            .setShoppingWay(shoppingWay);
                                    if (4 == shoppingWay) {

                                        if (buyNum <= productAll.getStock()) {
                                            int insertReturnKey = cartMapper.insertReturnKey(cart);
                                            Long primaryKey = cart.getId();
                                            if (insertReturnKey == 0) {
                                                throw exception;
                                            }else{
                                                int updateProStock = productMapper.updateProStock(product);
                                                if (updateProStock == 0) {
                                                    throw exception;
                                                }else{
                                                    int updateSkuStock = skuStockMapper.updateSkuStock(skuStock);
                                                    if (updateSkuStock == 0) {
                                                        throw exception;
                                                    }else{
                                                        if(0 == cartMapper.selectDeleteStatus(primaryKey)){
                                                            setTime(product,skuStock);
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            throw exception;
                                        }
                                    } else {
                                        int insert = cartMapper.insert(cart);
                                    }
                                }

                            }
                        } else {
                            if (null != cartProduct) {
                                Long cartId = Long.parseLong(JSONUtil.toJsonString(cartProduct.get("id")));

                                int deleteCartById = cartMapper.deleteCartById(cartId);

                                if (deleteCartById == 0) {
                                    throw exception;
                                }
                            }
                        }
                    }
                }catch(Exception e){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return false;
                }
                return true;
            }
        }
    }

    /**
     * @Author Li Yuzhao
     * @Description
     *      第一次点进商家的时候，有可能购物车里有原来·点过的商品，所以需要先查询购物车是否有商品并展示出来
     * 	接收前台传的店铺ID，从redis中获取用户ID
     * 		根据店铺ID用户ID是否删除状态码上架状态码购物车表与商品表联查select c.*,p.code from cart c,product p where shop_id = 1 and member_id = 1 and delete_code = 1 and code = 1
     * 			返回前台购物车数据List<Cart>
     * @Param  * @param shopId
     * @Return java.util.List<com.aaa.lee.app.domain.Cart>
     * @Date 2019/11/27
     */
    public List<Cart> selectCart(Map<String,Object> data, MemberService memberService){

        String token = JSONUtil.toJsonString(data.get("token"));
        if(null == token || "" == token){
            return null;
        }else{
            Long memberId = memberService.getMemberId(token);
            if (null == memberId) {
                return null;
            } else {
                //获取data里的shoppingway状态码和shopid
                Integer shoppingWay = Integer.parseInt(JSONUtil.toJsonString(data.get("shoppingWay")));
                Long shopId = Long.parseLong(JSONUtil.toJsonString(data.get("shopId")));
                Cart cart = new Cart();
                cart.setShopId(shopId);
                cart.setMemberId(memberId);
                cart.setShoppingWay(shoppingWay);

                List<Cart> cartList = cartMapper.selectByMidSid(cart);
                return cartList;
            }
        }

    }

    public Boolean deleteCartByShopId(Map<String,Object> data, MemberService memberService){
        String token = JSONUtil.toJsonString(data.get("token"));
        if(null == token || "" == token){
            return false;
        }else{
            Long memberId = memberService.getMemberId(token);
                if (null == memberId) {
                    return false;
                } else {
                    //获取data里的shoppingway状态码和shopid
                    Integer shoppingWay = Integer.parseInt(JSONUtil.toJsonString(data.get("shoppingWay")));
                    Long shopId = Long.parseLong(JSONUtil.toJsonString(data.get("shopId")));
                    Cart cart = new Cart();
                    cart.setShopId(shopId);
                    cart.setMemberId(memberId);
                    cart.setShoppingWay(shoppingWay);
                    int deleteCartByShopId = cartMapper.deleteCartByShopId(cart);
                    if (deleteCartByShopId == 0) {
                        return false;
                    }
                    return true;
                }
            }
    }

    @Transactional(rollbackFor = Exception.class)
    public void setTime(Product product, SkuStock skuStock){
        mScheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                try{
                    int updateBackProStock = productMapper.updateBackProStock(product);
                    if(updateBackProStock<0){
                        throw exception;
                    }else{
                        int updateBackSkuStock = skuStockMapper.updateBackSkuStock(skuStock);
                        if(updateBackSkuStock<0){
                            throw exception;
                        }
                    }
                }catch (Exception e){
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
            }
        },5, TimeUnit.MINUTES);
    }

}
