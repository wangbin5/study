- Fat JAR：zip 压缩格式
- /META_INF/MANIFEST.MF 制定执行类、开始类
    - Main-Class  JarLauncher
        - 继承 ExecutableArchiveLauncher
        - 重写isNestedArchive 方法
        
    - Start-Class 
        - Spring Application main 文件