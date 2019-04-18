# 根据av号与pid获取下载信息

https://api.bilibili.com/x/player/playurl?avid=42463258&cid=74506946&fnval=16&otype=json

## Success：

```json
{
    "code": 0,
    "message": "0",
    "ttl": 1,
    "data": {
        "from": "local",
        "result": "suee",
        "message": "",
        "quality": 32,
        "format": "flv480",
        "timelength": 99153,
        "accept_format": "hdflv2,flv,flv720,flv480,flv360",
        "accept_description": [
            "高清 1080P+",
            "高清 1080P",
            "高清 720P",
            "清晰 480P",
            "流畅 360P"
        ],
        "accept_quality": [
            112,
            80,
            64,
            32,
            16
        ],
        "video_codecid": 7,
        "seek_param": "start",
        "seek_type": "offset",
        "dash": { DownloadResources.class
            "duration": 99,
            "minBufferTime": 1.5,
            "video": [
                {
                    "id": 16,
                    "baseUrl": "http://upos-hz-mirrorwcsu.acgvideo.com/upgcxcode/46/69/74506946/74506946-1-30011.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEVEuxTEto8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&deadline=1550709028&gen=playurl&nbs=1&oi=2850703064&os=wcsu&platform=pc&trid=c621c311c72743849d98f7a347af60fe&uipk=5&upsig=9b0da29f23f0310cafdd672282192007",
                    "backupUrl": null,
                    "bandwidth": 287847,
                    "mimeType": "video/mp4",
                    "codecs": "hev1.1.6.L120.90",
                    "width": 640,
                    "height": 360,
                    "frameRate": "25",
                    "sar": "1:1",
                    "startWithSap": 1,
                    "SegmentBase": {
                        "Initialization": "0-1162",
                        "indexRange": "1163-1434"
                    },
                    "codecid": 12
                },
                {
                    "id": 32,
                    "baseUrl": "http://upos-hz-mirrorwcsu.acgvideo.com/upgcxcode/46/69/74506946/74506946-1-30033.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEVEuxTEto8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&deadline=1550709028&gen=playurl&nbs=1&oi=2850703064&os=wcsu&platform=pc&trid=c621c311c72743849d98f7a347af60fe&uipk=5&upsig=85f2c6c0366bba5bf8b6c77f5d4f88bb",
                    "backupUrl": null,
                    "bandwidth": 645696,
                    "mimeType": "video/mp4",
                    "codecs": "hev1.1.6.L120.90",
                    "width": 852,
                    "height": 480,
                    "frameRate": "25",
                    "sar": "1:1",
                    "startWithSap": 1,
                    "SegmentBase": {
                        "Initialization": "0-1164",
                        "indexRange": "1165-1436"
                    },
                    "codecid": 12
                },
                {
                    "id": 64,
                    "baseUrl": "http://upos-hz-mirrorwcsu.acgvideo.com/upgcxcode/46/69/74506946/74506946-1-30066.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEVEuxTEto8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&deadline=1550709028&gen=playurl&nbs=1&oi=2850703064&os=wcsu&platform=pc&trid=c621c311c72743849d98f7a347af60fe&uipk=5&upsig=e734dc29602df596e856e459cc2c5883",
                    "backupUrl": null,
                    "bandwidth": 1433484,
                    "mimeType": "video/mp4",
                    "codecs": "hev1.1.6.L120.90",
                    "width": 1280,
                    "height": 720,
                    "frameRate": "25",
                    "sar": "1:1",
                    "startWithSap": 1,
                    "SegmentBase": {
                        "Initialization": "0-1164",
                        "indexRange": "1165-1436"
                    },
                    "codecid": 12
                },
                {
                    "id": 80,
                    "baseUrl": "http://upos-hz-mirrorwcsu.acgvideo.com/upgcxcode/46/69/74506946/74506946-1-30077.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEVEuxTEto8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&deadline=1550709028&gen=playurl&nbs=1&oi=2850703064&os=wcsu&platform=pc&trid=c621c311c72743849d98f7a347af60fe&uipk=5&upsig=7c44dea6ad6cc00ef540786a3930dba4",
                    "backupUrl": null,
                    "bandwidth": 2150196,
                    "mimeType": "video/mp4",
                    "codecs": "hev1.1.6.L150.90",
                    "width": 1920,
                    "height": 1080,
                    "frameRate": "25",
                    "sar": "1:1",
                    "startWithSap": 1,
                    "SegmentBase": {
                        "Initialization": "0-1164",
                        "indexRange": "1165-1436"
                    },
                    "codecid": 12
                },
                {
                    "id": 16,
                    "baseUrl": "http://upos-hz-mirrorwcsu.acgvideo.com/upgcxcode/46/69/74506946/74506946-1-30015.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEVEuxTEto8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&deadline=1550709028&gen=playurl&nbs=1&oi=2850703064&os=wcsu&platform=pc&trid=c621c311c72743849d98f7a347af60fe&uipk=5&upsig=2606046de4634206472f232e5859b02c",
                    "backupUrl": null,
                    "bandwidth": 270615,
                    "mimeType": "video/mp4",
                    "codecs": "avc1.64001E",
                    "width": 640,
                    "height": 360,
                    "frameRate": "25",
                    "sar": "1:1",
                    "startWithSap": 1,
                    "SegmentBase": {
                        "Initialization": "0-986",
                        "indexRange": "987-1258"
                    },
                    "codecid": 7
                },
                {
                    "id": 32,
                    "baseUrl": "http://upos-hz-mirrorwcsu.acgvideo.com/upgcxcode/46/69/74506946/74506946-1-30032.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEVEuxTEto8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&deadline=1550709028&gen=playurl&nbs=1&oi=2850703064&os=wcsu&platform=pc&trid=c621c311c72743849d98f7a347af60fe&uipk=5&upsig=5aaaf29ea6a9331ee3660960cb6aa28a",
                    "backupUrl": null,
                    "bandwidth": 404642,
                    "mimeType": "video/mp4",
                    "codecs": "avc1.64001E",
                    "width": 852,
                    "height": 480,
                    "frameRate": "25",
                    "sar": "1:1",
                    "startWithSap": 1,
                    "SegmentBase": {
                        "Initialization": "0-986",
                        "indexRange": "987-1258"
                    },
                    "codecid": 7
                },
                {
                    "id": 64,
                    "baseUrl": "http://upos-hz-mirrorwcsu.acgvideo.com/upgcxcode/46/69/74506946/74506946-1-30064.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEVEuxTEto8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&deadline=1550709028&gen=playurl&nbs=1&oi=2850703064&os=wcsu&platform=pc&trid=c621c311c72743849d98f7a347af60fe&uipk=5&upsig=8d2dde1fe052e5c54fd7b9f62eb4a081",
                    "backupUrl": null,
                    "bandwidth": 604511,
                    "mimeType": "video/mp4",
                    "codecs": "avc1.64001F",
                    "width": 1280,
                    "height": 720,
                    "frameRate": "25",
                    "sar": "1:1",
                    "startWithSap": 1,
                    "SegmentBase": {
                        "Initialization": "0-986",
                        "indexRange": "987-1258"
                    },
                    "codecid": 7
                },
                {
                    "id": 80,
                    "baseUrl": "http://upos-hz-mirrorwcsu.acgvideo.com/upgcxcode/46/69/74506946/74506946-1-30080.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEVEuxTEto8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&deadline=1550709028&gen=playurl&nbs=1&oi=2850703064&os=wcsu&platform=pc&trid=c621c311c72743849d98f7a347af60fe&uipk=5&upsig=8115c1d8135a581d6eaacbe34499a89a",
                    "backupUrl": null,
                    "bandwidth": 998716,
                    "mimeType": "video/mp4",
                    "codecs": "avc1.640028",
                    "width": 1920,
                    "height": 1080,
                    "frameRate": "25",
                    "sar": "1:1",
                    "startWithSap": 1,
                    "SegmentBase": {
                        "Initialization": "0-987",
                        "indexRange": "988-1259"
                    },
                    "codecid": 7
                }
            ],
            "audio": [
                {
                    "id": 30280,
                    "baseUrl": "http://upos-hz-mirrorwcsu.acgvideo.com/upgcxcode/46/69/74506946/74506946-1-30280.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEVEuxTEto8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&deadline=1550709028&gen=playurl&nbs=1&oi=2850703064&os=wcsu&platform=pc&trid=c621c311c72743849d98f7a347af60fe&uipk=5&upsig=6d215be6848cb8e518c127c10dacf5f0",
                    "backupUrl": null,
                    "bandwidth": 319413,
                    "mimeType": "audio/mp4",
                    "codecs": "mp4a.40.2",
                    "width": 0,
                    "height": 0,
                    "frameRate": "",
                    "sar": "",
                    "startWithSap": 0,
                    "SegmentBase": {
                        "Initialization": "0-907",
                        "indexRange": "908-1179"
                    },
                    "codecid": 0
                },
                {
                    "id": 30216,
                    "baseUrl": "http://upos-hz-mirrorwcsu.acgvideo.com/upgcxcode/46/69/74506946/74506946-1-30216.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEVEuxTEto8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&deadline=1550709028&gen=playurl&nbs=1&oi=2850703064&os=wcsu&platform=pc&trid=c621c311c72743849d98f7a347af60fe&uipk=5&upsig=e5f950a97e3f82e0106469e57000ee5e",
                    "backupUrl": null,
                    "bandwidth": 67284,
                    "mimeType": "audio/mp4",
                    "codecs": "mp4a.40.2",
                    "width": 0,
                    "height": 0,
                    "frameRate": "",
                    "sar": "",
                    "startWithSap": 0,
                    "SegmentBase": {
                        "Initialization": "0-907",
                        "indexRange": "908-1179"
                    },
                    "codecid": 0
                }
            ]
        }
    }
}
```

### Format2:
```json
{
    "code": 0,
    "message": "0",
    "ttl": 1,
    "data": {
        "from": "local",
        "result": "suee",
        "message": "",
        "quality": 32,
        "format": "flv480",
        "timelength": 266692,
        "accept_format": "flv,flv720,flv480,flv360",
        "accept_description": [
            "高清 1080P",
            "高清 720P",
            "清晰 480P",
            "流畅 360P"
        ],
        "accept_quality": [
            80,
            64,
            32,
            16
        ],
        "video_codecid": 7,
        "seek_param": "start",
        "seek_type": "offset",
        "durl": [
            {
                "order": 1,
                "length": 266692,
                "size": 40954910,
                "ahead": "EhBW5QA=",
                "vhead": "AWQAHv/hABlnZAAerNlA2D3n4QAAAwABAAADADIPFi2WAQAFaOvs8jw=",
                "url": "http://upos-hz-mirrorwcsu.acgvideo.com/upgcxcode/57/72/48167257/48167257-1-32.flv?e=ig8euxZM2rNcNbh3hbUVhoMznwNBhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1552450974&gen=playurl&nbs=1&oi=3086324883&os=wcsu&platform=pc&trid=6dcd45d90d984599be9701d46904b591&uipk=5&upsig=f8b75202583a1bac4d7b1d075248f6b3",
                "backup_url": [
                    "http://upos-hz-mirrorcos.acgvideo.com/upgcxcode/57/72/48167257/48167257-1-32.flv?um_deadline=1552450974&platform=pc&rate=382500&oi=3086324883&um_sign=66e7add47120d7803a7e6368c020f624&gen=playurl&os=cos&trid=6dcd45d90d984599be9701d46904b591"
                ]
            }
        ]
    }
}
```


## Fail

```json
{
    "code": -404,
    "message": "啥都木有",
    "ttl": 1,
    "data": null
}
```
