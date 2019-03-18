## 获取所有共享location
```text
GET /location/list
```
```text
[
    {
        "userVO": {
            "userId": 1,
            "nickName": "root",
            "profile": "http://zbq88.oss-cn-hangzhou.aliyuncs.com/img/1551010048961-2059583269..jpg?x-oss-process=style/square"
        },
        "longitude": 118.58,
        "latitude": 24.93,
        "address": "福建省泉州市鲤城区媒人桥路2",
        "anonymous": false,
        "createTime": "2019-03-17T19:24:53"
    },
    {
        "userVO": {
            "userId": 2,
            "nickName": "my",
            "profile": "http://zbq88.oss-cn-hangzhou.aliyuncs.com/img/1551784391318-2059583269..jpg?x-oss-process=style/square"
        },
        "longitude": 118.95,
        "latitude": 25,
        "address": "是多少大所",
        "anonymous": false,
        "createTime": "2019-03-17T19:41:32"
    },
    {
        "userVO": null,
        "longitude": 119,
        "latitude": 24,
        "address": "多岁的撒多撒",
        "anonymous": true,
        "createTime": "2019-03-17T19:41:52"
    }
]
```