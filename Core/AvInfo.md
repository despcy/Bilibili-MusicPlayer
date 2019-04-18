# 根据av号获取信息

https://api.bilibili.com/x/web-interface/view?aid=42463258

## Success：

```json
{
    "code": 0,
    "message": "0",
    "ttl": 1,
    "data": { AvData.class
        "aid": 42463258,
        "videos": 2,
        "tid": 20,
        "tname": "宅舞",
        "copyright": 1,
        "pic": "http://i1.hdslb.com/bfs/archive/2b19826ff83641570fa9a83f3eea342c5725f51d.jpg",
        "title": "【咬人猫】书记舞❤️ 辉夜大小姐想让我告白 ED2 o(*≧▽≦)ツ",
        "pubdate": 1549112859,
        "ctime": 1549112859,
        "desc": "书记舞刚出来就被洗脑了，筹划着就开始准备，卖了很久的关子，终于可以呈现给大家，虽然不是cos细节上没有办法完全地还原，但跳得好开心，好久没有跳这种开心魔性的舞蹈了～希望大家也看得开心，马上要春节啦，也在这里祝大家春节快乐～假期吃吃吃喝喝喝玩的开心呀～\n舞蹈参考：辉夜大小姐想让我告白ed-チカっとチカ千花っ",
        "state": 0,
        "attribute": 16768,
        "duration": 200,
        "rights": {
            "bp": 0,
            "elec": 0,
            "download": 1,
            "movie": 0,
            "pay": 0,
            "hd5": 1,
            "no_reprint": 1,
            "autoplay": 1,
            "ugc_pay": 0,
            "is_cooperation": 0
        },
        "owner": {
            "mid": 116683,
            "name": "=咬人猫=",
            "face": "http://i0.hdslb.com/bfs/face/8fad84a4470f3d894d8f0dc95555ab8f2cb10a83.jpg"
        },
        "stat": {
            "aid": 42463258,
            "view": 2318616,
            "danmaku": 6954,
            "reply": 7970,
            "favorite": 54052,
            "coin": 93834,
            "share": 12523,
            "now_rank": 0,
            "his_rank": 0,
            "like": 96063,
            "dislike": 0
        },
        "dynamic": "#咬人猫##书记舞##辉夜大小姐想让我告白#用这支超洗脑的书记舞~提前祝大家新年快乐啦！",
        "cid": 74506946,
        "dimension": {
            "width": 1920,
            "height": 1080,
            "rotate": 0
        },
        "no_cache": false,
        "pages": [
            {
                "cid": 74506946,
                "page": 1,
                "from": "vupload",
                "part": "横屏",
                "duration": 100,
                "vid": "",
                "weblink": "",
                "dimension": {
                    "width": 1920,
                    "height": 1080,
                    "rotate": 0
                }
            },
            {
                "cid": 74507080,
                "page": 2,
                "from": "vupload",
                "part": "竖屏手机版",
                "duration": 100,
                "vid": "",
                "weblink": "",
                "dimension": {
                    "width": 1080,
                    "height": 1920,
                    "rotate": 0
                }
            }
        ],
        "subtitle": {
            "allow_submit": false,
            "list": []
        }
    }
}
```

## Fail:

```json
{
    "code": -403,
    "message": "访问权限不足",
    "ttl": 1
}

{
    "code": -404,
    "message": "啥都木有",
    "ttl": 1
}
```
