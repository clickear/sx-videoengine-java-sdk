# 视杏短视频渲染 java sdk

### 运行环境

+ 操作系统： ubuntu 16.04 64 位
+ 显卡 

       nvidia 显卡建议 gtx1050 或以上
       驱动 394.57  https://www.geforce.cn/drivers
       cuda 9.0     https://developer.nvidia.com/cuda-toolkit-archive
       
+ opengl 
+ Java： jdk 11
+ ffmpeg 4.1   

### sdk 目录结构

```text
    |-- lib/                jni 依赖包
    |-- local_dependency    第三方依赖包,需要安装到系统
    |-- src/                sdk 主要文件
        |-- main            sdk 主要文件
        |-- test            sdk 测试示例
    |-- workspace           提供了测试模板和素材

```




### 使用步骤

+ 安装好相关驱动和环境
+ 进入　local_dependency 执行　install_dependency.sh 安装第三方依赖库
+ 安装　ffmpeg

        apt-get -y install software-properties-common
    
        add-apt-repository ppa:jonathonf/ffmpeg-4
        apt-get update
        apt-get -y install ffmpeg
    
    
+ workspace 中有可供测试的模板,图片和音乐文件
+ 参考 RenderProcessTest 用例获取开发步骤


### 运行方法

+ 运行的时候需要指定　-Djava.library.path 到lib 目录

+ "edit Configuraions" 增加　junit 的运行配置，在　VM Options 增加　-Djava.library.path=./lib　


