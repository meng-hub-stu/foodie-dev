package com.imooc.designmode.struct.adapter;

/**
 * 适配器类
 * 为媒体播放器和更高级的媒体播放器创建接口。
 * @author Mengdl
 * @date 2022/04/19
 */
public interface MediaPlayer {
    /**
     * 播放
     * @param audioType
     * @param fileName
     */
    public void play(String audioType, String fileName);
}
