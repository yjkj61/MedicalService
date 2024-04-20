package com.example.medicalservice.tools;

public class API {

    private static final String adminHost = "http://deepsky.yjkjnb.cn/prod-api";
    private static final String hostAuth = "http://47.116.36.196:9200";
    private static final String hostAi = "https://gpt.wswyhwmy.com:9600";

    private static final String hostTool = "http://47.116.36.196:9300";
    private static final String updateApp = "http://47.116.36.196:9889";

    private static final String hostProperty = "http://47.116.36.196:9268";

    public static final String bannerHost = "http://47.116.36.196:9302";

    private static final String hostSelf = "http://47.116.36.196:9500";
    private static final String hostSystem = "http://47.116.36.196:9201";
    private static final String hostHealth = "http://47.116.36.196:9213";

    private static final String ownerHost = "http://47.116.36.196:9212";

    private static final String hostShop = "http://47.116.36.196:9889" ;

    public static final String hostRestaurant = "http://47.116.36.196:9233";


    public static String hardwareHost = hostHealth + "/hardware/hardware/passBoxData";

    public static String addUser = hostSelf + "/sms/register";

    public static String login = hostAuth + "/custom/login";

    public static String seedMsg = hostSelf + "/sms/send";

    //获取用户信息

    public static String userInfo() {
        return ownerHost + "/owner/getInfoToken";
    }

    public static String owner(String ownerId) {
        return ownerHost + "/owner/ownerId?ownerId=" + ownerId;
    }

    public static String owner = ownerHost + "/owner";

    public static String family = ownerHost + "/member/getFamilyList";

    public static String member = ownerHost + "/member";


    //public static String areaDetail = "http://47.116.36.196:9212/area/detail";

    public static String uploadImage = hostTool + "/upload";


    //菜单
    public static String foodList() {
        return hostRestaurant + "/food/queryCanteenShelvingRFood";
    }

    public static String placeOrder = hostRestaurant + "/order/placeOrder";


    //影像报告列表
    public static String reportList = hostHealth + "/report/list";


    public static String report = hostHealth + "/report";

//影像报告删除

    public static String reportDelete(String id) {
        return hostHealth + "/report/" + id;
    }

    //硬件列表
    public static String hardwareList = hostHealth + "/hardware/list";

    //删除硬件
    public static String hardwareList(int id) {
        return hostHealth + "/hardware/" + id;
    }

    public static String hardware = hostHealth + "/hardware";

    public static String chohdlldlList = hostHealth + "/chohdlldl/list";
    public static String chohdlldl = hostHealth + "/chohdlldl";

    public static String lastPhysicalExamination(String ownerId) {
        return hostHealth + "/oxygen/lastPhysicalExamination/" + ownerId;
    }


    //血氧数据上传
    public static String oxygen = hostHealth + "/oxygen";

    //血氧数据获取
    public static String oxygenList = hostHealth + "/oxygen/list";

    //血糖上传
    public static String sugar = hostHealth + "/sugar";

    //血糖数据获取
    public static String sugarList = hostHealth + "/sugar/list";


    //胆固醇上传
    public static String cholesterol = hostHealth + "/cholesterol";

    //胆固醇数据获取
    public static String cholesterolList = hostHealth + "/cholesterol/list";

    //血压数据获取
    public static String pressureList = hostHealth + "/pressure/list";

    //血压数据上传
    public static String pressure = hostHealth + "/pressure";

    //体温数据上传
    public static String temperature = hostHealth + "/temperature";

    //体温数据获取
    public static String temperatureList = hostHealth + "/temperature/list";

    //尿酸
    public static String acidList = hostHealth + "/acid/list";
    public static String acid = hostHealth + "/acid";


    //心率(脉搏)
    public static String rate = hostHealth + "/rate";
    public static String rateList = hostHealth + "/rate/list";


    //餐饮订单
    public static String eatOrderList = hostRestaurant + "/order/orderQueryByStatus";
    //public static String eatOrderStatus = hostRestaurant + "/order/orderStatus";

    //餐厅下单
    public static String eatOrder = hostRestaurant + "/order";

    //新建订单
    public static String order = hostShop + "/order";

    //商城类目
    public static String shopTypeList = hostShop + "/typeConfiguration/listNoPage";
    public static String limitedTime = hostShop +"/goods/limitedTime";

    public static String rotationImage = hostShop +"/rotationImage/listNoPage";

    public static String active = hostShop +"/active/list";

    //商城商品列表
    public static String goodsList(String goodsName, String sortType, String type) {
        return hostShop + "/goods/androidList?goodsName=" + goodsName + "&" + sortType + "=" + type;
    }

    public static String goodsList(String goodsName, String minPrice, String maxPrice, String priceSorting) {
        return hostShop + "/goods/androidList?goodsName=" + goodsName + "&minPrice=" + minPrice + "&maxPrice=" + maxPrice + "&priceSorting=" + priceSorting;
    }

    public static String goodsDetail(String goodsId) {
        return hostShop + "/goods/" + goodsId;
    }

    //商品收藏

    public static String collect = hostShop + "/collect";

    //商品搜藏列表
    public static String collectList = hostShop + "/collect/list";

    //商品搜藏列表
    public static String collectCancel(int id) {
        return hostShop + "/collect/" + id;
    }

    ;
    //购物车
    public static String shoppingCartList = hostShop + "/shoppingCart/list";

    //购物车
    public static String shoppingCart = hostShop + "/shoppingCart";


    //购物车失效字段
    public static String shoppingCartList(String selectInvalidGoods) {
        return hostShop + "/shoppingCart/list&selectInvalidGoods=" + selectInvalidGoods;
    }

    public static String shoppingDelete(String ids) {
        return hostShop + "/shoppingCart/" + ids;
    }

    //订单查询
    public static String orderList(String status) {
        return hostShop + "/order/list?status=" + status;
    }

    public static String orderEdit = hostShop + "/order";


    //地址列表
    public static String areaList = hostShop + "/area/list";

    //餐厅地址
    public static String addressList = hostRestaurant + "/address/list";
    public static String address = hostRestaurant + "/address";

    //餐厅订单评价
    public static String orderEvaluation  = hostRestaurant + "/order/orderEvaluation";
    public static String orderRefundRefund  = hostRestaurant  +"/order/orderRefundRefund";

    public static String canteenList = hostRestaurant+ "/canteen/canteenlist";
    //地址添加
    public static String area = hostShop + "/area";

    //精选品牌
    public static String plateAndroidList = hostShop + "/plate/androidList?plateType=0";

    //板块
    public static String plateAndroidList2 = hostShop + "/plate/androidList?plateType=1";


    public static String medicinalTypeList = hostHealth + "/medicinalType/androidList";

    public static String medicinalUnitList = hostHealth + "/medicinalUnit/androidList";

    public static String medicinalRemindList(String time) {
        return hostHealth + "/medicinalRemind/androidList?beginTime=" + time;

    }

    //chatgpt
    public static String chatGpt = hostAi + "/chatgpt/chat/chat";


    //新增用药提醒
    public static String medicinalRemind = hostHealth + "/medicinalRemind";

    public static String medicinalRemindAndroidListByUserId = hostHealth + "/medicinalRemind/androidListByUserId";

    public static String medicinalRemindTimeAndroidEdit = hostHealth + "/medicinalRemindTime/androidEdit";


    public static String medicinalRemindTimeAndroidAdd = hostHealth + "/medicinalRemindTime/androidAdd";

    public static String medicinalRecodeList(String type) {
        return hostHealth + "/medicinalRecode/list?remindType=" + type;
    }

    ;

    public static String physicalRemindList = hostHealth + "/physicalRemind/list";
    public static String physicalRemind = hostHealth + "/physicalRemind";

    public static String physicalRemindTime = hostHealth + "/physicalRemindTime/androidAdd";

    public static String physicalRemindTimeAndroidEdit = hostHealth + "/physicalRemindTime/androidEdit";
    public static String lunar = hostSelf + "/self/index/lunar";

    public static String weather = hostSelf + "/self/index/weather";

    public static String homeBanner = bannerHost + "/chart/list";


    public static String recommendingGoods = hostShop + "/goods/recommendingGoods";

    public static String recordsAndroidInfo = hostHealth + "/records/androidInfo";

    public static String records = hostHealth + "/records";


    public static String physicalRemindAndroidListByUserId = hostHealth + "/physicalRemind/androidListByUserId";

    //彻底删除体检提醒 - 安卓
    public static String physicalRemindAndroidRemove(String id) {
        return hostHealth + "/physicalRemind/androidRemove/" + id;
    }

    //彻底删除用药提醒记录
    public static String medicinalRemindAndroidRemove(String id) {
        return hostHealth + "/medicinalRemind/androidRemove/" + id;
    }

    public static String queryTrack(String com, String num) {
        return hostShop + "/kuaidi/queryTrack?com=" + com + "&num=" + num;
    }

    public static String commentsFirst = hostShop + "/commentsFirst";

    public static String commentsFirstList = hostShop + "/commentsFirst/list?isShow=0&";

    public static String lastPhysicalExamination = hostHealth + "/oxygen/lastPhysicalExamination/";

    //物业接口
    public static String selectAllTypes = hostProperty + "/type/selectAllTypes";

    public static String selectItemBySTypeId = hostProperty + "/item/selectItemBySTypeId";
    public static String selectAllSecondTypesOwnerFirstTypeByFTypeId = hostProperty + "/type/selectAllSecondTypesOwnerFirstTypeByFTypeId";

    public static String selectTypeDownBySTypeIdAndFirstDownProperty = hostProperty + "/typeDown/selectTypeDownBySTypeIdAndFirstDownProperty";

    public static String selectTypeDownBySTypeIdAndSecondDownProperty = hostProperty + "/typeDown/selectTypeDownBySTypeIdAndSecondDownProperty";

    public static String selectTypeDownBySTypeIdAndThirdDownProperty = hostProperty + "/typeDown/selectTypeDownBySTypeIdAndThirdDownProperty";

    public static String selectBookMoneyByDifferentValue = hostProperty + "/moneyModel/selectBookMoneyByDifferentValue";

    public static String book = hostProperty + "/book";

    public static String submitApi = hostProperty + "/book/cost";

    public static String selectBookListByOwnerUserIdAndStatus = hostProperty + "/book/selectBookListByOwnerUserIdAndStatus";

    public static String updateBookByBookNum = hostProperty + "/book/updateBookByBookNum";

    public static String selectSysMarkerNameList = hostSystem + "/marker/selectSysMarkerNameList";


    public static String requestMsgMindList = bannerHost + "/notice/authority/list";

    public static String updateAppUIrl = updateApp + "/apkVersion/checkNewVersion";

    public static String nomeBannerUrl(String type){
        return homeBanner + type + "/authority/list";
    }


    public static String homeAdBannerUrl = bannerHost + "/advertisement/authority/list";  //广告banner


    public static String homeArticleBannerUrl = bannerHost + "/article/authority/list"; //资讯banner


    public static String homeActivityBannerUrl = bannerHost + "/activity/authority/list"; //活动banner

    public static String cateringBannerUrl = bannerHost + "/chart/authority/list"; //餐饮banner

    public static String applyActiveUrl = bannerHost + "/message/authority/add";

    public static String requestFamily = ownerHost + "/member/getAndroidFamilyList";
    public static String medicalHistory = ownerHost + "/profile/list/history";

    public static String dictList = hostSystem + "/dict/data/dictList";


    public static String addOwnerInfo =ownerHost + "/owner/addOwnerInfo";
    public static String editOwnerInfo =ownerHost + "/owner/editOwnerInfo";



}

