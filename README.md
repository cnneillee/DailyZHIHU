# DailyZHIHU
## Project Scheduling
- 2016.03.24 upload the project initially
- 2016.04.13 beautify the StoryActivity UI(ObservableScrollView、FloatingActionButton)
  - 改变
    - 将查看评论功能移至FloatingActionButton中
    - 文章图片、标题用ObservableScrollView上部展示
  - 遗留：
    - 版权信息的textView不能随标题图片隐藏
    - 状态栏未实现成沉浸式
    - 评论的展示建议使用activity来替换目前AleartDialog
    - 代码整理
- 2016.04.16 truely finished the ui and funtion, from then on,every changes of this project will be based on this version.
  - 说明：
    功能和界面已经基本能够达到正常使用的标准了，今后的改动将会基于这一版。并且，今后的改动一定会按更改项来提交
- 2016.04.16 day/nightMode(by restarting Activity)
- 2016.04.16 topstory实现了ViewPager，夜间模式未完全实现
- 2016.04.16 用item_lv_UniversalStory整合了三种story的item界面文件，添加了下方的extra信息
- 2016.04.17 整合了story和block两类实体的adapter及实体自身
  - 用UniversalStoryListAdapter整合了hotStoryListAdapter、latestStoryListAdapter、pastStoryListAdapter
  - 用UniversalBlockGridAdapter整合了ThemeListAdapter、SectionListAdapter
  - 用UniversalStoryBean整合了Story实体（三个）中关键部分（title、id、imgs）
  - 用UniversalBlockBean整合了block实体（两个）中关键部分（title、id、imgs、description）
  - 用item_gv_universal_block整合了theme、section列表的布局文件
  - 部分section、theme无法进入（部分的url失效），仅作了粗糙处理
  - 优化了MainActivity的toolBar（实现了MainActivity的toolBar的navItem文字切换及使用ObservableScrollListView/ObservableScrollGridView实现navItem内的隐藏）
- 2016.04.17 ThemeActivirt、SectionActivity的界面及功能优化
- 2016.04.19 story多平台分享界面已经实现
- 2016.04.19 ImageStoryActivity生成图片分享界面、图片生成、简单分享功能已经实现
- 2016.04.20 多平台分享简单环境测试通过（仅做一个简单测试以熟悉环境）
- 2016.04.21 多平台分享功能已经全部完成（多平台分享+图片生成分享）
- 2016.04.23 数据库搭建实现特定功能-数据库测试OK，功能有待完善
- 2016.06.11 修复了sectionSettingActivity中item选择错位的bug


## Description
基于私有api而开发的一款获取知乎日报内容的app（Android版）

### Basic functions
- 启动界面
- 获取最新消息列表
- 获取最新消息的详细内容
- 获取过往消息列表及内容
- 展示各个消息的额外信息（点赞、评论数及评论内容【长评论+短评论】）
- 主题日报列表、日报具体文章列表及文章具体内容获取
- 热门消息列表及具体内容获取
- 栏目总览及栏目具体内容获取
- bla bla...

### Declaration
- 1、此app内容基于 [izzyleung/ZhihuDailyPurify](https://github.com/izzyleung/ZhihuDailyPurify/wiki/%E7%9F%A5%E4%B9%8E%E6%97%A5%E6%8A%A5-API-%E5%88%86%E6%9E%90) 提供的api。

如有侵害某一方的合法权益，请告知，本人将在第一时间删除

- 2、此app为本菜鸟练习之用，如有相关意见和建议，非常欢迎与本菜鸟交流

- 3、如您须使用使用本项目源码，请注明出处，谢谢

## Demo
![](https://github.com/neilleecn/DailyZHIHU/blob/master/screenshot/01.png)
![](https://github.com/neilleecn/DailyZHIHU/blob/master/screenshot/02.png)
![](https://github.com/neilleecn/DailyZHIHU/blob/master/screenshot/03.png)
![](https://github.com/neilleecn/DailyZHIHU/blob/master/screenshot/04.png)
![](https://github.com/neilleecn/DailyZHIHU/blob/master/screenshot/05.png)
![](https://github.com/neilleecn/DailyZHIHU/blob/master/screenshot/06.png)
![](https://github.com/neilleecn/DailyZHIHU/blob/master/screenshot/07.png)
![](https://github.com/neilleecn/DailyZHIHU/blob/master/screenshot/08.png)
![](https://github.com/neilleecn/DailyZHIHU/blob/master/screenshot/09.png)
![](https://github.com/neilleecn/DailyZHIHU/blob/master/screenshot/10.png)
