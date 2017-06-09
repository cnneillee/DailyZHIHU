# DailyZHIHU

一款基于知乎日报内容的第三方应用

## API
基于第三方 API [izzyleung/ZhihuDailyPurify][0]

## Declaration
- 1. 关于内容
内容来自知乎日报，APP 基于 [izzyleung/ZhihuDailyPurify][0] 提供的第三方 API。

- 2. 关于版权
项目为练习之用，如有侵权，本人将于第一时间删除。

- 3. 关于引用
您可以随意使用本项目中的源码，烦请注明出处。

如有问题，欢迎交流！

## Done && TODO
### Done
- 0.1.1(1) --- 基础构建
- 0.2.1(2) --- 基础构建
- 0.3.1(3) --- 基础构建
- 1.0.0(4) --- 完成基本功能
- 1.1.0(5) --- 引导页、Splash功能
- 1.2.0(6) --- 意见反馈、收藏与缓存、缓存统计与清除、语言选择与切换功能、优化网络请求

1.1.0 ：
- ~~功能的 ViewPager 展示~~
- ~~添加 Splash 选项（不加载、从 多个源中自定义 加载）~~
- ~~Splash 优化~~
- ~~修复更新页面的更新信息乱码问题~~

~~引导页：~~

![贴图][1]

~~Splash设置：~~

![贴图][2]

1.2.0 ：
- ~~意见反馈功能添加（About->DailyZhihu）~~
- ~~图标的修改（Launcher、DrawerMenu的图标、MainActivity的Tabs图标）~~
- ~~语言选择与切换~~
- ~~缓存统计与清除功能（设置-网络）~~
- ~~无图模式的支持~~
- ~~收藏功能、文章缓存~~
- ~~优化网络请求，添加网络请求错误判断~~
- ~~界面显示优化~~

- ~~修改架构，使用 MVP 架构~~
- ~~网络优化，使用 Retrofit2.0 网络加载~~
- ~~引入 Dagger2.0 依赖注入框架~~
- ~~引入 GreenDao3 数据库操作框架~~

- ~~修复部分bug~~

1.3.0：
- ~~主题颜色优化~~
- ~~语言切换功能优化~~
- ~~自主化（图标、包名、提示性名称等）~~
- ~~夜间模式图标、文字适应模式变化~~

- ~~应用市场发布~~
- ~~用户信息打点~~
- ~~bugly、fabric 集成~~
- ~~引入 walle 多渠道打包~~
- ~~引入 leakcanary、blockcanary 监控内存泄漏~~

### TODO

1.4.0以后：
- 优化整个App
- 主题切换功能（设置-偏好）

## Function and UI
一款基于知乎日报内容的第三方应用.

日报开屏，每日一图，开启一日好时光.

抽拉之间，一切尽在掌握.

![贴图][3]

新热旧文，主页一应俱全。

![贴图][4]

主题日报，想看什么看什么。

![贴图][5]

栏目纵览，总有一个适合你。

![贴图][6]

治愈长夜的无聊！

![贴图][7]

爱上毒舌的快感！

![贴图][8]

畅享阅读的高潮！

![贴图][9]

[0]: https://github.com/izzyleung/ZhihuDailyPurify/wiki/%E7%9F%A5%E4%B9%8E%E6%97%A5%E6%8A%A5-API-%E5%88%86%E6%9E%90
[1]: ./screenshot/v1.2.0/group-guide.png
[2]: ./screenshot/v1.3.0/group-image-splash.png
[3]: ./screenshot/v1.3.0/group-overall-intro.png
[4]: ./screenshot/v1.2.0/group-main.png
[5]: ./screenshot/v1.2.0/group-topic.png
[6]: ./screenshot/v1.2.0/group-column.png
[7]: ./screenshot/v1.2.0/1.png
[8]: ./screenshot/v1.2.0/group-comment.png
[9]: ./screenshot/v1.2.0/2.png
