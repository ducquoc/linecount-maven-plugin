package net.ducquoc.tr.engine.plugin;

import net.ducquoc.tr.engine.download.LftpWgetDownloader;

public class GifAnimationDownloader {

    private String webLink;

    public GifAnimationDownloader() {
        this.webLink = WebHelper.MAGIC_LINK3;
    }

    public GifAnimationDownloader(String webLink) {
        this.webLink = webLink;
    }

    public void downAnimation() {
        downAnimation(this.webLink);
    }

    public static void downAnimation(String webLink) {
        System.out.println("Downloading... " + webLink);
        LftpWgetDownloader.downAnimation(webLink);
        System.out.println("Complete Download " + webLink);
    }

}
