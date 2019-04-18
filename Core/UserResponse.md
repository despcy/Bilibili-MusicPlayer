# 获取用户头像以及其他信息：

https://api.bilibili.com/x/space/acc/info?mid=402053210&jsonp=jsonp

## SuccessResponse:

```json
{
    "code": 0,
    "message": "0",
    "ttl": 1,
    "data": { UserData
        "mid": 3379951,
        "name": "ilem",
        "sex": "男",
        "face": "http://i2.hdslb.com/bfs/face/2e25812e0ba75174c07fe9b61e68e20c66e89499.jpg",
        "sign": "@ilemonyk 最近好像都不怎么好回复私信，有不商用的翻唱/翻调/翻填之类，你们就看着整吧",
        "rank": 10000,
        "level": 6,
        "jointime": 0,
        "moral": 0,
        "silence": 0,
        "birthday": "09-10",
        "coins": 182069.7,
        "fans_badge": true,
        "official": {
            "role": 1,
            "title": "bilibili 十周年成就奖UP主、高能联盟成员",
            "desc": ""
        },
        "vip": {
            "type": 2,
            "status": 1
        },
        "is_followed": false,
        "top_photo": "http://i1.hdslb.com/bfs/space/79d52f5a23fb391aad0b76e63aaeb0bc4c349f2b.png",
        "theme": {}
    }
}
```

## FailureResponse:

```json
{
    "code": -626,
    "message": "用户不存在",
    "ttl": 1,
    "data": {
        "mid": 337995,
        "name": "bilibili",
        "sex": "保密",
        "face": "https://static.hdslb.com/images/member/noface.gif",
        "sign": "哔哩哔哩 (゜-゜)つロ 干杯~-bilibili",
        "rank": 5000,
        "level": 0,
        "jointime": 0,
        "moral": 0,
        "silence": 0,
        "birthday": "01-01",
        "coins": 0,
        "fans_badge": false,
        "official": {
            "role": 0,
            "title": "",
            "desc": ""
        },
        "vip": {
            "type": 0,
            "status": 0
        },
        "is_followed": false,
        "top_photo": "http://i1.hdslb.com/bfs/space/cb1c3ef50e22b6096fde67febe863494caefebad.png",
        "theme": {}
    }
}
```
