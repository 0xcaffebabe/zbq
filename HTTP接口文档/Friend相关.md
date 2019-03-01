## 获取所有好友
```text
GET /friend/self
response:好友列表
可选参数:kw(如果不为空，则搜索)
```
```text
{
    "success": true,
    "msg": "success",
    "data": [
        {
            "friendUserId": 2,
            "friendUserInfo": {
                "userInfoId": null,
                "nickName": "my",
                "profile": null,
                "birthday": null,
                "penYear": null,
                "region": null,
                "gender": null,
                "description": null,
                "createTime": null,
                "updateTime": null
            }
        },
        {
            "friendUserId": 3,
            "friendUserInfo": {
                "userInfoId": null,
                "nickName": "佚名",
                "profile": null,
                "birthday": null,
                "penYear": null,
                "region": null,
                "gender": null,
                "description": null,
                "createTime": null,
                "updateTime": null
            }
        }
    ]
}
```
## 获取推荐好友
```text
GET /friend/recommend
response:推荐好友列表可选参数:kw(如果不为空，则搜索)

```
```text
{
    "success": true,
    "msg": "success",
    "data": [
        {
            "friendUserId": 4,
            "friendUserInfo": {
                "userInfoId": null,
                "nickName": "404",
                "profile": "img/anonymous.jpg",
                "birthday": null,
                "penYear": null,
                "region": null,
                "gender": null,
                "description": null,
                "createTime": null,
                "updateTime": null
            }
        },
        ...
    ]
}
```