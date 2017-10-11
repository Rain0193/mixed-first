# release notes

Not maintained anymore. The docs is in [here](https://ideasfortester.github.io/mixed-first/)

This project have some interesting idea there. Make UI and API testing
Annotation based.

This tools was built two years ago. It was a interesting journey to build it.
It opened a window to me for how to absorb opensource codes, compose these codes
then try to build something useful.

## 更新日志

### 10-19 Add More Elements handlers 
- 添加CheckBoxContainerElement
- 添加TagElement
- 添加RandomHelper.getNumAsString
- 添加RandomHelper.getRandomFrom

### 10-26 Add set Method to TestData 

- 添加set 方法到TestData
  目的是直接将一个实例复制到另外一个实例中
  
  ```java
     CustomerTestData d = new CustomerTestData();
     FavouriteTestData t = new FavouriteTestData();
     t.set(d);
  ```
  
  此时FavouriteTestData的实例就有了和CustomerTestData实例中的数据

### 11-9 

- 减少API代码自动生成时,用户的字段展示
- 在数据交验前添加等待时间,保证数据已经更新后在做检查
- SoftAssertion支持空json[] 这样的返回值
- 修改API Test Code 模版


### 11-18 Test Result Log refactor and enhancement

- Get Rid of Test Result templates file in product project
- Add logs and screenshots to test result report, every steps logged by TestResultLogger are shown in test result report
- Change Design of testing step logging, and TestNGTestContextAware to get the context of TestNG test information

## 11-24 Add HAR file to API Test Codes Generator
- 通过HAR 文件生成API测试代码
- 添加NumberHelper
- 去除点击页面元会高亮显示的代码,为了防止破坏原有页面元素

## 2-14 Change Get Page Source by using Rest Client

## install package and deploy to nexus server

- install to local:

```shell

sh install_local.sh

```

- deploy package to nexus

```shell

sh deploy.sh

```

## commit and push to git and svn

```shell
 sh svn_git_submit.sh "your-comments"
```


