# 视杏短视频渲染 java sdk

### 运行环境

+ 操作系统： ubuntu 16.04
+ 显卡 

       nvidia 显卡
       驱动 最低 394.57
       cuda 9
       opengl
       
+ Java： jdk 11
+ ffmpeg 4.1   


# 使用说明

+ workspace 中有可供测试的模板,图片和音乐文件
+ 参考 RenderProcessTest 用例获取开发步骤
+ lib 中已经编译好的　jni 动态库


# 运行方法

+ 运行的时候需要指定　-Djava.library.path 到lib 目录

+ "edit Configuraions" 增加　junit 的运行配置，在　VM Options 增加　-Djava.library.path=./lib　
