前后端交换文档
==============

[toc]

请求接口
--------

### 统一设定

回复接口：RspModel

```json
{
    "status":1,
    "date":"yyyy-MM-dd'T'HH:mm:ss",
    "message":"ok",
    "result":[]
}
```

请求基础连接

基础连接：https://BASE_URL/

BASE_URL：指的是服务器规定的前缀

ID统一利用：UUID.randomUUID().toString()生成



### 文件上传

```http
https://BASE_URL/file/
```

返回数据：RspModel\<ETCBFile> ，为统一回复接口，包裹着用户数据

```json
{
	"id": "",
	"path": "",
	"createAt": "2020-01-05T18:06:38"
}
```



### 用户系统

基础连接：https://BASE_URL/account/

#### 登入请求

URL

```http
https://BASE_URL/account/login/
```

POST请求参数

```json
{
	"code": "lkkzbl123888",
	"flag": false,
	"password": "18870742138"
}
```

flag关键字

- true：code代表使用验证码登入
- false：code代表电话号码登入

验证码发送请求，GET请求：https://BASE_URL/account/codeRsp/{phone}/

根据路径请求：phone为用户的电话号码，用户后台发送短信到客户端



#### 注册请求

URL

```http
https://BASE_URL/account/register/
```

POST请求参数

```json
{
	"avatarPath": "https://club.crabglory.www.etcb/cache/avatar/9193918.jpg",
	"name": "kevin",
	"sex":0,
	"password": "lkk23888",
	"phone": "18870742138"
}
```

sex：0代表男1代表



#### 登入/注册回送数据

RspModel\<AccountRspModel> ，为统一回复接口，包裹着用户数据

```json
"user": {
    "id": "",
    "name": "",
    "phone": "",
    "avatar": "",
    "address": "",
    "sex": 0,
    "alias": "",
    "follows": 0,
    "following": 0,
    "isFollow": false,
    "favorite": 0.0,
    "modifyAt": "2020-01-05T17:37:28"
},
"account": "",
"isBind": false
}
```

- account：用于后面支持多账户登入

- isBind：用户绑定推送

- isFollow：表示放回的用户数据，与请求用户数据的关系

- 统一回复接口：message为token值

  ```json
  {
      "status":1,
      "date":"yyyy-MM-dd'T'HH:mm:ss",
      "message":"token",
      "result":AccountRspModel
  }
  ```

  

#### 用户信息修改

URL

```http
https://BASE_URL/account/modify/
```

POST请求参数

```json
{
	"userId": "",
	"code": "",
	"type": 1
}
```

各种用户信息修改，通过type来控制

```java
public static final int VALUE_AVATAR = 0;	// 图片
public static final int VALUE_NAME = 1;		// 名字
public static final int VALUE_SEX = 2;		// 性别
public static final int VALUE_ADDRESS = 3;	// 用户地址
```

#### 用户退出

```http
https://BASE_URL/account/out/{id}
```



### 书籍/商品系统

#### 书籍/商品拉取

##### 单个拉取书籍

```http
https://BASE_URL/book/pullByID/{bookPullId}/
```

返回书籍信息

```json
{
    "id":"",
    "author":"",
    "image":"",
    "video":"",
    "name":"",
    "description":"",
    "price": 0.0,
    "type": 0,
    "count": 0,
    "sales": 0,
    "upper": {
        "id": "",
        "name": "",
        "phone": "",
        "avatar": "",
        "address": "",
        "sex": 0,
        "alias": "",
        "follows": 0,
        "following": 0,
        "isFollow": false,
        "favorite": 0.0,
        "modifyAt": "2020-01-05T17:37:28"
    },
    "upTime":"",
    "modifyAt":""
}
```



##### 批量拉取

###### 请求

商品拉取URL：https://BASE_URL/goods/pullGoods/

书籍拉取URL：https://BASE_URL/book/pullBook/

PSOT请求参数

```json
{
    "text":"",
    "type":0,
    "isMore":false
}
```

isMore指代是否添加，type的指代

```json
// 实际书本分类
public static final int TYPE_EDUCATION = 0;
public static final int TYPE_ECONOMIC = 1;
public static final int TYPE_SOCIETY = 2;
public static final int TYPE_SCIENCE = 3;

// 前端页面展示分类
public static final int TYPE_RANDOM = 4;
public static final int TYPE_DAILY = 5;
public static final int TYPE_MY_BUY = 6;    // 自己购买的产品，购物车的产品不是存在云端，放在mine goods
public static final int TYPE_MY_UP = 7;     // 我自己上传的放在 display中
public static final int TYPE_SEARCH = 8;
/*
* 以上8中类型都会作为type的类型传入后台，进行数据加载
* TYPE_MY_BUY：text为用户的ID
* TYPE_MY_UP：text为用户的ID
* TYPE_SEARCH：text为搜索的数据
* */
```

###### 批量数据返回

批量book

```json
[{
    "id":"",
    "author":"",
    "image":"",
    "video":"",
    "name":"",
    "description":"",
    "price": 0.0,
    "type": 0,
    "count": 0,
    "sales": 0,
    "upper": {
        "id": "",
        "name": "",
        "phone": "",
        "avatar": "",
        "address": "",
        "sex": 0,
        "alias": "",
        "follows": 0,
        "following": 0,
        "isFollow": false,
        "favorite": 0.0,
        "modifyAt": "2020-01-05T17:37:28"
    },
    "upTime":"",
    "modifyAt":""
},]
```

批量goods

```json
[{
    "count": 0,
    "state": false,
    "book": {
        "id":"",
        "author":"",
        "image":"",
        "video":"",
        "name":"",
        "description":"",
        "price": 0.0,
        "type": 0,
        "count": 0,
        "sales": 0,
        "upper": {
            "id": "",
            "name": "",
            "phone": "",
            "avatar": "",
            "address": "",
            "sex": 0,
            "alias": "",
            "follows": 0,
            "following": 0,
            "isFollow": false,
            "favorite": 0.0,
            "modifyAt": "2020-01-05T17:37:28"
        },
        "upTime":"",
        "modifyAt":""
    },
    "createAt": "2020-01-05T18:44:19"
}]
```

state：代表goods书籍是当前用户所有



#### 书籍修改与上传

都通过一个接口

```http
https://BASE_URL/book/upBook/
```

POST请求参数

```json
{
    "id":"",
    "author":"",
    "image":"",
    "video":"",
    "name":"",
    "description":"",
    "price": 0.0,
    "type": 0,
    "count": 0,
    "sales": 0,
    "upper": {
        "id": "",
        "name": "",
        "phone": "",
        "avatar": "",
        "address": "",
        "sex": 0,
        "alias": "",
        "follows": 0,
        "following": 0,
        "isFollow": false,
        "favorite": 0.0,
        "modifyAt": "2020-01-05T17:37:28"
    },
    "upTime":"",
    "modifyAt":""
}
```



#### 书籍/商品删除

书籍的删除只支持自己的单个删除

GET请求

```http
https://BASE_URL/book/delete/{bookDeleteId}/
```

商品的批量删除

```http
https://BASE_URL/goods/delete/
```

POST求情参数，是一个list装载goods的id

```json
["752ba496-57fa-4c83-b044-ca3ddde40490"]
```



#### 支付系统

```http
https://BASE_URL/goods/pay/
```

请求参数

```json
[{
	"id": "",
	"type": 1,
	"count": 0,
	"consumer": ""
}]
```

type指代类型

```java
public static final int TYPE_GOODS = 0;
// 用来扩展，以后不是书籍的情况
```



### 音视频系统

#### 评论子系统

#### 音视频上传



### 聊天系统

#### 文字/表情包/图片

#### 音视频通话

#### 商品信息传输









