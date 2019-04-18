Bilibili的music streaming 类型应该是 [MPEG-DASH](https://en.wikipedia.org/wiki/Dynamic_Adaptive_Streaming_over_HTTP)类型, 所以Exoplayer的media source 应该选 DashMediaSource




Renderer – You may want to implement a custom Renderer to handle a media type not supported by the default implementations provided by the library.

TrackSelector – Implementing a custom TrackSelector allows an app developer to change the way in which tracks exposed by a MediaSource are selected for consumption by each of the available Renderers.

LoadControl – Implementing a custom LoadControl allows an app developer to change the player’s buffering policy.

Extractor – If you need to support a container format not currently supported by the library, consider implementing a custom Extractor class, which can then be used to together with ExtractorMediaSource to play media of that type.

MediaSource – Implementing a custom MediaSource class may be appropriate if you wish to obtain media samples to feed to renderers in a custom way, or if you wish to implement custom MediaSource compositing behavior.

DataSource – ExoPlayer’s upstream package already contains a number of DataSource implementations for different use cases. You may want to implement you own DataSource class to load data in another way, such as over a custom protocol, using a custom HTTP stack, or from a custom persistent cache.
