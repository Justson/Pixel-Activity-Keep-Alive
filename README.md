## <center/>通过一个像素的activity保活

### 使用
* Gradle

```
compile 'com.just.library:pixel:1.0.0'
```

* Maven

```
<dependency>
  <groupId>com.just.library</groupId>
  <artifactId>pixel</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```

### 引入
```
PixelActivityUnion
                .with(App.mContext)
                .targetActivityClazz(PointActivity.class)//
                .args(null)//
                .setActiviyManager(ActivityManager.getInstance())
                .start();
``` 
### 关闭
```
 PixelActivityUnion.quit();
```


### 别忘了配置AndroidManifest
```
<activity
            android:name=".pixelsdk.PointActivity"
            android:launchMode="singleTop"
            android:theme="@style/PointActivityStyle"
            ></activity>
```

### 自定义IActivityManager和Activity
IActivityManager是对Activity进行管理的一个借口, 比如启动和销毁,所以根据自己需要重写Add和Remove.
Activity比较简单, 最好让用户无法感知整过过程, 所以建议Activity尽量小化和透明化.
