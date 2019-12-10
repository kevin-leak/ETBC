 易书阁
======

 一款基于短视频的书籍交易app

[toc]



需求分析
----

直播：用来做书籍的发布和宣讲

短视频：书籍的段篇章的介绍，这里可以叫编辑，绘画出一些书籍来进行宣传

两者之间的差别就是时间，可以很好的缩短介绍。

书籍类别的展示

个人页面的展示：基础的信息，购物车，支付方式



架构与实现
----------

分为四个包

app，factory，data，common

- app充当View的作用
- factory充当presenter的作用，连接data以及model的作用
- data充当model的作用，数据本地化以及数据的网络请求
- common，建立一些可以复用的类



### 页面实现

#### 页面制作

需要考虑的问题

前端页面的制作，主要是四方面：

- 页面素材：页面样式，页面色彩，页面图标
- Theme与Style：抽象，沉浸式，输入法模式
- 基类View以及页面碎片化组合：（Fragment，ViewStub，include，merge)，BaseFragment，BaseActivity
- 自定义View

// todo 说清楚，碎片化了哪些界面

#### 主界面展示

<img src="./meterial/page/main_page.png"  />

#### 知识储备

// TODO 

- [Butterknife原理]() 
- [圆角与圆形的原理以及库]() 
- [Fragment切换封装]() 
- [Android工具类集合：Bar,Sceren]() 
- [RecycleView原理与封装]() 
- [下拉水文库的原理]()
- [drawable：画边框, 阴影, 圆角]()



### 基类铺垫

#### 基类架构图

<img src="./meterial/images/base_struct.png" />

BaseActivity、BaseFragment、ToolbarActivity都是抽象类

定义了统一的方法名，用来规定每个view会经过的操作

Contract定义了标准基础的View，Presenter，RecycleView的协议

DataSource：定义了数据 加载的阶段 统一接口

```java
public interface DataSource  {
    interface Callback<T> extends SucceedCallback<T>, FailedCallback {}
    // 获取成功
    interface SucceedCallback<T> { void onDataLoaded(T t);}
    // 获取失败
    interface FailedCallback { void onDataNotAvailable(@StringRes int strRes);}
    // 数据销毁
    void dispose();
}
```

DbDataSource：定义从数据库中加载数据

```java
public interface DbDataSource<Data> extends DataSource {
    void load(SucceedCallback<List<Data>> callback);
}
```

Dphelper

```java
interface DataChangeListener<Data extends BaseModel> {
    void onDataSave(Data... list);
    void onDataDelete(Data... list);
}
```

- 单例模式
- 

BaseDbRepository

- 在DbHelp中注册，当前的仓库对象
- 建立数据添加，与插入，清除的具体动作，同时
- 



#### Contract

定义View和Presenter最基础需要做什么，形成协议的层级化，方便后面扩展

一般对于用户来说，就是：操作，结果，失败

```java
public interface BaseContract {
    interface View<T extends Presenter> {
        void showError(@StringRes int error);
        void setPresenter(T present);
        void showDialog();
    }
    interface Presenter {
        void start();
        void destroy();
    }
    // 常见的View，也需要定义在基础的协议中
    interface RecyclerView<T extends Presenter, ViewMode> extends View<T> {
        RecyclerAdapter<ViewMode> getRecyclerAdapter();
        void onAdapterDataChanged();
    }
}
```

view需要做的就是，展示进度，展示错误，展示成功(继承自定义)

```java
interface View<T extends Presenter> {
    void showError(@StringRes int error);
    void setPresenter(T present);
    void showDialog();
}
```

presenter需要做的就是，初始化，执行操作(自定义，可以没有)，操作收尾

```java
interface Presenter {
    void start();
    void destroy();
}
```

View和Presenter双向绑定，View是java VM创建，

则定义setPresenter方法，Presenter 需要在创建实例的时候传入



#### BasePresenter

利用泛型约束BasePresenter操控的View，同时实现基本的Presenter

```java
public class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter {
    private T mView;
    public BasePresenter(T view) {
        setView(view);
    }
    public void setView(T view){
        this.mView = view;
        this.mView.setPresenter(this);
    }
    public T getView(){
        return this.mView;
    }
    @Override
    public void start() {
        T view = mView;
        if (view != null) {
            view.showDialog();
        }
    }
    @Override
    public void destroy() {}
}
```

#### BaseView

- BaseActivity、BaseFragment、ToolbarActivity：基类View

- BasePresenterActivity、BasePresenterFragment、PresentToolActivity：基类Presenter 结合 的View

  主要是做了一个简单抽象方法，要求子类中绑定Presenter的方式

  ```java
  @Override
  protected void initBefore() {
      super.initBefore();
      presenter = initPresent();
  }
  protected abstract Presenter initPresent();
  @Override
  public void setPresenter(Presenter presenter) {
      this.presenter = presenter;
  }
  ```

  

### 逻辑实现

#### 基础实现

##### 多线程/IO

建立一个以大小为4的线程池。

```java
public class DataKit {
    private final ExecutorService executor;
    private static DataKit dataKit;

    public DataKit() { executor = Executors.newFixedThreadPool(4); }
    public static void runOnAsync(Runnable runnable){
        DataKit.getInstance().executor.execute(runnable);
    }
    static {
        dataKit = new DataKit();
    }
    static DataKit getInstance(){
        return dataKit;
    }
}
```



##### 本地数据

建立图片缓存位置，建立本地数据库，建立观察者模型数据通知。



##### 网络数据



#### 用户信息系统

用户信息分两种，一种是forker，一种是自身

登入判断流程，用户信息存储流程，用户信息展示与修改流程。

UserAccount

```

```



#### 书籍购买系统



#### 即时通信系统



#### 视频引流系统



后台搭建与测试
--------------