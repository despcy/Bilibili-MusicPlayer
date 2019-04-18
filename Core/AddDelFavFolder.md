# 新建/删除收藏夹：

添加：

https://api.bilibili.com/x/v2/fav/folder/add

```
POST

HEADER:
Content-Type: application/x-www-form-urlencoded
Cookie: ...

BODY:

name=test&public=0&jsonp=jsonp&csrf=0e92ada8992b4f5312aedf1cdb7a597c

以上public=0->公开 public->1 私有
```

删除：

https://api.bilibili.com/x/v2/fav/folder/del

```
POST

HEADER:
Content-Type: application/x-www-form-urlencoded
Cookie: ...

BODY:

fid=3266531&jsonp=jsonp&csrf=0e92ada8992b4f5312aedf1cdb7a597c
```

## Success:

添加：
```json
{
    "code": 0,
    "message": "0",
    "ttl": 1,
    "data": {
        "fid": 3269792
    }
}
```

删除

```json
{
    "code": 0,
    "message": "0",
    "ttl": 1
}
```

## Failure:

添加：

```json
{
    "code": -101,
    "message": "账号未登录",
    "ttl": 1
}

{
    "code": -111,
    "message": "csrf 校验失败",
    "ttl": 1
}
```
删除

```json
{
    "code": 11010,
    "message": "您访问的内容不存在",
    "ttl": 1
}

{
    "code": 11003,
    "message": "不能删除默认内容",
    "ttl": 1
}

{
    "code": -111,
    "message": "csrf 校验失败",
    "ttl": 1
}
```
