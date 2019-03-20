# 使用说明

lib 中是已经编译好的　jni 动态库

src/test/java/com.seeshion/VideoEngineTest.java 有个　testRender 方法。

运行的时候需要指定　-Djava.library.path 到　jni 的目录：

+ "edit Configuraions" 增加　junit 的运行配置，在　VM Options 增加　-Djava.library.path=./lib　即可