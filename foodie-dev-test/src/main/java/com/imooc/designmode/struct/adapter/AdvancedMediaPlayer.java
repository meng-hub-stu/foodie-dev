package com.imooc.designmode.struct.adapter;

/**
 * 更高级的播放器接口
 * @author Mengdl
 * @date 2022/04/19
 */
public interface AdvancedMediaPlayer {
    /**
     * 播放vlc
     * @param fileName
     */
    public void playVlc(String fileName);

    /**
     * 播放MP4
     * @param fileName
     */
    public void playMp4(String fileName);
}
