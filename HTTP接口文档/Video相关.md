## 将播放页解析成内嵌地址
```text
PUT /video/analyze
body:网页地址
```
```text
https://www.bilibili.com/video/av42856333
```
```text
{
    "success": true,
    "msg": "success",
    "data": "//player.bilibili.com/player.html?aid=42856333"
}
```