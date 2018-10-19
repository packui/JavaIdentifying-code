# imgcode-1.0.0

### 简要介绍

imgcode是一个完全由用户自定义的、灵活的、用于快速生成网页图片验证码的一个框架，图片验证码的显示效果完全交由程序员自定义。

imgcode的使用也非常简单，使用者只需提供一个Properties配置文件，通过简单设置该文件中的属性，调用方法时传入该配置对象，即可开发出属于自己的图片验证码。

| Imgcode 类的方法及说明                                       | 参数说明                   | 返回值说明                                                   |
| ------------------------------------------------------------ | -------------------------- | ------------------------------------------------------------ |
| public   static   Map<String, BufferedImage>    createImageCode(Properties properties) | properties：验证码配置对象 | Map<String, BufferedImage>：键表示验证码字符内容，值表示验证码图片 |

### 使用指南

##### 1、在工程中导入 imgcode-1.0.0.jar

##### 2、创建一个Properties配置文件并添加以下所有属性

###### imgcode-1.0.0 的所有属性介绍

| 属性            | 属性值                | 描述说明                                                     |
| --------------- | --------------------- | ------------------------------------------------------------ |
| width           | 整数值                | 验证码图片的长                                               |
| height          | 整数值                | 验证码图片的高                                               |
| bgColor         | RGB整数值             | 例如：255,255,255                                            |
| borderColor     | RGB整数值             | useBorder为true才有效                                        |
| useBorder       | true，空值            | true表示有边框，空值表示无边框                               |
| type            | number，letter，blend | 验证码类型，number表示数字验证码，letter表示字母验证码，blend表示混合验证码 |
| length          | 整数值                | 验证码字符长度                                               |
| fontSize        | 整数值                | 验证码字符大小                                               |
| fontFamily      | 字体值                | 验证码所用字体                                               |
| fontColor       | RGB整数值，空值       | 空值表示颜色随机                                             |
| startSpace      | 整数值                | 验证码第一个字符出现的水平位置                               |
| space           | 整数值                | 验证码字符之间的间距                                         |
| angle           | 整数值                | 字符的倾斜角度的绝对值的最大值，例如：angle=20表示倾斜角度范围在 -20 到 20 之间 |
| interfereType   | line，dot，空值       | 干扰类型，line表示线条干扰，dot表示点阵干扰，空值表示无干扰  |
| interfereColor  | RGB整数值，空值       | 干扰色，空值表示颜色随机                                     |
| interfereNum    | 整数值                | 干扰数量                                                     |
| interfereRadius | 整数值                | 干扰点的大小，当interfereType=dot时才有效                    |

### 示例1-纯色验证码

imgcode.properties

```properties
width=150
height=50
bgColor=255,255,255
borderColor=0,0,0
useBorder=true
type=blend
length=4
fontSize=36
fontFamily=宋体
fontColor=0,0,0
startSpace=35
space=20
angle=10
interfereType=line
interfereColor=0,0,0
interfereNum=10
interfereRadius=0
```

调用示例：

```java
...
Properties properties = new Properties();
properties.load(this.getClass().getClassLoader().getResourceAsStream("imgcode.properties"));
Map<String, BufferedImage> map = Icode.createImageCode(properties);
...
```

效果图
![image](https://github.com/smallpawpaw/identifying-code/blob/master/example1.JPG)
### 示例2-随机色验证码

imgcode.properties

```properties
width=150
height=50
bgColor=255,255,255
borderColor=0,0,0
useBorder=true
type=blend
length=4
fontSize=36
fontFamily=宋体
fontColor=
startSpace=35
space=20
angle=10
interfereType=line
interfereColor=
interfereNum=10
interfereRadius=0
```

效果图
![image](https://github.com/smallpawpaw/identifying-code/blob/master/example2.JPG)
