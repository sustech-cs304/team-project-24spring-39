# Design-39 Report

## 1. Architecture Design

![architecture_design](C:\Users\Simon\AppData\Roaming\Typora\typora-user-images\architecture_design.png)

1. 系统主要分为5个component
   1. 选课：提供积分选课、课程推荐服务
   2. 评教系统：提供课程评价
   3. 课程论坛：发布帖子分享学习心得，上传文件分享资料、帖子可点赞，评论，收藏
   4. 自习室预约系统：提供预约自习室服务，可见已被预约的时段
   5. 消息提醒：汇总以上系统的消息提醒，支持自定义消息提醒（待办）
2. 系统交互：
   1. 选课系统中将展示课程评价情况页(包括课程总分、具体评价等)
   2. 选课系统根据评教系统对课程的评分进行推荐
   3. 评教系统和课程论坛从选课系统中获取课程详情进行展示
   4. 以下场景将会进行消息提醒：
      1. 选课系统中选课时间即将截止、选课结束后哪些课掉了
      2. 评教系统中课程结课提醒评教
      3. 论坛系统中帖子收获点赞、评论、收藏或评论被回复
      4. 预约系统中临近自习室预约时间提醒用户
      5. 自定义提醒（待办）

## UI Design

1. 页面布局设计：

   ![UI-布局](C:\Users\Simon\AppData\Roaming\Typora\typora-user-images\UI-布局.png)

2. 主体部分-首页：

   ![UI-首页](C:\Users\Simon\AppData\Roaming\Typora\typora-user-images\UI-首页.png)

3. 主体部分-选课系统：

   ![UI-选课系统](C:\Users\Simon\AppData\Roaming\Typora\typora-user-images\UI-选课系统.png)

4. 主体部分-评教系统：

   ![UI-评教系统](C:\Users\Simon\AppData\Roaming\Typora\typora-user-images\UI-评教系统.png)

5. 主体部分-课程论坛：

   ![UI-课程论坛](C:\Users\Simon\AppData\Roaming\Typora\typora-user-images\UI-课程论坛.png)

6. 主体部分-自习室预约系统

   ![UI-自习室预约](C:\Users\Simon\AppData\Roaming\Typora\typora-user-images\UI-自习室预约.png)