# VE java-sdk 使用文档


## 运行环境

### java-sdk 使用环境

+ 操作系统： ubuntu 16.04 64 位
+ 显卡 

       nvidia  gtx1050 或以上
       驱动    418.30 or newer  https://www.geforce.cn/drivers
       cuda    10.0  or newer   https://developer.nvidia.com/cuda-toolkit-archive
       
+ opengl : 3.0 or newer 
+ Java： jdk 1.8
+ ffmpeg : 4.1   
+ libfreeimage: 3 or newer( 1.4.4.release 版本需要)

### 注意事项

+ 使用 nvidia 显卡硬编码有显卡对同时编码的任务有数量限制
    
    参考 :  https://developer.nvidia.com/video-encode-decode-gpu-support-matrix#Encoder  
    "Max # of concurrent sessions" 这一列展示了显卡最大编码会话数  
    比如 gtx 1050　同时最大只支持两个编码任务, 所以在该显卡下最大只能开两个渲染进程

### sdk 目录结构

```text
    |-- lib/                jni 依赖包
    |-- local_dependency    第三方依赖包,需要安装到系统
    |-- src/                sdk 主要文件
        |-- main            sdk 主要文件
        |-- test            sdk 测试示例
    |-- workspace           提供了测试模板和素材
    |-- docs                开发文档
    |-- sdk-doc             sdk java doc

```




### 使用步骤

+ 安装好相关驱动和环境
+ 进入　lib 执行　install_lib.sh 安装第三方依赖库
+ 安装　ffmpeg

        apt-get -y install software-properties-common
    
        add-apt-repository ppa:jonathonf/ffmpeg-4
        apt-get update
        apt-get -y install ffmpeg

+ 安装 libfreeimage  (1.4.4.release 及以上需要安装 )
    
        apt-get install libfreeimage3
        
+ workspace 中有可供测试的模板,图片和音乐文件
+ 参考 RenderProcessTest 用例获取开发步骤




### 运行


+ 运行的时候需要指定　-Djava.library.path 到lib 目录

+ "edit Configuraions" 增加　junit 的运行配置，在　VM Options 增加　-Djava.library.path=./lib　
