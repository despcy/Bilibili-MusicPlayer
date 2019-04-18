# 获取用户收藏夹：

https://api.bilibili.com/x/space/fav/nav?mid=402053210&jsonp=jsonp

## RequestSuccess:

```json
{
    "code": 0,
    "message": "0",
    "ttl": 1,
    "data": {
        "archive": [List<FolderArchive.class>
            {
                "media_id": 325298510,
                "fid": 3252985,
                "mid": 402053210,
                "name": "默认收藏夹",
                "max_count": 50000,
                "cur_count": 3,
                "atten_count": 0,
                "favoured": 0,
                "state": 0,
                "ctime": 1550348755,
                "mtime": 1550566852,
                "cover": [
                    {
                        "aid": 43783589,
                        "pic": "http://i1.hdslb.com/bfs/archive/26b70d2229c6fc1e708be8669b1123cdfa7629e3.jpg",
                        "type": 2
                    },
                    {
                        "aid": 43693654,
                        "pic": "http://i2.hdslb.com/bfs/archive/27455dc1c1d6eff963a680b6cb3ffa9d2737223b.jpg",
                        "type": 2
                    },
                    {
                        "aid": 43426592,
                        "pic": "http://i0.hdslb.com/bfs/archive/7591c89704b448cd94a0cf749e65a39833c68d4f.png",
                        "type": 2
                    }
                ]
            },
            {
                "media_id": 326676510,
                "fid": 3266765,
                "mid": 402053210,
                "name": "aha123",
                "max_count": 999,
                "cur_count": 0,
                "atten_count": 0,
                "favoured": 0,
                "state": 2,
                "ctime": 1550650037,
                "mtime": 1550650037
            },
            {
                "media_id": 326674110,
                "fid": 3266741,
                "mid": 402053210,
                "name": "Publictest",
                "max_count": 999,
                "cur_count": 2,
                "atten_count": 0,
                "favoured": 0,
                "state": 2,
                "ctime": 1550649593,
                "mtime": 1550686590,
                "cover": [
                    {
                        "aid": 26305734,
                        "pic": "http://i0.hdslb.com/bfs/archive/eab0725ee5169ff28c3d9d10eb653bf4dbf698e7.jpg",
                        "type": 2
                    },
                    {
                        "aid": 43959668,
                        "pic": "http://i2.hdslb.com/bfs/archive/096aeb62a3630d62a75f17d7ce414392a7dcd056.jpg",
                        "type": 2
                    }
                ]
            },
            {
                "media_id": 326668010,
                "fid": 3266680,
                "mid": 402053210,
                "name": "pri",
                "max_count": 999,
                "cur_count": 2,
                "atten_count": 0,
                "favoured": 0,
                "state": 3,
                "ctime": 1550648516,
                "mtime": 1550686605,
                "cover": [
                    {
                        "aid": 26357452,
                        "pic": "http://i2.hdslb.com/bfs/archive/1478ced731c991460b7e172a9a145d6093ed3c65.jpg",
                        "type": 2
                    },
                    {
                        "aid": 43953324,
                        "pic": "http://i1.hdslb.com/bfs/archive/109715b3d2bf7f0e6d96e90ce84d1db54a9f4dd8.png",
                        "type": 2
                    }
                ]
            },
            {
                "media_id": 325298810,
                "fid": 3252988,
                "mid": 402053210,
                "name": "testPrivate",
                "max_count": 999,
                "cur_count": 2,
                "atten_count": 0,
                "favoured": 0,
                "state": 3,
                "ctime": 1550348801,
                "mtime": 1550453465,
                "cover": [
                    {
                        "aid": 43342490,
                        "pic": "http://i0.hdslb.com/bfs/archive/672b9ede5d868ed2b87114c6a610af9b7664b6be.jpg",
                        "type": 2
                    },
                    {
                        "aid": 43593228,
                        "pic": "http://i0.hdslb.com/bfs/archive/5d58e921c30696a00d016a0f02f14d393c131e66.jpg",
                        "type": 2
                    }
                ]
            }
        ],
        "playlist": 0,
        "topic": 0,
        "article": 0,
        "album": 0,
        "movie": 0
    }
}
```

## RequestFail:

```json
{
    "code": 0,
    "message": "0",
    "ttl": 1,
    "data": {
        "archive": [],
        "playlist": 0,
        "topic": 0,
        "article": 0,
        "album": 0,
        "movie": 0
    }
}
```
